package com.longmai.cipheradmin.modules.bs.service.mapstruct;

import com.longmai.cipheradmin.base.BaseMapper;
import com.longmai.cipheradmin.modules.bs.domain.BsKnownCa;
import com.longmai.cipheradmin.modules.bs.service.dto.BsKnownCaDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-08
**/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BsKnownCaMapper extends BaseMapper<BsKnownCaDto, BsKnownCa> {

}