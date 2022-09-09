package com.longmai.cipheradmin.modules.bs.repository;

import com.longmai.cipheradmin.modules.bs.domain.SysRootSecretkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-09
**/
public interface SysRootSecretkeyRepository extends JpaRepository<SysRootSecretkey, Long>, JpaSpecificationExecutor<SysRootSecretkey> {
}