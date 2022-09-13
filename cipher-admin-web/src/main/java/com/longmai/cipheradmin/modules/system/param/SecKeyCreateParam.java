package com.longmai.cipheradmin.modules.system.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecKeyCreateParam {

    private String secKeyName;
    private Integer seckeyLength;
    private Integer cryptAlgorithm;
    private Integer objectType;
    private Integer cryptMask;

}
