package com.longmai.cipheradmin.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证方式（1：usb key，2：用户名密码）
 */
@Getter
@AllArgsConstructor
public enum AuthMethodEnum {

    USB_KEY(1, "usb key"),
    USER_PWD(2, "用户名密码");

    private final Integer code;
    private final String description;

    public static CodeBiEnum find(Integer code) {
        for (CodeBiEnum value : CodeBiEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
