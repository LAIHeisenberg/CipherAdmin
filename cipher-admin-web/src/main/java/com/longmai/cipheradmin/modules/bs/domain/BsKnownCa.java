package com.longmai.cipheradmin.modules.bs.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
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
@Table(name="bs_known_ca")
public class BsKnownCa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`certificate_name`")
    @ApiModelProperty(value = "ca 名称")
    private String certificateName;

    @Column(name = "`key_size`")
    @ApiModelProperty(value = "ca 证书密钥长度")
    private Integer keySize;

    @Column(name = "`certificate_issuer`")
    @ApiModelProperty(value = "ca 签发者")
    private String certificateIssuer;

    @Column(name = "`certificate_subject`")
    @ApiModelProperty(value = "ca证书使用者")
    private String certificateSubject;

    @Column(name = "`start_date`")
    @ApiModelProperty(value = "ca 证书开始日期")
    private Timestamp startDate;

    @Column(name = "`expire_date`")
    @ApiModelProperty(value = "ca 证书过期时间")
    private Timestamp expireDate;

    @Column(name = "`certificate_status`")
    @ApiModelProperty(value = "ca 证书状态(1:有效,2:过期,3:未知)")
    private Integer certificateStatus;

    @Column(name = "`certificate_content`")
    @ApiModelProperty(value = "ca 证书内容")
    private String certificateContent;

    public void copy(BsKnownCa source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}