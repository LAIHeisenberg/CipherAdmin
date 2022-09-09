package com.longmai.cipheradmin.modules.bs.service.mapstruct;

import com.longmai.cipheradmin.base.BaseMapper;
import com.longmai.cipheradmin.modules.bs.domain.SysRootCertificate;
import com.longmai.cipheradmin.modules.bs.service.dto.SysRootCertificateDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-09
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRootCertificateMapper extends BaseMapper<SysRootCertificateDto, SysRootCertificate> {

}