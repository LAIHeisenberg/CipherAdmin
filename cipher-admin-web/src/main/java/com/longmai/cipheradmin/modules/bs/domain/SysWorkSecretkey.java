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
@Table(name="sys_work_secretkey")
public class SysWorkSecretkey implements Serializable {

    @Id
    @Column(name = "`id`")
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @Column(name = "`secretkey`")
    @ApiModelProperty(value = "secretkey")
    private String secretkey;

    @Column(name = "`cryptographic_algorithm`")
    @ApiModelProperty(value = "cryptographicAlgorithm")
    private String cryptographicAlgorithm;

    @Column(name = "`cryptographic_length`")
    @ApiModelProperty(value = "cryptographicLength")
    private Double cryptographicLength;

    public void copy(SysWorkSecretkey source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}