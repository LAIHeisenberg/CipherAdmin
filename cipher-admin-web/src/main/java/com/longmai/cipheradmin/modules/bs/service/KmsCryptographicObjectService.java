package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.domain.KmsCryptographicObject;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectDto;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务接口
* @author huangsi
* @date 2022-09-08
**/
public interface KmsCryptographicObjectService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(KmsCryptographicObjectQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<KmsCryptographicObjectDto>
    */
    List<KmsCryptographicObjectDto> queryAll(KmsCryptographicObjectQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return KmsCryptographicObjectDto
     */
    KmsCryptographicObjectDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return KmsCryptographicObjectDto
    */
    KmsCryptographicObjectDto create(KmsCryptographicObject resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(KmsCryptographicObject resources);

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
    void download(List<KmsCryptographicObjectDto> all, HttpServletResponse response) throws IOException;
}