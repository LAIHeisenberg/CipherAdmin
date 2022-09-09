package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.KmsCryptographicObject;
import com.longmai.cipheradmin.modules.bs.repository.KmsCryptographicObjectRepository;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.service.KmsCryptographicObjectService;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectDto;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.KmsCryptographicObjectMapper;
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
public class KmsCryptographicObjectServiceImpl implements KmsCryptographicObjectService {

    private final KmsCryptographicObjectRepository kmsCryptographicObjectRepository;
    private final KmsCryptographicObjectMapper kmsCryptographicObjectMapper;

    @Override
    public Map<String,Object> queryAll(KmsCryptographicObjectQueryCriteria criteria, Pageable pageable){
        Page<KmsCryptographicObject> page = kmsCryptographicObjectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(kmsCryptographicObjectMapper::toDto));
    }

    @Override
    public List<KmsCryptographicObjectDto> queryAll(KmsCryptographicObjectQueryCriteria criteria){
        return kmsCryptographicObjectMapper.toDto(kmsCryptographicObjectRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public KmsCryptographicObjectDto findById(Long id) {
        KmsCryptographicObject kmsCryptographicObject = kmsCryptographicObjectRepository.findById(id).orElseGet(KmsCryptographicObject::new);
        ValidationUtil.isNull(kmsCryptographicObject.getId(),"KmsCryptographicObject","id",id);
        return kmsCryptographicObjectMapper.toDto(kmsCryptographicObject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KmsCryptographicObjectDto create(KmsCryptographicObject resources) {
        return kmsCryptographicObjectMapper.toDto(kmsCryptographicObjectRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(KmsCryptographicObject resources) {
        KmsCryptographicObject kmsCryptographicObject = kmsCryptographicObjectRepository.findById(resources.getId()).orElseGet(KmsCryptographicObject::new);
        ValidationUtil.isNull( kmsCryptographicObject.getId(),"KmsCryptographicObject","id",resources.getId());
        kmsCryptographicObject.copy(resources);
        kmsCryptographicObjectRepository.save(kmsCryptographicObject);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            kmsCryptographicObjectRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<KmsCryptographicObjectDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (KmsCryptographicObjectDto kmsCryptographicObject : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("激活日期", kmsCryptographicObject.getActivationDate());
            map.put("系统意识到系统密钥泄漏日期", kmsCryptographicObject.getCompromiseDate());
            map.put("密钥泄漏发生日期 ", kmsCryptographicObject.getCompromiseOccurrenceDate());
            map.put("停用日期 ", kmsCryptographicObject.getDeactivationDate());
            map.put("销毁日期", kmsCryptographicObject.getDestroyDate());
            map.put("租约时间", kmsCryptographicObject.getLeaseTime());
            map.put("处理开始日期", kmsCryptographicObject.getProcessStartDate());
            map.put("保护停止时间", kmsCryptographicObject.getProtectStopDate());
            map.put("撤销原因", kmsCryptographicObject.getRevocationReasonCode());
            map.put("撤销原因", kmsCryptographicObject.getRevocationMessage());
            map.put("密钥状态(0:Pre-Active,1:Active,2: Deactivated,3:Compromised,4:Destroyed,5:Destroyed Compromised)", kmsCryptographicObject.getState());
            map.put("加密使用掩码", kmsCryptographicObject.getCryptographicUsageMask());
            map.put("key_block", kmsCryptographicObject.getKeyBlockId());
            map.put("创建人", kmsCryptographicObject.getCreateBy());
            map.put("更新人", kmsCryptographicObject.getUpdateBy());
            map.put("创建时间", kmsCryptographicObject.getCreateTime());
            map.put("更新时间", kmsCryptographicObject.getUpdateTime());
            map.put("创建人id", kmsCryptographicObject.getCreateByUserId());
            map.put("更新人id", kmsCryptographicObject.getUpdateByUserId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}