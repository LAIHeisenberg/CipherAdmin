package com.longmai.cipheradmin.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptographicStateEnum {

    Default(0, "Default"),
    PreActive(1, "PreActive"),
    Active(2, "Active"),
    Deactivated(3, "Deactivated"),
    Compromised(4, "Compromised"),
    Destroyed(5, "Destroyed"),
    DestroyedCompromised(6, "DestroyedCompromised");

    private final Integer code;
    private final String description;

    public static CryptographicStateEnum find(Integer code) {
        for (CryptographicStateEnum value : CryptographicStateEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
