package com.longmai.cipheradmin.modules.bs.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author huangsi
* @date 2022-09-09
**/
@Entity
@Data
@Table(name="sys_root_secretkey")
public class SysRootSecretkey implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @Column(name = "`secretkey`")
    @ApiModelProperty(value = "secretkey")
    private String secretkey;

    @Column(name = "`key1`")
    @ApiModelProperty(value = "密码卡密钥")
    private String key1;

    @Column(name = "`key2`")
    @ApiModelProperty(value = "超级管理员创建时初始化")
    private String key2;

    public void copy(SysRootSecretkey source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
