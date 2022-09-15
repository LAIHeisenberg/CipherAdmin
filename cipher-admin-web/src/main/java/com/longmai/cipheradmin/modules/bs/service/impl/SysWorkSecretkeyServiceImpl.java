package com.longmai.cipheradmin.modules.bs.service.impl;

import ch.ntb.inf.kmip.kmipenum.EnumCryptographicAlgorithm;
import ch.ntb.inf.kmip.kmipenum.EnumObjectType;
import cn.hutool.core.collection.CollectionUtil;
import com.longmai.cipheradmin.modules.bs.domain.SysWorkSecretkey;
import com.longmai.cipheradmin.modules.bs.param.SecKeyCreateParam;
import com.longmai.cipheradmin.modules.bs.service.KmipService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTemplateDto;
import com.longmai.cipheradmin.utils.*;
import com.longmai.cipheradmin.utils.enums.CryptographicAlgorithmEnum;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.SysWorkSecretkeyRepository;
import com.longmai.cipheradmin.modules.bs.service.SysWorkSecretkeyService;
import com.longmai.cipheradmin.modules.bs.service.dto.SysWorkSecretkeyDto;
import com.longmai.cipheradmin.modules.bs.service.dto.SysWorkSecretkeyQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.SysWorkSecretkeyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
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
    @Autowired
    KmipService kmip;

    @Override
    public Map<String,Object> queryAll(SysWorkSecretkeyQueryCriteria criteria, Pageable pageable){
        Page<SysWorkSecretkey> page = sysWorkSecretkeyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<SysWorkSecretkeyDto> secretkeyDtos = sysWorkSecretkeyMapper.toDto(page.getContent());

        secretkeyDtos.stream().forEach(secretkey->{
            String alg = secretkey.getCryptographicAlgorithm();
            if(StringUtils.isNotBlank(alg)){
                secretkey.setCryptographicAlgorithmName(CryptographicAlgorithmEnum.find(Integer.valueOf(alg)).name());
            }
        });

        Page<SysWorkSecretkeyDto> pages = new PageImpl(secretkeyDtos,pageable,page.getTotalElements());

        return PageUtil.toPage(pages);
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
    public SysWorkSecretkeyDto create() {
        SecKeyCreateParam secKeyCreateParam =new SecKeyCreateParam();
        //创建对称密钥
        secKeyCreateParam.setObjectType(EnumObjectType.SymmetricKey);
        secKeyCreateParam.setCryptAlgorithm(EnumCryptographicAlgorithm.AES);
        secKeyCreateParam.setSeckeyLength(128);
        secKeyCreateParam.setCryptMask(0x0C);
        List<String> uuidKeys = kmip.sendCreatRequest(secKeyCreateParam);

        SysWorkSecretkey resources = new SysWorkSecretkey();
        resources.setCryptographicLength(128);
        resources.setCryptographicAlgorithm(String.valueOf(EnumCryptographicAlgorithm.AES));
        if(CollectionUtil.isNotEmpty(uuidKeys)){
            resources.setSecretkey(uuidKeys.get(0));
            resources.setUuidKey(uuidKeys.get(0));
        }
//        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
//        resources.setId(snowflake.nextId());
        SysWorkSecretkey secretkey = sysWorkSecretkeyRepository.save(resources);
        return sysWorkSecretkeyMapper.toDto(secretkey);
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