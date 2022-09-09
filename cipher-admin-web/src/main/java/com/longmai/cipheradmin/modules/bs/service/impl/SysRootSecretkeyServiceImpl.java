package com.longmai.cipheradmin.modules.bs.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.longmai.cipheradmin.modules.bs.domain.SysRootSecretkey;
import com.longmai.cipheradmin.utils.*;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.SysRootSecretkeyRepository;
import com.longmai.cipheradmin.modules.bs.service.SysRootSecretkeyService;
import com.longmai.cipheradmin.modules.bs.service.dto.SysRootSecretkeyDto;
import com.longmai.cipheradmin.modules.bs.service.dto.SysRootSecretkeyQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.SysRootSecretkeyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author huangsi
* @date 2022-09-09
**/
@Service
@RequiredArgsConstructor
public class SysRootSecretkeyServiceImpl implements SysRootSecretkeyService {

    private final SysRootSecretkeyRepository sysRootSecretkeyRepository;
    private final SysRootSecretkeyMapper sysRootSecretkeyMapper;
    private final RedisUtils redisUtils;

    @Override
    public Map<String,Object> queryAll(SysRootSecretkeyQueryCriteria criteria, Pageable pageable){
        Page<SysRootSecretkey> page = sysRootSecretkeyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysRootSecretkeyMapper::toDto));
    }

    @Override
    public List<SysRootSecretkeyDto> queryAll(SysRootSecretkeyQueryCriteria criteria){
        return sysRootSecretkeyMapper.toDto(sysRootSecretkeyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SysRootSecretkeyDto findById(Long id) {
        SysRootSecretkey sysRootSecretkey = sysRootSecretkeyRepository.findById(id).orElseGet(SysRootSecretkey::new);
        ValidationUtil.isNull(sysRootSecretkey.getId(),"SysRootSecretkey","id",id);
        return sysRootSecretkeyMapper.toDto(sysRootSecretkey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRootSecretkeyDto create(SysRootSecretkey resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return sysRootSecretkeyMapper.toDto(sysRootSecretkeyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRootSecretkey resources) {
        SysRootSecretkey sysRootSecretkey = sysRootSecretkeyRepository.findById(resources.getId()).orElseGet(SysRootSecretkey::new);
        ValidationUtil.isNull( sysRootSecretkey.getId(),"SysRootSecretkey","id",resources.getId());
        sysRootSecretkey.copy(resources);
        sysRootSecretkeyRepository.save(sysRootSecretkey);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            sysRootSecretkeyRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SysRootSecretkeyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysRootSecretkeyDto sysRootSecretkey : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" secretkey",  sysRootSecretkey.getSecretkey());
            map.put("密码卡密钥", sysRootSecretkey.getKey1());
            map.put("超级管理员创建时初始化", sysRootSecretkey.getKey2());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void generateRootSecrectKey() {
        if(ObjectUtil.isNotNull(redisUtils.get("rootSecrectKey"))){
            return;
        }
        List<SysRootSecretkey> sysRootSecretkeys = sysRootSecretkeyRepository.findAll();
        if(CollectionUtil.isNotEmpty(sysRootSecretkeys)){
            SysRootSecretkey secretkey = sysRootSecretkeys.get(0);
            if(StringUtils.isBlank(secretkey.getSecretkey()) && StringUtils.isNotBlank(secretkey.getKey1()) && StringUtils.isNotBlank(secretkey.getKey2()) ){
                String secKey= secretkey.getKey1()+secretkey.getKey2();
                SysRootSecretkey update = new SysRootSecretkey();
                update.setId(secretkey.getId());
                update.setSecretkey(secKey);
                sysRootSecretkeyRepository.save(update);
                redisUtils.set("rootSecrectKey",true);
            }
        }
    }
}