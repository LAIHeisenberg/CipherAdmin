package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.domain.SysWorkSecretkey;
import com.longmai.cipheradmin.modules.bs.service.dto.SysWorkSecretkeyDto;
import com.longmai.cipheradmin.modules.bs.service.dto.SysWorkSecretkeyQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务接口
* @author huangsi
* @date 2022-09-09
**/
public interface SysWorkSecretkeyService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(SysWorkSecretkeyQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<SysWorkSecretkeyDto>
    */
    List<SysWorkSecretkeyDto> queryAll(SysWorkSecretkeyQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return SysWorkSecretkeyDto
     */
    SysWorkSecretkeyDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return SysWorkSecretkeyDto
    */
    SysWorkSecretkeyDto create(SysWorkSecretkey resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(SysWorkSecretkey resources);

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
    void download(List<SysWorkSecretkeyDto> all, HttpServletResponse response) throws IOException;
}