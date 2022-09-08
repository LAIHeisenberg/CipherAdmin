package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.BsTrustCertificate;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.BsTrustCertificateRepository;
import com.longmai.cipheradmin.modules.bs.service.BsTrustCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTrustCertificateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTrustCertificateQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsTrustCertificateMapper;
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
public class BsTrustCertificateServiceImpl implements BsTrustCertificateService {

    private final BsTrustCertificateRepository bsTrustCertificateRepository;
    private final BsTrustCertificateMapper bsTrustCertificateMapper;

    @Override
    public Map<String,Object> queryAll(BsTrustCertificateQueryCriteria criteria, Pageable pageable){
        Page<BsTrustCertificate> page = bsTrustCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bsTrustCertificateMapper::toDto));
    }

    @Override
    public List<BsTrustCertificateDto> queryAll(BsTrustCertificateQueryCriteria criteria){
        return bsTrustCertificateMapper.toDto(bsTrustCertificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsTrustCertificateDto findById(Long id) {
        BsTrustCertificate bsTrustCertificate = bsTrustCertificateRepository.findById(id).orElseGet(BsTrustCertificate::new);
        ValidationUtil.isNull(bsTrustCertificate.getId(),"BsTrustCertificate","id",id);
        return bsTrustCertificateMapper.toDto(bsTrustCertificate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsTrustCertificateDto create(BsTrustCertificate resources) {
        return bsTrustCertificateMapper.toDto(bsTrustCertificateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsTrustCertificate resources) {
        BsTrustCertificate bsTrustCertificate = bsTrustCertificateRepository.findById(resources.getId()).orElseGet(BsTrustCertificate::new);
        ValidationUtil.isNull( bsTrustCertificate.getId(),"BsTrustCertificate","id",resources.getId());
        bsTrustCertificate.copy(resources);
        bsTrustCertificateRepository.save(bsTrustCertificate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsTrustCertificateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsTrustCertificateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsTrustCertificateDto bsTrustCertificate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("可信ca 列表配置文件名称", bsTrustCertificate.getProfileName());
            map.put("本地ca 名称列表", bsTrustCertificate.getLocalCas());
            map.put("第三方ca名称列表", bsTrustCertificate.getKnownCas());
            map.put("keystore类型(JKS或者SWKS)", bsTrustCertificate.getKeyStoreType());
            map.put("keystore 内容", bsTrustCertificate.getKeyStore());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}