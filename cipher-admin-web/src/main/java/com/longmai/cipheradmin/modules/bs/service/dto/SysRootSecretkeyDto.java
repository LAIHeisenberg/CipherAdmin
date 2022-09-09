package com.longmai.cipheradmin.modules.bs.service.dto;

import lombok.Data;
import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
* @website https://eladmin.vip
* @description /
* @author huangsi
* @date 2022-09-09
**/
@Data
public class SysRootSecretkeyDto implements Serializable {

    /** 主键ID */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    private String secretkey;

    /** 密码卡密钥 */
    private String key1;

    /** 超级管理员创建时初始化 */
    private String key2;
}