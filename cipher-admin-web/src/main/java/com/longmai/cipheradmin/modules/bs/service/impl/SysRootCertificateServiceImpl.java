package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.SysRootCertificate;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.SysRootCertificateRepository;
import com.longmai.cipheradmin.modules.bs.service.SysRootCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.SysRootCertificateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.SysRootCertificateQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.SysRootCertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class SysRootCertificateServiceImpl implements SysRootCertificateService {

    private final SysRootCertificateRepository sysRootCertificateRepository;
    private final SysRootCertificateMapper sysRootCertificateMapper;

    @Override
    public Map<String,Object> queryAll(SysRootCertificateQueryCriteria criteria, Pageable pageable){
        Page<SysRootCertificate> page = sysRootCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysRootCertificateMapper::toDto));
    }

    @Override
    public List<SysRootCertificateDto> queryAll(SysRootCertificateQueryCriteria criteria){
        return sysRootCertificateMapper.toDto(sysRootCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SysRootCertificateDto findById(Long id) {
        SysRootCertificate sysRootCertificate = sysRootCertificateRepository.findById(id).orElseGet(SysRootCertificate::new);
        ValidationUtil.isNull(sysRootCertificate.getId(),"SysRootCertificate","id",id);
        return sysRootCertificateMapper.toDto(sysRootCertificate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRootCertificateDto create(SysRootCertificate resources) {
        return sysRootCertificateMapper.toDto(sysRootCertificateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRootCertificate resources) {
        SysRootCertificate sysRootCertificate = sysRootCertificateRepository.findById(resources.getId()).orElseGet(SysRootCertificate::new);
        ValidationUtil.isNull( sysRootCertificate.getId(),"SysRootCertificate","id",resources.getId());
        sysRootCertificate.copy(resources);
        sysRootCertificateRepository.save(sysRootCertificate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            sysRootCertificateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SysRootCertificateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysRootCertificateDto sysRootCertificate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("ca id", sysRootCertificate.getCaId());
            map.put("证书名称", sysRootCertificate.getCertificateName());
            map.put("证书密钥长度", sysRootCertificate.getKeySize());
            map.put("证书签发者", sysRootCertificate.getCertificateIssuer());
            map.put("证书使用者", sysRootCertificate.getCertificateSubject());
            map.put("证书开始时间", sysRootCertificate.getStartDate());
            map.put("证书过期时间", sysRootCertificate.getExpireDate());
            map.put("证书状态(0:request pending证书请求,1:active证书有效,2:expired 证书过期,3:unknown未知)", sysRootCertificate.getCertificateStatus());
            map.put("证书目的(0:证书请求,1:服务端认证,2:客户端认证,3:客户端和服务端认证)", sysRootCertificate.getCertificatePurpose());
            map.put("证书内容", sysRootCertificate.getCertificateContent());
            map.put("证书密钥", sysRootCertificate.getPrivateKey());
            map.put("keystore类型(JKS或者SWKS)", sysRootCertificate.getKeystoreType());
            map.put("keystore 内容", sysRootCertificate.getKeystoreContent());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}