package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.BsKnownCa;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.BsKnownCaRepository;
import com.longmai.cipheradmin.modules.bs.service.BsKnownCaService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsKnownCaDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsKnownCaQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsKnownCaMapper;
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
* @date 2022-09-19
**/
@Service
@RequiredArgsConstructor
public class BsKnownCaServiceImpl implements BsKnownCaService {

    private final BsKnownCaRepository bsKnownCaRepository;
    private final BsKnownCaMapper bsKnownCaMapper;

    @Override
    public Map<String,Object> queryAll(BsKnownCaQueryCriteria criteria, Pageable pageable){
        Page<BsKnownCa> page = bsKnownCaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bsKnownCaMapper::toDto));
    }

    @Override
    public List<BsKnownCaDto> queryAll(BsKnownCaQueryCriteria criteria){
        return bsKnownCaMapper.toDto(bsKnownCaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsKnownCaDto findById(Long id) {
        BsKnownCa bsKnownCa = bsKnownCaRepository.findById(id).orElseGet(BsKnownCa::new);
        ValidationUtil.isNull(bsKnownCa.getId(),"BsKnownCa","id",id);
        return bsKnownCaMapper.toDto(bsKnownCa);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsKnownCaDto create(BsKnownCa resources) {
        return bsKnownCaMapper.toDto(bsKnownCaRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsKnownCa resources) {
        BsKnownCa bsKnownCa = bsKnownCaRepository.findById(resources.getId()).orElseGet(BsKnownCa::new);
        ValidationUtil.isNull( bsKnownCa.getId(),"BsKnownCa","id",resources.getId());
        bsKnownCa.copy(resources);
        bsKnownCaRepository.save(bsKnownCa);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsKnownCaRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsKnownCaDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsKnownCaDto bsKnownCa : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("ca 名称", bsKnownCa.getCertificateName());
            map.put("ca 证书密钥长度", bsKnownCa.getKeySize());
            map.put("ca 签发者", bsKnownCa.getCertificateIssuer());
            map.put("ca证书使用者", bsKnownCa.getCertificateSubject());
            map.put("ca 证书开始日期", bsKnownCa.getStartDate());
            map.put("ca 证书过期时间", bsKnownCa.getExpireDate());
            map.put("ca 证书状态(1:有效,2:过期,3:未知)", bsKnownCa.getCertificateStatus());
            map.put("ca 证书内容", bsKnownCa.getCertificateContent());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}