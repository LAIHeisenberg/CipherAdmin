package com.longmai.cipheradmin.modules.bs.service.dto;

import com.longmai.cipheradmin.base.BaseDTO;
import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author huangsi
* @date 2022-09-08
**/
@Data
public class BsGroupDto extends BaseDTO implements Serializable {

    /** 组ID */
    private Long id;

    /** 组名称 */
    private String name;
}