package com.longmai.cipheradmin.modules.bs.service.dto;

import com.longmai.cipheradmin.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-08
**/
@Data
public class BsGroupQueryCriteria{
    @Query(type = Query.Type.INNER_LIKE)
    private String name;
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}