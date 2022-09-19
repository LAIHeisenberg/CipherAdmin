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
public class BsLocalCaDto implements Serializable {

    private Long id;

    /** ca 名称 */
    private String certificateName;

    /** ca 证书密钥长度 */
    private Integer keySize;

    /** ca 签发者 */
    private String certificateIssuer;

    /** ca证书使用者 */
    private String certificateSubject;

    /** ca 证书开始日期 */
    private Timestamp startDate;

    /** ca 证书过期时间 */
    private Timestamp expireDate;

    /** ca 证书状态(1:有效,2:过期,3:未知) */
    private Integer certificateStatus;

    /** ca 证书内容 */
    private String certificateContent;

    /** ca证书密钥 */
    private String privateKey;
}