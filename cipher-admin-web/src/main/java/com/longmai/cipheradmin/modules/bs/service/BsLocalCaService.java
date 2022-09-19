package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.domain.BsLocalCa;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCaDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCaQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务接口
* @author huangsi
* @date 2022-09-19
**/
public interface BsLocalCaService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(BsLocalCaQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<BsLocalCaDto>
    */
    List<BsLocalCaDto> queryAll(BsLocalCaQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return BsLocalCaDto
     */
    BsLocalCaDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return BsLocalCaDto
    */
    BsLocalCaDto create(BsLocalCa resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(BsLocalCa resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<BsLocalCaDto> all, HttpServletResponse response) throws IOException;
}