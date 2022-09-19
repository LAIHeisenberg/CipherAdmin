package com.longmai.cipheradmin.modules.bs.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.hash.Hash;
import com.longmai.cipheradmin.exception.BadRequestException;
import com.longmai.cipheradmin.exception.EntityExistException;
import com.longmai.cipheradmin.exception.EntityNotFoundException;
import com.longmai.cipheradmin.modules.bs.domain.BsGroup;
import com.longmai.cipheradmin.modules.bs.repository.BsGroupRepository;
import com.longmai.cipheradmin.modules.bs.service.BsGroupService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsGroupDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsGroupQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsGroupMapper;
import com.longmai.cipheradmin.modules.system.domain.Menu;
import com.longmai.cipheradmin.modules.system.domain.Role;
import com.longmai.cipheradmin.utils.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.SetUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

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
        String name = resources.getName();
        if(StringUtils.isBlank(name)){
            throw new BadRequestException("用户组不能为空");
        }
        BsGroup group = bsGroupRepository.findByName(name);
        if(Objects.nonNull(group)){
            throw new EntityExistException(BsGroup.class,"name",resources.getName());
        }
        return bsGroupMapper.toDto(bsGroupRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsGroup resources) {
        BsGroup bsGroup = bsGroupRepository.findById(resources.getId()).orElseGet(BsGroup::new);
        ValidationUtil.isNull( bsGroup.getId(),"BsGroup","id",resources.getId());

        BsGroup group1 = bsGroupRepository.findByName(bsGroup.getName());

        if (group1 != null && !group1.getId().equals(resources.getId())) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }
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
    public void verification(Long[] ids) {
        Set<Long> idSet = new HashSet<>(Arrays.asList(ids));
        if (bsGroupRepository.countByGroupIds(idSet) > 0) {
            throw new BadRequestException("所选组存在用户关联，请解除关联再试！");
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