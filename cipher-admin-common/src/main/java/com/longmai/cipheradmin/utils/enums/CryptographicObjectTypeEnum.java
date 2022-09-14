package com.longmai.cipheradmin.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 加密类型枚举
 */
@AllArgsConstructor
@Getter
public enum CryptographicObjectTypeEnum {
    Default(0,"Default"),
    Certificate(1,"Certificate"),
    SymmetricKey(2,"SymmetricKey"),
    PublicKey(3,"PublicKey"),
    PrivateKey(4,"SplitKey"),
    SplitKey(5,"SplitKey"),
    Template(6,"Template"),
    SecretData(7,"SecretData"),
    OpaqueObject(8,"OpaqueObject");

    private final Integer code;
    private final String description;

    public static CryptographicObjectTypeEnum find(Integer code) {
        for (CryptographicObjectTypeEnum value : CryptographicObjectTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
