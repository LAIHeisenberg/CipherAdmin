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
@Table(name="kms_cryptographic_object")
public class KmsCryptographicObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "密钥ID")
    private Long id;

    @Column(name = "`activation_date`")
    @ApiModelProperty(value = "激活日期")
    private Timestamp activationDate;

    @Column(name = "`compromise_date`")
    @ApiModelProperty(value = "系统意识到系统密钥泄漏日期")
    private Timestamp compromiseDate;

    @Column(name = "`compromise_occurrence_date`")
    @ApiModelProperty(value = "密钥泄漏发生日期 ")
    private Timestamp compromiseOccurrenceDate;

    @Column(name = "`deactivation_date`")
    @ApiModelProperty(value = "停用日期 ")
    private Timestamp deactivationDate;

    @Column(name = "`destroy_date`")
    @ApiModelProperty(value = "销毁日期")
    private Timestamp destroyDate;

    @Column(name = "`lease_time`")
    @ApiModelProperty(value = "租约时间")
    private Timestamp leaseTime;

    @Column(name = "`process_start_date`")
    @ApiModelProperty(value = "处理开始日期")
    private Timestamp processStartDate;

    @Column(name = "`protect_stop_date`")
    @ApiModelProperty(value = "保护停止时间")
    private Timestamp protectStopDate;

    @Column(name = "`revocation_reason_code`")
    @ApiModelProperty(value = "撤销原因")
    private String revocationReasonCode;

    @Column(name = "`revocation_message`")
    @ApiModelProperty(value = "撤销原因")
    private String revocationMessage;

    @Column(name = "`state`")
    @ApiModelProperty(value = "密钥状态(0:Pre-Active,1:Active,2: Deactivated,3:Compromised,4:Destroyed,5:Destroyed Compromised)")
    private Integer state;

    @Column(name = "`cryptographic_usage_mask`")
    @ApiModelProperty(value = "加密使用掩码")
    private String cryptographicUsageMask;

    @Column(name = "`key_block_id`")
    @ApiModelProperty(value = "key_block")
    private Long keyBlockId;

    @Column(name = "`create_by`")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @Column(name = "`update_by`")
    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @Column(name = "`create_time`")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "`update_time`")
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "`create_by_user_id`")
    @ApiModelProperty(value = "创建人id")
    private Long createByUserId;

    @Column(name = "`update_by_user_id`")
    @ApiModelProperty(value = "更新人id")
    private Long updateByUserId;

    public void copy(KmsCryptographicObject source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}