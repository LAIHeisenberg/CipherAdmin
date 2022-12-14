/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.longmai.cipheradmin.modules.system.rest;

import cn.hutool.core.collection.CollectionUtil;
import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.config.RsaProperties;
import com.longmai.cipheradmin.exception.BadRequestException;
import com.longmai.cipheradmin.modules.system.domain.User;
import com.longmai.cipheradmin.modules.system.domain.vo.UserPassVo;
import com.longmai.cipheradmin.modules.system.service.*;
import com.longmai.cipheradmin.modules.system.service.dto.RoleSmallDto;
import com.longmai.cipheradmin.modules.system.service.dto.UserDto;
import com.longmai.cipheradmin.modules.system.service.dto.UserQueryCriteria;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.RsaUtils;
import com.longmai.cipheradmin.utils.SecurityUtils;
import com.longmai.cipheradmin.utils.StringUtils;
import com.longmai.cipheradmin.utils.enums.AuthMethodEnum;
import com.longmai.cipheradmin.utils.enums.CodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "?????????????????????")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final VerifyService verificationCodeService;

    @ApiOperation("??????????????????")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('user:list')")
    public void exportUser(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        userService.download(userService.queryAll(criteria), response);
    }

    @ApiOperation("????????????")
    @GetMapping
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> queryUser(UserQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("????????????")
    @ApiOperation("????????????")
    @PostMapping
    @PreAuthorize("@el.check('user:add')")
    public ResponseEntity<Object> createUser(@Validated @RequestBody User resources) {
        // ???????????? 123456
        if (AuthMethodEnum.USER_PWD.getCode().equals(resources.getAuthMethod())) {
            if(StringUtils.isBlank(resources.getPassword())){
                resources.setPassword("123456");
            }
            resources.setPassword(passwordEncoder.encode(resources.getPassword()));
        }

        resources.setIfLogin(Boolean.FALSE);
        userService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("?????????????????????")
    @ApiOperation("????????????")
    @PutMapping
    @PreAuthorize("@el.check('user:edit')")
    public ResponseEntity<Object> updateUser(@Validated(User.Update.class) @RequestBody User resources) throws Exception {
        if (AuthMethodEnum.USER_PWD.getCode().equals(resources.getAuthMethod())) {
            if(StringUtils.isBlank(resources.getPassword())){
                resources.setPassword("123456");
            }
            resources.setPassword(passwordEncoder.encode(resources.getPassword()));
        }
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("???????????????????????????")
    @ApiOperation("???????????????????????????")
    @PutMapping(value = "center")
    public ResponseEntity<Object> centerUser(@Validated(User.Update.class) @RequestBody User resources) {
        if (!resources.getId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BadRequestException("????????????????????????");
        }
        userService.updateCenter(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Log("????????????")
    @ApiOperation("????????????")
    @DeleteMapping
    @PreAuthorize("@el.check('user:del')")
    public ResponseEntity<Object> deleteUser(@RequestBody Set<Long> ids) {
        userService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("??????????????????")
    @PostMapping(value = "/updatePass")
    public ResponseEntity<Object> updateUserPass(@RequestBody UserPassVo passVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getOldPass());
        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getNewPass());
        UserDto user = userService.findByName(SecurityUtils.getCurrentUsername());
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new BadRequestException("??????????????????????????????");
        }
        if (passwordEncoder.matches(newPass, user.getPassword())) {
            throw new BadRequestException("?????????????????????????????????");
        }
        userService.updatePass(user.getUsername(), passwordEncoder.encode(newPass));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("????????????")
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateUserAvatar(@RequestParam MultipartFile avatar) {
        return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
    }

//    @Log("????????????")
//    @ApiOperation("????????????")
//    @PostMapping(value = "/updateEmail/{code}")
//    public ResponseEntity<Object> updateUserEmail(@PathVariable String code,@RequestBody User user) throws Exception {
//        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey,user.getPassword());
//        UserDto userDto = userService.findByName(SecurityUtils.getCurrentUsername());
//        if(!passwordEncoder.matches(password, userDto.getPassword())){
//            throw new BadRequestException("????????????");
//        }
//        verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + user.getEmail(), code);
//        userService.updateEmail(userDto.getUsername(),user.getEmail());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
