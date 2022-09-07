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

import com.longmai.cipheradmin.modules.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author Zheng Jie
 * @date 2018-12-03
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    /**
     * 根据名称查询
     * @param name /
     * @return /
     */
    Role findByName(String name);

    List<Role> findByRoleType(Integer roleType);

    /**
     * 删除多个角色
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);

    /**
     * 根据用户ID查询
     * @param id 用户ID
     * @return /
     */
    @Query(value = "SELECT r.* FROM sys_role r, sys_user u WHERE " +
            "r.id = u.role_id AND u.id = ?1",nativeQuery = true)
    Set<Role> findByUserId(Long id);

    /**
     * 解绑角色菜单
     * @param id 菜单ID
     */
    @Modifying
    @Query(value = "delete from sys_role_permission where permission_id = ?1",nativeQuery = true)
    void untiedMenu(Long id);



    /**
     * 根据菜单Id查询
     * @param menuIds /
     * @return /
     */
    @Query(value = "SELECT r.* FROM sys_role r, sys_role_permission m WHERE " +
            "r.id = m.role_id AND m.permission_id in ?1",nativeQuery = true)
    List<Role> findInMenuId(List<Long> menuIds);
}
