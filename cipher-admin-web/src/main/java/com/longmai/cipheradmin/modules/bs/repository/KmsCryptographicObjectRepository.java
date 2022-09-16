package com.longmai.cipheradmin.modules.bs.repository;

import com.longmai.cipheradmin.modules.bs.domain.KmsCryptographicObject;
import com.longmai.cipheradmin.modules.bs.param.SecKeyParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-08
**/
public interface KmsCryptographicObjectRepository extends JpaRepository<KmsCryptographicObject, Long>, JpaSpecificationExecutor<KmsCryptographicObject> {

//    Page<SecKeyParam> findAllPage(@Nullable Specification<SecKeyParam> spec, Pageable pageable);
}