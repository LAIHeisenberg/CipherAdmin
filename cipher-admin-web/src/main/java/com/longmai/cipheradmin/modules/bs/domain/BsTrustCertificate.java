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
@Table(name="bs_trust_certificate")
public class BsTrustCertificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Long id;

    @Column(name = "`profile_name`")
    @ApiModelProperty(value = "可信ca 列表配置文件名称")
    private String profileName;

    @Column(name = "`local_cas`")
    @ApiModelProperty(value = "本地ca 名称列表")
    private String localCas;

    @Column(name = "`known_cas`")
    @ApiModelProperty(value = "第三方ca名称列表")
    private String knownCas;

    @Column(name = "`key_store_type`")
    @ApiModelProperty(value = "keystore类型(JKS或者SWKS)")
    private String keyStoreType;

    @Column(name = "`key_store`")
    @ApiModelProperty(value = "keystore 内容")
    private String keyStore;

    public void copy(BsTrustCertificate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}