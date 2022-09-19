package com.longmai.cipheradmin.modules.bs.param;

import lombok.Data;

@Data
public class SecKeyImportParam {

    /**
     * ObjectType
     */
    private Integer objectType;
    /**
     * 对称密钥
     */
    private String secKey;
    /**
     * 私密密钥
     */
    private String privateKey;
    /**
     * 公钥密钥
     */
    private String publicKey;
    /**
     * 加密长度
     */
    private Integer seckeyLength;
    /**
     * 加密算法
     */
    private Integer cryptAlgorithm;
    /**
     * 加密密钥
     */
    private Integer cryptographicUsageMask;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
}
