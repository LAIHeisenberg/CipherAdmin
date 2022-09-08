package com.longmai.cipheradmin.modules.bs.service.impl;

import com.longmai.cipheradmin.modules.bs.domain.BsStrategy;
import com.longmai.cipheradmin.modules.bs.repository.BsStrategyRepository;
import com.longmai.cipheradmin.modules.bs.service.BsStrategyService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsStrategyDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsStrategyQueryCriteria;
import com.longmai.cipheradmin.modules.bs.service.mapstruct.BsStrategyMapper;
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
public class BsStrategyServiceImpl implements BsStrategyService {

    private final BsStrategyRepository bsStrategyRepository;
    private final BsStrategyMapper bsStrategyMapper;

    @Override
    public Map<String,Object> queryAll(BsStrategyQueryCriteria criteria, Pageable pageable){
        Page<BsStrategy> page = bsStrategyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(bsStrategyMapper::toDto));
    }

    @Override
    public List<BsStrategyDto> queryAll(BsStrategyQueryCriteria criteria){
        return bsStrategyMapper.toDto(bsStrategyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BsStrategyDto findById(Long id) {
        BsStrategy bsStrategy = bsStrategyRepository.findById(id).orElseGet(BsStrategy::new);
        ValidationUtil.isNull(bsStrategy.getId(),"BsStrategy","id",id);
        return bsStrategyMapper.toDto(bsStrategy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BsStrategyDto create(BsStrategy resources) {
        return bsStrategyMapper.toDto(bsStrategyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BsStrategy resources) {
        BsStrategy bsStrategy = bsStrategyRepository.findById(resources.getId()).orElseGet(BsStrategy::new);
        ValidationUtil.isNull( bsStrategy.getId(),"BsStrategy","id",resources.getId());
        bsStrategy.copy(resources);
        bsStrategyRepository.save(bsStrategy);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            bsStrategyRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<BsStrategyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BsStrategyDto bsStrategy : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("策略名称", bsStrategy.getPolicyName());
            map.put(" propertys",  bsStrategy.getPropertys());
            map.put(" startDay",  bsStrategy.getStartDay());
            map.put(" startHour",  bsStrategy.getStartHour());
            map.put(" startMin",  bsStrategy.getStartMin());
            map.put(" endDay",  bsStrategy.getEndDay());
            map.put(" endHour",  bsStrategy.getEndHour());
            map.put(" endMin",  bsStrategy.getEndMin());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}