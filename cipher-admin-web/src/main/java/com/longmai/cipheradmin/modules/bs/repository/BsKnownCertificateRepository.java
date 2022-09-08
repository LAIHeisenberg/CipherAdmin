package com.longmai.cipheradmin.modules.bs.repository;

import com.longmai.cipheradmin.modules.bs.domain.BsKnownCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-08
**/
public interface BsKnownCertificateRepository extends JpaRepository<BsKnownCertificate, Long>, JpaSpecificationExecutor<BsKnownCertificate> {
}