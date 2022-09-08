package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.BsGroup;
import com.longmai.cipheradmin.modules.bs.repository.BsGroupRepository;
import com.longmai.cipheradmin.modules.bs.service.BsGroupService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsGroupDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsGroupQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsGroupMapper;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
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
public class BsGroupServiceImpl implements BsGroupService {

    private final BsGroupRepository bsGroupRepository;
    private final BsGroupMapper bsGroupMapper;

    @Override
    public Map<String,Object> queryAll(BsGroupQueryCriteria criteria, Pageable pageable){
        Page<BsGroup> page = bsGroupRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bsGroupMapper::toDto));
    }

    @Override
    public List<BsGroupDto> queryAll(BsGroupQueryCriteria criteria){
        return bsGroupMapper.toDto(bsGroupRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsGroupDto findById(Long id) {
        BsGroup bsGroup = bsGroupRepository.findById(id).orElseGet(BsGroup::new);
        ValidationUtil.isNull(bsGroup.getId(),"BsGroup","id",id);
        return bsGroupMapper.toDto(bsGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsGroupDto create(BsGroup resources) {
        return bsGroupMapper.toDto(bsGroupRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsGroup resources) {
        BsGroup bsGroup = bsGroupRepository.findById(resources.getId()).orElseGet(BsGroup::new);
        ValidationUtil.isNull( bsGroup.getId(),"BsGroup","id",resources.getId());
        bsGroup.copy(resources);
        bsGroupRepository.save(bsGroup);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsGroupRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsGroupDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsGroupDto bsGroup : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("组名称", bsGroup.getName());
            map.put("创建人", bsGroup.getCreateBy());
            map.put("创建时间", bsGroup.getCreateTime());
            map.put("修改人", bsGroup.getUpdateBy());
            map.put("修改时间", bsGroup.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}