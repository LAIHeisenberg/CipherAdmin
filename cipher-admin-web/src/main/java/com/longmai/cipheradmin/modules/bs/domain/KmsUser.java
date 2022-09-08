package com.longmai.cipheradmin.modules.bs.domain;

import com.longmai.cipheradmin.base.BaseEntity;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author huangsi
* @date 2022-09-08
**/
@Entity
@Data
@Table(name="kms_user")
public class KmsUser extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "业务用户ID")
    private Long id;

    @Column(name = "`user_name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "业务用户名称,唯一不重复")
    private String userName;

    @Column(name = "`pwd`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "业务用户密码")
    private String pwd;

    @Column(name = "`group_id`")
    @ApiModelProperty(value = "业务组ID")
    private Long groupId;

    public void copy(KmsUser source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}