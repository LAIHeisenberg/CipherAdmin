package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.SysWorkSecretkey;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.SysWorkSecretkeyRepository;
import com.longmai.cipheradmin.modules.bs.service.SysWorkSecretkeyService;
import com.longmai.cipheradmin.modules.bs.service.dto.SysWorkSecretkeyDto;
import com.longmai.cipheradmin.modules.bs.service.dto.SysWorkSecretkeyQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.SysWorkSecretkeyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author huangsi
* @date 2022-09-09
**/
@Service
@RequiredArgsConstructor
public class SysWorkSecretkeyServiceImpl implements SysWorkSecretkeyService {

    private final SysWorkSecretkeyRepository sysWorkSecretkeyRepository;
    private final SysWorkSecretkeyMapper sysWorkSecretkeyMapper;

    @Override
    public Map<String,Object> queryAll(SysWorkSecretkeyQueryCriteria criteria, Pageable pageable){
        Page<SysWorkSecretkey> page = sysWorkSecretkeyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysWorkSecretkeyMapper::toDto));
    }

    @Override
    public List<SysWorkSecretkeyDto> queryAll(SysWorkSecretkeyQueryCriteria criteria){
        return sysWorkSecretkeyMapper.toDto(sysWorkSecretkeyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SysWorkSecretkeyDto findById(Long id) {
        SysWorkSecretkey sysWorkSecretkey = sysWorkSecretkeyRepository.findById(id).orElseGet(SysWorkSecretkey::new);
        ValidationUtil.isNull(sysWorkSecretkey.getId(),"SysWorkSecretkey","id",id);
        return sysWorkSecretkeyMapper.toDto(sysWorkSecretkey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysWorkSecretkeyDto create(SysWorkSecretkey resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return sysWorkSecretkeyMapper.toDto(sysWorkSecretkeyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysWorkSecretkey resources) {
        SysWorkSecretkey sysWorkSecretkey = sysWorkSecretkeyRepository.findById(resources.getId()).orElseGet(SysWorkSecretkey::new);
        ValidationUtil.isNull( sysWorkSecretkey.getId(),"SysWorkSecretkey","id",resources.getId());
        sysWorkSecretkey.copy(resources);
        sysWorkSecretkeyRepository.save(sysWorkSecretkey);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            sysWorkSecretkeyRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SysWorkSecretkeyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysWorkSecretkeyDto sysWorkSecretkey : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" secretkey",  sysWorkSecretkey.getSecretkey());
            map.put(" cryptographicAlgorithm",  sysWorkSecretkey.getCryptographicAlgorithm());
            map.put(" cryptographicLength",  sysWorkSecretkey.getCryptographicLength());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}