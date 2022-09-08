package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.domain.BsCertificate;
import com.longmai.cipheradmin.modules.bs.service.dto.BsCertificateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsCertificateQueryCriteria;
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
public interface BsCertificateService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(BsCertificateQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<BsCertificateDto>
    */
    List<BsCertificateDto> queryAll(BsCertificateQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return BsCertificateDto
     */
    BsCertificateDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return BsCertificateDto
    */
    BsCertificateDto create(BsCertificate resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(BsCertificate resources);

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
    void download(List<BsCertificateDto> all, HttpServletResponse response) throws IOException;
}