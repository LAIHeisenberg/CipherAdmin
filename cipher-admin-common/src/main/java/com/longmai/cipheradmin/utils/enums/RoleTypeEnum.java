/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.longmai.cipheradmin.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 角色类型
 * </p>
 * @author Zheng Jie
 * @date 2020-05-02
 */
@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

    ADMIN(1, "超级管理员"),

    AUDIT(2, "审计员"),

    OPERATOR(3, "操作员")
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
