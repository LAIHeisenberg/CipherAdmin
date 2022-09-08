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
* @date 2022-09-08
**/
@Entity
@Data
@Table(name="bs_strategy")
public class BsStrategy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "策略ID")
    private Long id;

    @Column(name = "`policy_name`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "策略名称")
    private String policyName;

    @Column(name = "`propertys`")
    @ApiModelProperty(value = "propertys")
    private Integer propertys;

    @Column(name = "`start_day`")
    @ApiModelProperty(value = "startDay")
    private Integer startDay;

    @Column(name = "`start_hour`")
    @ApiModelProperty(value = "startHour")
    private Integer startHour;

    @Column(name = "`start_min`")
    @ApiModelProperty(value = "startMin")
    private Integer startMin;

    @Column(name = "`end_day`")
    @ApiModelProperty(value = "endDay")
    private Integer endDay;

    @Column(name = "`end_hour`")
    @ApiModelProperty(value = "endHour")
    private Integer endHour;

    @Column(name = "`end_min`")
    @ApiModelProperty(value = "endMin")
    private Integer endMin;

    public void copy(BsStrategy source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}