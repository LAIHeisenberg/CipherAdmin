package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.modules.bs.domain.KmsCryptographicObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-08
**/
public interface KmsCryptographicObjectRepository extends JpaRepository<KmsCryptographicObject, Long>, JpaSpecificationExecutor<KmsCryptographicObject> {
}