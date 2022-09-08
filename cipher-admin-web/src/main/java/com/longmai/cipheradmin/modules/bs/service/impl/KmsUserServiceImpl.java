package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.KmsUser;
import com.longmai.cipheradmin.utils.FileUtil;
import com.longmai.cipheradmin.utils.PageUtil;
import com.longmai.cipheradmin.utils.QueryHelp;
import com.longmai.cipheradmin.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.longmai.cipheradmin.modules.bs.repository.KmsUserRepository;
import com.longmai.cipheradmin.modules.bs.service.KmsUserService;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsUserDto;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsUserQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.KmsUserMapper;
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
public class KmsUserServiceImpl implements KmsUserService {

    private final KmsUserRepository kmsUserRepository;
    private final KmsUserMapper kmsUserMapper;

    @Override
    public Map<String,Object> queryAll(KmsUserQueryCriteria criteria, Pageable pageable){
        Page<KmsUser> page = kmsUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(kmsUserMapper::toDto));
    }

    @Override
    public List<KmsUserDto> queryAll(KmsUserQueryCriteria criteria){
        return kmsUserMapper.toDto(kmsUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public KmsUserDto findById(Long id) {
        KmsUser kmsUser = kmsUserRepository.findById(id).orElseGet(KmsUser::new);
        ValidationUtil.isNull(kmsUser.getId(),"KmsUser","id",id);
        return kmsUserMapper.toDto(kmsUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KmsUserDto create(KmsUser resources) {
        return kmsUserMapper.toDto(kmsUserRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(KmsUser resources) {
        KmsUser kmsUser = kmsUserRepository.findById(resources.getId()).orElseGet(KmsUser::new);
        ValidationUtil.isNull( kmsUser.getId(),"KmsUser","id",resources.getId());
        kmsUser.copy(resources);
        kmsUserRepository.save(kmsUser);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            kmsUserRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<KmsUserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (KmsUserDto kmsUser : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("业务用户名称,唯一不重复", kmsUser.getUserName());
            map.put("业务用户密码", kmsUser.getPwd());
            map.put("业务组ID", kmsUser.getGroupId());
            map.put("创建人", kmsUser.getCreateBy());
            map.put("创建时间", kmsUser.getCreateTime());
            map.put("更新人", kmsUser.getUpdateBy());
            map.put("更新时间", kmsUser.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}