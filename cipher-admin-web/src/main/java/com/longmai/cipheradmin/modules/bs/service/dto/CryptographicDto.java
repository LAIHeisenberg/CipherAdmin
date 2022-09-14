package com.longmai.cipheradmin.modules.bs.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 加密对象
 */
@Data
public class CryptographicDto implements Serializable {

    private String secKeyName;
    private Integer seckeyLength;
    private Integer cryptAlgorithm;
    private String cryptAlgorithmName;
    private Integer objectType;
    private Integer cryptMask;


    private String userName;
    private String pwd;
}
