package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.BsLocalCa;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.BsLocalCaRepository;
import com.longmai.cipheradmin.modules.bs.service.BsLocalCaService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCaDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCaQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsLocalCaMapper;
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
public class BsLocalCaServiceImpl implements BsLocalCaService {

    private final BsLocalCaRepository bsLocalCaRepository;
    private final BsLocalCaMapper bsLocalCaMapper;

    @Override
    public Map<String,Object> queryAll(BsLocalCaQueryCriteria criteria, Pageable pageable){
        Page<BsLocalCa> page = bsLocalCaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bsLocalCaMapper::toDto));
    }

    @Override
    public List<BsLocalCaDto> queryAll(BsLocalCaQueryCriteria criteria){
        return bsLocalCaMapper.toDto(bsLocalCaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsLocalCaDto findById(Long id) {
        BsLocalCa bsLocalCa = bsLocalCaRepository.findById(id).orElseGet(BsLocalCa::new);
        ValidationUtil.isNull(bsLocalCa.getId(),"BsLocalCa","id",id);
        return bsLocalCaMapper.toDto(bsLocalCa);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsLocalCaDto create(BsLocalCa resources) {
        return bsLocalCaMapper.toDto(bsLocalCaRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsLocalCa resources) {
        BsLocalCa bsLocalCa = bsLocalCaRepository.findById(resources.getId()).orElseGet(BsLocalCa::new);
        ValidationUtil.isNull( bsLocalCa.getId(),"BsLocalCa","id",resources.getId());
        bsLocalCa.copy(resources);
        bsLocalCaRepository.save(bsLocalCa);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsLocalCaRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsLocalCaDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsLocalCaDto bsLocalCa : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("ca 名称", bsLocalCa.getCertificateName());
            map.put("ca 证书密钥长度", bsLocalCa.getKeySize());
            map.put("ca 签发者", bsLocalCa.getCertificateIssuer());
            map.put("ca证书使用者", bsLocalCa.getCertificateSubject());
            map.put("ca 证书开始日期", bsLocalCa.getStartDate());
            map.put("ca 证书过期时间", bsLocalCa.getExpireDate());
            map.put("ca 证书状态(1:有效,2:过期,3:未知)", bsLocalCa.getCertificateStatus());
            map.put("ca 证书内容", bsLocalCa.getCertificateContent());
            map.put("ca证书密钥", bsLocalCa.getPrivateKey());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}