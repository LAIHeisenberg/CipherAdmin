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
public class KmsCryptographicObjectDto implements Serializable {

    /** 密钥ID */
    private Long id;

    /** 激活日期 */
    private Timestamp activationDate;

    /** 系统意识到系统密钥泄漏日期 */
    private Timestamp compromiseDate;

    /** 密钥泄漏发生日期  */
    private Timestamp compromiseOccurrenceDate;

    /** 停用日期  */
    private Timestamp deactivationDate;

    /** 销毁日期 */
    private Timestamp destroyDate;

    /** 租约时间 */
    private Timestamp leaseTime;

    /** 处理开始日期 */
    private Timestamp processStartDate;

    /** 保护停止时间 */
    private Timestamp protectStopDate;

    /** 撤销原因 */
    private String revocationReasonCode;

    /** 撤销原因 */
    private String revocationMessage;

    /** 密钥状态(0:Pre-Active,1:Active,2: Deactivated,3:Compromised,4:Destroyed,5:Destroyed Compromised) */
    private Integer state;

    /** 加密使用掩码 */
    private String cryptographicUsageMask;

    /** key_block */
    private Long keyBlockId;

    /** 创建人 */
    private String createBy;

    /** 更新人 */
    private String updateBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 创建人id */
    private Long createByUserId;

    /** 更新人id */
    private Long updateByUserId;
}