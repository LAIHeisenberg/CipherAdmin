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
package com.longmai.cipheradmin.modules.system.domain;

import com.longmai.cipheradmin.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
@Entity
@Getter
@Setter
@Table(name="sys_user")
public class User extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    @ApiModelProperty(value = "用户角色")
    private Role role;

    @NotBlank
    @Column(unique = true,name = "name")
    @ApiModelProperty(value = "用户名称")
    private String username;

    @Column(name = "pwd")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "pwd_reset_time")
    @ApiModelProperty(value = "最后修改密码的时间", hidden = true)
    private Date pwdResetTime;

    @ApiModelProperty(value = "用户性别")
    private String gender;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @NotBlank
    @ApiModelProperty(value = "电话号码")
    private String tel;

    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "")
    private String address;

    @ApiModelProperty(value = "头像真实名称",hidden = true)
    private String avatarName;

    @ApiModelProperty(value = "头像存储的路径", hidden = true)
    private String avatarPath;

    @NotNull
    @ApiModelProperty(value = "帐号启用状态：0->启用；1->临时停用；2->永久停用")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "认证方式（1：usb key，2：用户名密码，3：两种方式都可以）")
    private String authMethod;

    @ApiModelProperty(value = "首次登陆是否需要修改密码（0：需要，1：不需要）")
    @Column(name = "if_need_modify_pwd")
    private Boolean ifNeedModifyPwd;

    @ApiModelProperty(value = "人员证书（usb key 认证使用）")
    private String cert;

    @ApiModelProperty(value = "人员DN（usb key认证使用）")
    private String dn;

    @ApiModelProperty(value = "是否为admin账号", hidden = true)
    private Boolean enabled = true;

    @ApiModelProperty(value = "是否为admin账号", hidden = true)
    private Boolean isAdmin = false;

    @NotBlank
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "是否登录（0：是，1：否）", hidden = true)
    private Boolean ifLogin=false;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}