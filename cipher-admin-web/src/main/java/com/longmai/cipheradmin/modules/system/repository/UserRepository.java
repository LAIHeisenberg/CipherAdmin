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
package com.longmai.cipheradmin.modules.system.repository;

import com.longmai.cipheradmin.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-11-22
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return /
     */
    User findByUsername(String username);

    /**
     * 根据邮箱查询
     * @param email 邮箱
     * @return /
     */
    User findByEmail(String email);

    /**
     * 根据手机号查询
     * @param phone 手机号
     * @return /
     */
    User findByTel(String phone);

    /**
     * 修改密码
     * @param username 用户名
     * @param pass 密码
     * @param lastPasswordResetTime /
     */
    @Modifying
    @Query(value = "update sys_user set password = ?2 , pwd_reset_time = ?3 where name = ?1",nativeQuery = true)
    void updatePass(String username, String pass, Date lastPasswordResetTime);

    /**
     * 修改邮箱
     * @param username 用户名
     * @param email 邮箱
     */
    @Modifying
    @Query(value = "update sys_user set email = ?2 where name = ?1",nativeQuery = true)
    void updateEmail(String username, String email);

    /**
     * 根据角色查询用户
     * @param roleId /
     * @return /
     */
    @Query(value = "SELECT u.* FROM sys_user u WHERE" +
            " u.role_id = ?1", nativeQuery = true)
    List<User> findByRoleId(Long roleId);



    /**
     * 根据菜单查询
     * @param id 菜单ID
     * @return /
     */
    @Query(value = "SELECT u.* FROM sys_user u, sys_role_permission rm WHERE " +
            " u.role_id = rm.role_id AND rm.permission_id = ?1 group by u.id", nativeQuery = true)
    List<User> findByMenuId(Long id);

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);


    /**
     * 根据角色查询
     * @param ids /
     * @return /
     */
    @Query(value = "SELECT count(u.id) FROM sys_user u where u.role_id in ?1", nativeQuery = true)
    int countByRoles(Set<Long> ids);

    User findByDnAndEnabled(String dn, boolean enabled);
}
