package com.longmai.cipheradmin.modules.bs.repository;

import com.longmai.cipheradmin.modules.bs.domain.BsGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-08
**/
public interface BsGroupRepository extends JpaRepository<BsGroup, Long>, JpaSpecificationExecutor<BsGroup> {
    BsGroup findByName(String name);

    @Query(value = "SELECT count(u.id) FROM kms_user u where u.group_id in ?1", nativeQuery = true)
    int countByGroupIds(Set<Long> ids);
}