package com.longmai.cipheradmin.modules.bs.repository;

import com.longmai.cipheradmin.modules.bs.domain.SysWorkSecretkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-09
**/
public interface SysWorkSecretkeyRepository extends JpaRepository<SysWorkSecretkey, Long>, JpaSpecificationExecutor<SysWorkSecretkey> {
}