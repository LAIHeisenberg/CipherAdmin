package com.longmai.cipheradmin.modules.bs.param;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 密钥模板
 */
@Getter
@Setter
public class SecTemplateParam {

    /**
     * 密钥名称
     */
    private String secKeyName;
    /**
     * 加密长度
     */
    private Integer seckeyLength;
    /**
     * 加密算法
     */
    private Integer cryptAlgorithm;
    /**
     *
     */
    private Integer objectType;
    /**
     * 加密使用掩码
     */
    private Integer cryptMask;

    private List<KmipName> names;

    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;

    private Long templateId;

    private Long strategyId;
}
