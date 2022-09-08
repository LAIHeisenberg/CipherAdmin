package com.longmai.cipheradmin.modules.bs.domain;

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
@Table(name="bs_template")
public class BsTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`cryptographic_algorithm`")
    @ApiModelProperty(value = "加密算法")
    private String cryptographicAlgorithm;

    @Column(name = "`cryptographic_length`")
    @ApiModelProperty(value = "加密长度")
    private String cryptographicLength;

    @Column(name = "`cryptographic_usage_mask`")
    @ApiModelProperty(value = "加密使用掩码")
    private String cryptographicUsageMask;

    @Column(name = "`deactivation_date`")
    @ApiModelProperty(value = "停用日期 ")
    private Timestamp deactivationDate;

    @Column(name = "`operation_policy_name`")
    @ApiModelProperty(value = "操作策略名称")
    private String operationPolicyName;

    @Column(name = "` process_start_date`")
    @ApiModelProperty(value = "处理开始日期")
    private Timestamp  processStartDate;

    @Column(name = "`protect_stop_date`")
    @ApiModelProperty(value = "保护停止时间")
    private Timestamp protectStopDate;

    @Column(name = "`qlength`")
    @ApiModelProperty(value = "qlength")
    private String qlength;

    @Column(name = "`recommended_curve`")
    @ApiModelProperty(value = "推荐曲线")
    private String recommendedCurve;

    public void copy(BsTemplate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}