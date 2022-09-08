package com.longmai.cipheradmin.modules.bs.service;

import com.longmai.cipheradmin.modules.bs.domain.BsLocalCertificate;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCertificateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCertificateQueryCriteria;
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
public interface BsLocalCertificateService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(BsLocalCertificateQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<BsLocalCertificateDto>
    */
    List<BsLocalCertificateDto> queryAll(BsLocalCertificateQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return BsLocalCertificateDto
     */
    BsLocalCertificateDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return BsLocalCertificateDto
    */
    BsLocalCertificateDto create(BsLocalCertificate resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(BsLocalCertificate resources);

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
    void download(List<BsLocalCertificateDto> all, HttpServletResponse response) throws IOException;
}