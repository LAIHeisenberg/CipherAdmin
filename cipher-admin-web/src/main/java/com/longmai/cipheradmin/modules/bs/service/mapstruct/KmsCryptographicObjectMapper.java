package com.longmai.cipheradmin.modules.bs.service.mapstruct;

import com.longmai.cipheradmin.base.BaseMapper;
import com.longmai.cipheradmin.modules.bs.domain.KmsCryptographicObject;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-08
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KmsCryptographicObjectMapper extends BaseMapper<KmsCryptographicObjectDto, KmsCryptographicObject> {

}