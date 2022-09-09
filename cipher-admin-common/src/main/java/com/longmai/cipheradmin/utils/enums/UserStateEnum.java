package com.longmai.cipheradmin.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 帐号启用状态：0->启用；1->临时停用；2->永久停用
 */
@Getter
@AllArgsConstructor
public enum UserStateEnum {
    OPEN(0, "启用"),

    TEMPORARY(1, "临时停用"),

    PERMANENT(2, "永久停用")
    ;

    private final Integer code;
    private final String description;

    public static RoleTypeEnum find(Integer code) {
        for (RoleTypeEnum value : RoleTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
