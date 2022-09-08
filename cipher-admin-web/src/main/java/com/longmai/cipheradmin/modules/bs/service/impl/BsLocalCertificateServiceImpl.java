package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.BsLocalCertificate;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.BsLocalCertificateRepository;
import com.longmai.cipheradmin.modules.bs.service.BsLocalCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCertificateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCertificateQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsLocalCertificateMapper;
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
public class BsLocalCertificateServiceImpl implements BsLocalCertificateService {

    private final BsLocalCertificateRepository bsLocalCertificateRepository;
    private final BsLocalCertificateMapper bsLocalCertificateMapper;

    @Override
    public Map<String,Object> queryAll(BsLocalCertificateQueryCriteria criteria, Pageable pageable){
        Page<BsLocalCertificate> page = bsLocalCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bsLocalCertificateMapper::toDto));
    }

    @Override
    public List<BsLocalCertificateDto> queryAll(BsLocalCertificateQueryCriteria criteria){
        return bsLocalCertificateMapper.toDto(bsLocalCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsLocalCertificateDto findById(Long id) {
        BsLocalCertificate bsLocalCertificate = bsLocalCertificateRepository.findById(id).orElseGet(BsLocalCertificate::new);
        ValidationUtil.isNull(bsLocalCertificate.getId(),"BsLocalCertificate","id",id);
        return bsLocalCertificateMapper.toDto(bsLocalCertificate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsLocalCertificateDto create(BsLocalCertificate resources) {
        return bsLocalCertificateMapper.toDto(bsLocalCertificateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsLocalCertificate resources) {
        BsLocalCertificate bsLocalCertificate = bsLocalCertificateRepository.findById(resources.getId()).orElseGet(BsLocalCertificate::new);
        ValidationUtil.isNull( bsLocalCertificate.getId(),"BsLocalCertificate","id",resources.getId());
        bsLocalCertificate.copy(resources);
        bsLocalCertificateRepository.save(bsLocalCertificate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsLocalCertificateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsLocalCertificateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsLocalCertificateDto bsLocalCertificate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("ca 名称", bsLocalCertificate.getCertificateName());
            map.put("ca 证书密钥长度", bsLocalCertificate.getKeySize());
            map.put("ca 签发者", bsLocalCertificate.getCertificateIssuer());
            map.put("ca证书使用者", bsLocalCertificate.getCertificateSubject());
            map.put("ca 证书开始日期", bsLocalCertificate.getStartDate());
            map.put("ca 证书过期时间", bsLocalCertificate.getExpireDate());
            map.put("ca 证书状态(1:有效,2:过期,3:未知)", bsLocalCertificate.getCertificateStatus());
            map.put("ca 证书内容", bsLocalCertificate.getCertificateContent());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}