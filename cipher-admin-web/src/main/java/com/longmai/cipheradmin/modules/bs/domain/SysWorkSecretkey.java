package com.longmai.cipheradmin.modules.bs.domain;

import com.longmai.cipheradmin.base.BaseEntity;
import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @website https://eladmin.vip
* @description /
* @author huangsi
* @date 2022-09-09
**/
@Entity
@Data
@Table(name="sys_work_secretkey")
public class SysWorkSecretkey extends BaseEntity implements Serializable {

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
    private Integer cryptographicLength;

    @Column(name = "`uuidKey`")
    @ApiModelProperty(value = "uuidKey")
    private String uuidKey;

    @CreationTimestamp
    @Column(name = "destroy_time")
    @ApiModelProperty(value = "销毁时间", hidden = true)
    private Timestamp destroyTime;



    public void copy(SysWorkSecretkey source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}