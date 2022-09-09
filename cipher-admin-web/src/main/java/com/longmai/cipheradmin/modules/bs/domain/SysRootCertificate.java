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
* @date 2022-09-09
**/
@Entity
@Data
@Table(name="sys_root_certificate")
public class SysRootCertificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`ca_id`")
    @ApiModelProperty(value = "ca id")
    private Long caId;

    @Column(name = "`certificate_name`")
    @ApiModelProperty(value = "证书名称")
    private String certificateName;

    @Column(name = "`key_size`")
    @ApiModelProperty(value = "证书密钥长度")
    private Integer keySize;

    @Column(name = "`certificate_issuer`")
    @ApiModelProperty(value = "证书签发者")
    private String certificateIssuer;

    @Column(name = "`certificate_subject`")
    @ApiModelProperty(value = "证书使用者")
    private String certificateSubject;

    @Column(name = "`start_date`")
    @ApiModelProperty(value = "证书开始时间")
    private Timestamp startDate;

    @Column(name = "`expire_date`")
    @ApiModelProperty(value = "证书过期时间")
    private Timestamp expireDate;

    @Column(name = "`certificate_status`")
    @ApiModelProperty(value = "证书状态(0:request pending证书请求,1:active证书有效,2:expired 证书过期,3:unknown未知)")
    private Integer certificateStatus;

    @Column(name = "`certificate_purpose`")
    @ApiModelProperty(value = "证书目的(0:证书请求,1:服务端认证,2:客户端认证,3:客户端和服务端认证)")
    private Integer certificatePurpose;

    @Column(name = "`certificate_content`")
    @ApiModelProperty(value = "证书内容")
    private String certificateContent;

    @Column(name = "`private_key`")
    @ApiModelProperty(value = "证书密钥")
    private String privateKey;

    @Column(name = "`keystore_type`")
    @ApiModelProperty(value = "keystore类型(JKS或者SWKS)")
    private String keystoreType;

    @Column(name = "`keystore_content`")
    @ApiModelProperty(value = "keystore 内容")
    private String keystoreContent;

    public void copy(SysRootCertificate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}