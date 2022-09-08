package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.BsKnownCertificate;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.BsKnownCertificateRepository;
import com.longmai.cipheradmin.modules.bs.service.BsKnownCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsKnownCertificateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsKnownCertificateQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsKnownCertificateMapper;
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
public class BsKnownCertificateServiceImpl implements BsKnownCertificateService {

    private final BsKnownCertificateRepository bsKnownCertificateRepository;
    private final BsKnownCertificateMapper bsKnownCertificateMapper;

    @Override
    public Map<String,Object> queryAll(BsKnownCertificateQueryCriteria criteria, Pageable pageable){
        Page<BsKnownCertificate> page = bsKnownCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bsKnownCertificateMapper::toDto));
    }

    @Override
    public List<BsKnownCertificateDto> queryAll(BsKnownCertificateQueryCriteria criteria){
        return bsKnownCertificateMapper.toDto(bsKnownCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsKnownCertificateDto findById(Long id) {
        BsKnownCertificate bsKnownCertificate = bsKnownCertificateRepository.findById(id).orElseGet(BsKnownCertificate::new);
        ValidationUtil.isNull(bsKnownCertificate.getId(),"BsKnownCertificate","id",id);
        return bsKnownCertificateMapper.toDto(bsKnownCertificate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsKnownCertificateDto create(BsKnownCertificate resources) {
        return bsKnownCertificateMapper.toDto(bsKnownCertificateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsKnownCertificate resources) {
        BsKnownCertificate bsKnownCertificate = bsKnownCertificateRepository.findById(resources.getId()).orElseGet(BsKnownCertificate::new);
        ValidationUtil.isNull( bsKnownCertificate.getId(),"BsKnownCertificate","id",resources.getId());
        bsKnownCertificate.copy(resources);
        bsKnownCertificateRepository.save(bsKnownCertificate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsKnownCertificateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsKnownCertificateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsKnownCertificateDto bsKnownCertificate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("ca 名称", bsKnownCertificate.getCertificateName());
            map.put("ca 证书密钥长度", bsKnownCertificate.getKeySize());
            map.put("ca 签发者", bsKnownCertificate.getCertificateIssuer());
            map.put("ca证书使用者", bsKnownCertificate.getCertificateSubject());
            map.put("ca 证书开始日期", bsKnownCertificate.getStartDate());
            map.put("ca 证书过期时间", bsKnownCertificate.getExpireDate());
            map.put("ca 证书状态(1:有效,2:过期,3:未知)", bsKnownCertificate.getCertificateStatus());
            map.put("ca 证书内容", bsKnownCertificate.getCertificateContent());
            map.put("ca证书密钥", bsKnownCertificate.getPrivateKey());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}