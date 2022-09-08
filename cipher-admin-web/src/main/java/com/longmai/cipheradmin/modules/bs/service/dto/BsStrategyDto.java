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
public class BsStrategyDto implements Serializable {

    /** 策略ID */
    private Long id;

    /** 策略名称 */
    private String policyName;

    private Integer propertys;

    private Integer startDay;

    private Integer startHour;

    private Integer startMin;

    private Integer endDay;

    private Integer endHour;

    private Integer endMin;
}