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
**/
@Entity
@Data
@Table(name="bs_group")
public class BsGroup extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "组ID")
    private Long id;

    @Column(name = "`name`")
    @ApiModelProperty(value = "组名称")
    private String name;

    public void copy(BsGroup source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}