package com.longmai.cipheradmin.modules.bs.service.impl;

import ch.ntb.inf.kmip.kmipenum.EnumCryptographicAlgorithm;
import cn.hutool.core.collection.CollectionUtil;
import com.longmai.cipheradmin.modules.bs.domain.BsTemplate;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import com.longmai.cipheradmin.utils.enums.CryptographicAlgorithmEnum;
import com.longmai.cipheradmin.utils.enums.CryptographicUsageMaskEnum;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.BsTemplateRepository;
import com.longmai.cipheradmin.modules.bs.service.BsTemplateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTemplateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTemplateQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsTemplateMapper;
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
public class BsTemplateServiceImpl implements BsTemplateService {

    private final BsTemplateRepository bsTemplateRepository;
    private final BsTemplateMapper bsTemplateMapper;

    @Override
    public Map<String,Object> queryAll(BsTemplateQueryCriteria criteria, Pageable pageable){
        Page<BsTemplate> page = bsTemplateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<BsTemplate> list = page.getContent();
        if(CollectionUtil.isNotEmpty(list)){
            for(BsTemplate template:list){
                template.setCryptographicAlgorithm(CryptographicAlgorithmEnum.find(Integer.valueOf(template.getCryptographicAlgorithm())).getDescription());
                template.setCryptographicUsageMask(CryptographicUsageMaskEnum.find(Integer.valueOf(template.getCryptographicUsageMask())).getDescription());
            }
        }
        return PageUtil.toPage(page.map(bsTemplateMapper::toDto));
    }

    @Override
    public List<BsTemplateDto> queryAll(BsTemplateQueryCriteria criteria){
        return bsTemplateMapper.toDto(bsTemplateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsTemplateDto findById(Long id) {
        BsTemplate bsTemplate = bsTemplateRepository.findById(id).orElseGet(BsTemplate::new);
        ValidationUtil.isNull(bsTemplate.getId(),"BsTemplate","id",id);
        return bsTemplateMapper.toDto(bsTemplate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsTemplateDto create(BsTemplate resources) {
        return bsTemplateMapper.toDto(bsTemplateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsTemplate resources) {
        BsTemplate bsTemplate = bsTemplateRepository.findById(resources.getId()).orElseGet(BsTemplate::new);
        ValidationUtil.isNull( bsTemplate.getId(),"BsTemplate","id",resources.getId());
        bsTemplate.copy(resources);
        bsTemplateRepository.save(bsTemplate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsTemplateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsTemplateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsTemplateDto bsTemplate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("加密算法", bsTemplate.getCryptographicAlgorithm());
            map.put("加密长度", bsTemplate.getCryptographicLength());
            map.put("加密使用掩码", bsTemplate.getCryptographicUsageMask());
            map.put("停用日期 ", bsTemplate.getDeactivationDate());
            map.put("操作策略名称", bsTemplate.getOperationPolicyName());
            map.put("处理开始日期", bsTemplate.getProcessStartDate());
            map.put("保护停止时间", bsTemplate.getProtectStopDate());
            map.put(" qlength",  bsTemplate.getQlength());
            map.put("推荐曲线", bsTemplate.getRecommendedCurve());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}