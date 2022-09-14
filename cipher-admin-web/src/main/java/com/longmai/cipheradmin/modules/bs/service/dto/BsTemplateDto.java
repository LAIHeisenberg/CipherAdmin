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
public class BsTemplateDto implements Serializable {

    private Long id;

    /** 加密算法 */
    private String cryptographicAlgorithm;

    /** 加密长度 */
    private String cryptographicLength;

    /** 加密使用掩码 */
    private String cryptographicUsageMask;

    /** 停用日期  */
    private Timestamp deactivationDate;

    /** 操作策略名称 */
    private String operationPolicyName;

    /** 处理开始日期 */
    private Timestamp  processStartDate;

    /** 保护停止时间 */
    private Timestamp protectStopDate;

    private String qlength;

    /** 推荐曲线 */
    private String recommendedCurve;

    private String userId;

    private String pwd;

    private String uuidKey;
}