package com.longmai.cipheradmin.modules.bs.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author huangsi
* @date 2022-09-08
**/
@Data
public class BsTrustCertificateDto implements Serializable {

    private Long id;

    /** 可信ca 列表配置文件名称 */
    private String profileName;

    /** 本地ca 名称列表 */
    private String localCas;

    /** 第三方ca名称列表 */
    private String knownCas;

    /** keystore类型(JKS或者SWKS) */
    private String keyStoreType;

    /** keystore 内容 */
    private String keyStore;
}