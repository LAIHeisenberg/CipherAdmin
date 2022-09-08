package com.longmai.cipheradmin.modules.bs.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author huangsi
* @date 2022-09-08
**/
@Data
public class BsCertificateDto implements Serializable {

    private Long id;

    /** ca id */
    private Long caId;

    /** 证书名称 */
    private String certificateName;

    /** 证书密钥长度 */
    private Integer keySize;

    /** 证书签发者 */
    private String certificateIssuer;

    /** 证书使用者 */
    private String certificateSubject;

    /** 证书开始时间 */
    private Timestamp startDate;

    /** 证书过期时间 */
    private Timestamp expireDate;

    /** 证书状态(0:request pending证书请求,1:active证书有效,2:expired 证书过期,3:unknown未知) */
    private Integer certificateStatus;

    /** 证书目的(0:证书请求,1:服务端认证,2:客户端认证,3:客户端和服务端认证) */
    private Integer certificatePurpose;

    /** 证书内容 */
    private String certificateContent;

    /** 证书密钥 */
    private String privateKey;

    /** keystore类型(JKS或者SWKS) */
    private String keystoreType;

    /** keystore 内容 */
    private String keystoreContent;
}