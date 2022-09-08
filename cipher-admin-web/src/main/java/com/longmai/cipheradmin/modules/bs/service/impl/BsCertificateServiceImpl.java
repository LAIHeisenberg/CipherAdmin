package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.BsCertificate;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.BsCertificateRepository;
import com.longmai.cipheradmin.modules.bs.service.BsCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsCertificateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsCertificateQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsCertificateMapper;
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
* @date 2022-09-08
**/
@Service
@RequiredArgsConstructor
public class BsCertificateServiceImpl implements BsCertificateService {

    private final BsCertificateRepository bsCertificateRepository;
    private final BsCertificateMapper bsCertificateMapper;

    @Override
    public Map<String,Object> queryAll(BsCertificateQueryCriteria criteria, Pageable pageable){
        Page<BsCertificate> page = bsCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bsCertificateMapper::toDto));
    }

    @Override
    public List<BsCertificateDto> queryAll(BsCertificateQueryCriteria criteria){
        return bsCertificateMapper.toDto(bsCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsCertificateDto findById(Long id) {
        BsCertificate bsCertificate = bsCertificateRepository.findById(id).orElseGet(BsCertificate::new);
        ValidationUtil.isNull(bsCertificate.getId(),"BsCertificate","id",id);
        return bsCertificateMapper.toDto(bsCertificate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsCertificateDto create(BsCertificate resources) {
        return bsCertificateMapper.toDto(bsCertificateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsCertificate resources) {
        BsCertificate bsCertificate = bsCertificateRepository.findById(resources.getId()).orElseGet(BsCertificate::new);
        ValidationUtil.isNull( bsCertificate.getId(),"BsCertificate","id",resources.getId());
        bsCertificate.copy(resources);
        bsCertificateRepository.save(bsCertificate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsCertificateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsCertificateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsCertificateDto bsCertificate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("ca id", bsCertificate.getCaId());
            map.put("证书名称", bsCertificate.getCertificateName());
            map.put("证书密钥长度", bsCertificate.getKeySize());
            map.put("证书签发者", bsCertificate.getCertificateIssuer());
            map.put("证书使用者", bsCertificate.getCertificateSubject());
            map.put("证书开始时间", bsCertificate.getStartDate());
            map.put("证书过期时间", bsCertificate.getExpireDate());
            map.put("证书状态(0:request pending证书请求,1:active证书有效,2:expired 证书过期,3:unknown未知)", bsCertificate.getCertificateStatus());
            map.put("证书目的(0:证书请求,1:服务端认证,2:客户端认证,3:客户端和服务端认证)", bsCertificate.getCertificatePurpose());
            map.put("证书内容", bsCertificate.getCertificateContent());
            map.put("证书密钥", bsCertificate.getPrivateKey());
            map.put("keystore类型(JKS或者SWKS)", bsCertificate.getKeystoreType());
            map.put("keystore 内容", bsCertificate.getKeystoreContent());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}