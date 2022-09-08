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
public class KmsUserDto extends BaseDTO implements Serializable {

    /** 业务用户ID */
    private Long id;

    /** 业务用户名称,唯一不重复 */
    private String userName;

    /** 业务用户密码 */
    private String pwd;

    /** 业务组ID */
    private Long groupId;
}