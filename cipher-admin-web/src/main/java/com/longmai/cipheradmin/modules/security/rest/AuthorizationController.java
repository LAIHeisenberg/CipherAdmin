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
package com.longmai.cipheradmin.modules.security.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.annotation.rest.AnonymousDeleteMapping;
import com.longmai.cipheradmin.annotation.rest.AnonymousGetMapping;
import com.longmai.cipheradmin.annotation.rest.AnonymousPostMapping;
import com.longmai.cipheradmin.config.RsaProperties;
import com.longmai.cipheradmin.exception.BadRequestException;
import com.longmai.cipheradmin.modules.bs.domain.SysRootSecretkey;
import com.longmai.cipheradmin.modules.bs.service.SysRootSecretkeyService;
import com.longmai.cipheradmin.modules.security.config.bean.LoginCodeEnum;
import com.longmai.cipheradmin.modules.security.config.bean.LoginProperties;
import com.longmai.cipheradmin.modules.security.config.bean.SecurityProperties;
import com.longmai.cipheradmin.modules.security.security.TokenProvider;
import com.longmai.cipheradmin.modules.security.service.OnlineUserService;
import com.longmai.cipheradmin.modules.security.service.dto.AuthUserDto;
import com.longmai.cipheradmin.modules.security.service.dto.JwtUserDto;
import com.longmai.cipheradmin.modules.system.service.UserService;
import com.longmai.cipheradmin.modules.system.service.dto.UserDto;
import com.longmai.cipheradmin.modules.system.service.dto.UserLoginDto;
import com.longmai.cipheradmin.utils.*;
import com.longmai.cipheradmin.utils.enums.AuthMethodEnum;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * ???????????????token????????????????????????
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "???????????????????????????")
public class AuthorizationController {
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Resource
    private LoginProperties loginProperties;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SysRootSecretkeyService rootSecretkeyService;

    @Log("????????????")
    @ApiOperation("????????????")
    @AnonymousPostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        String password = null;
        String username = authUser.getUsername();
        Authentication authentication;
        if (AuthMethodEnum.USB_KEY.getCode().equals(authUser.getAuthMethod())){
            UserDto userDto = userService.findByDn(authUser.getDn());
            if (userDto == null){
                throw new BadRequestException("???????????????");
            }
            PublicKey publicKey = CertUtils.getPublicKey(userDto.getCert());
            if(publicKey == null){
                throw new BadRequestException("???????????????");
            }
            if(!RsaUtils.verify(authUser.getPreSignCode(), authUser.getSign(), publicKey, "SHA1withRSA")){
                throw new BadRequestException("????????????");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }else {
            // ????????????
            password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
            // ???????????????
            String code = (String) redisUtils.get(authUser.getUuid());
            // ???????????????
            redisUtils.del(authUser.getUuid());
            if (StringUtils.isBlank(code)) {
                throw new BadRequestException("??????????????????????????????");
            }
            if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
                throw new BadRequestException("???????????????");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();

        //???????????????????????????
        // ????????????????????????????????? ????????????????????????????????????????????? ?????????????????????????????????????????????????????????
        // ???????????????????????? USB Key ??????????????? ?????????????????? ???????????????????????????????????? ????????????????????????
        // ??????????????????????????? ????????????????????????????????????????????????????????? ???????????????????????????????????????????????? ????????????????????? USB Key ??????
        if (AuthMethodEnum.USB_KEY.getCode().equals(authUser.getAuthMethod())){
            rootSecretkeyService.generateRootSecrectKey();
        }


        // ??????????????????
        onlineUserService.save(jwtUserDto, token, request);
        //?????????????????????????????????????????????
        UserLoginDto loginDto = jwtUserDto.getUser();
        Date pwdResetTime = loginDto.getPwdResetTime();
        Boolean ifNeedModifyPwd = loginDto.getIfNeedModifyPwd();


        // ?????? token ??? ????????????
        Map<String, Object> authInfo = new HashMap<String, Object>(3) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
            if(pwdResetTime==null && (ifNeedModifyPwd==null || ifNeedModifyPwd)){
                put("ifNeedModifyPwd", true);
            }else{
                put("ifNeedModifyPwd", false);
            }
        }};
        if (loginProperties.isSingleLogin()) {
            //???????????????????????????token
            onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
        }
        return ResponseEntity.ok(authInfo);
    }

    @ApiOperation("??????????????????")
    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
    }

    @ApiOperation("???????????????")
    @AnonymousGetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // ?????????????????????
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        //????????????????????? arithmetic???????????? >= 2 ??????captcha.text()??????????????????????????????
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // ??????
        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // ???????????????
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }

    @ApiOperation("????????????")
    @AnonymousDeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AnonymousGetMapping(value = "/preSign-code")
    public ResponseEntity<Object> getPreSignCode(){
        String code = RandomUtil.randomString(16);
        Map<String, String> resMap = new HashMap<String, String>(2) {{
            put("code", code);
        }};
        return ResponseEntity.ok(resMap);
    }

}
