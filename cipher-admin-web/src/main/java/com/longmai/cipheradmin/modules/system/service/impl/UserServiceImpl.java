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
package com.longmai.cipheradmin.modules.system.service.impl;

import com.longmai.cipheradmin.config.FileProperties;
import com.longmai.cipheradmin.exception.BadRequestException;
import com.longmai.cipheradmin.exception.EntityExistException;
import com.longmai.cipheradmin.exception.EntityNotFoundException;
import com.longmai.cipheradmin.modules.security.service.OnlineUserService;
import com.longmai.cipheradmin.modules.security.service.UserCacheManager;
import com.longmai.cipheradmin.modules.system.domain.User;
import com.longmai.cipheradmin.modules.system.repository.UserRepository;
import com.longmai.cipheradmin.modules.system.service.UserService;
import com.longmai.cipheradmin.modules.system.service.dto.*;
import com.longmai.cipheradmin.modules.system.service.mapstruct.UserLoginMapper;
import com.longmai.cipheradmin.modules.system.service.mapstruct.UserMapper;
import com.longmai.cipheradmin.utils.*;
import com.longmai.cipheradmin.utils.enums.AuthMethodEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileProperties properties;
    private final RedisUtils redisUtils;
    private final UserCacheManager userCacheManager;
    private final OnlineUserService onlineUserService;
    private final UserLoginMapper userLoginMapper;

    @Override
    public Object queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<User> page = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    public List<UserDto> queryAll(UserQueryCriteria criteria) {
        List<User> users = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return userMapper.toDto(users);
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    @Transactional(rollbackFor = Exception.class)
    public UserDto findById(long id) {
        User user = userRepository.findById(id).orElseGet(User::new);
        ValidationUtil.isNull(user.getId(), "User", "id", id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User resources) {
        if (userRepository.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
        userRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) throws Exception {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        ValidationUtil.isNull(user.getId(), "User", "id", resources.getId());
        User user1 = userRepository.findByUsername(resources.getUsername());
        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
        // ???????????????????????????
        if (!resources.getRole().equals(user.getRole())) {
            redisUtils.del(CacheKey.DATA_USER + resources.getId());
            redisUtils.del(CacheKey.MENU_USER + resources.getId());
            redisUtils.del(CacheKey.ROLE_AUTH + resources.getId());
        }
        // ???????????????????????????????????????????????????
        if(!resources.getEnabled()){
            onlineUserService.kickOutForUsername(resources.getUsername());
        }
        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRole(resources.getRole());
        user.setTel(resources.getTel());
        user.setNickName(resources.getNickName());
        user.setGender(resources.getGender());
        user.setAuthMethod(resources.getAuthMethod());
        user.setState(resources.getState());
        user.setAddress(resources.getAddress());
        if(StringUtils.isNotBlank(resources.getDn())){
            user.setDn(resources.getDn());
        }
        if(StringUtils.isNotBlank(resources.getCert())){
            user.setCert(resources.getCert());
        }
        if(StringUtils.isNotBlank(resources.getPassword())){
            user.setCert(resources.getPassword());
        }
        userRepository.save(user);
        // ????????????
        delCaches(user.getId(), user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(User resources) {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);

        user.setNickName(resources.getNickName());
        user.setTel(resources.getTel());
        user.setGender(resources.getGender());
        userRepository.save(user);
        // ????????????
        delCaches(user.getId(), user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            // ????????????
            UserDto user = findById(id);
            delCaches(user.getId(), user.getUsername());
        }
        userRepository.deleteAllByIdIn(ids);
    }

    @Override
    public UserDto findByName(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @Override
    public UserDto findByDn(String dn){
        User user = userRepository.findByDnAndEnabled(dn, true);
        if (user == null){
            throw new EntityNotFoundException(User.class, "dn", dn);
        } else {
            return userMapper.toDto(user);
        }
    }


    @Override
    public UserLoginDto getLoginData(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userLoginMapper.toDto(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        userRepository.updatePass(username, pass, new Date());
        flushCache(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> updateAvatar(MultipartFile multipartFile) {
        // ??????????????????
        FileUtil.checkSize(properties.getAvatarMaxSize(), multipartFile.getSize());
        // ???????????????????????????
        String image = "gif jpg png jpeg";
        String fileType = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        if(fileType != null && !image.contains(fileType)){
            throw new BadRequestException("?????????????????????, ????????? " + image +" ??????");
        }
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername());
        String oldPath = user.getAvatarPath();
        File file = FileUtil.upload(multipartFile, properties.getPath().getAvatar());
        user.setAvatarPath(Objects.requireNonNull(file).getPath());
        user.setAvatarName(file.getName());
        userRepository.save(user);
        if (StringUtils.isNotBlank(oldPath)) {
            FileUtil.del(oldPath);
        }
        @NotBlank String username = user.getUsername();
        flushCache(username);
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName());
        }};
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userRepository.updateEmail(username, email);
        flushCache(username);
    }

    @Override
    public void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDto userDTO : queryAll) {
            String roleName = userDTO.getRole().getName();
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("?????????", userDTO.getUsername());
            map.put("??????", roleName);
            map.put("??????", userDTO.getEmail());
            map.put("??????", userDTO.getEnabled() ? "??????" : "??????");
            map.put("????????????", userDTO.getTel());
            map.put("?????????????????????", userDTO.getPwdResetTime());
            map.put("????????????", userDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * ????????????
     *
     * @param id /
     */
    public void delCaches(Long id, String username) {
        redisUtils.del(CacheKey.USER_ID + id);
        flushCache(username);
    }

    /**
     * ?????? ????????? ??????????????????
     *
     * @param username /
     */
    private void flushCache(String username) {
        userCacheManager.cleanUserCache(username);
    }
}
