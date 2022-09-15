package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.modules.bs.service.KmipService;
import com.longmai.cipheradmin.modules.bs.service.KmsCryptographicObjectService;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectQueryCriteria;
import com.longmai.cipheradmin.modules.bs.param.SecKeyCreateParam;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 密钥管理
 */
@Api(tags = "密钥：密钥管理")
@RestController
@RequestMapping("/api/seckey")
@RequiredArgsConstructor
public class SeckeyController {

    @Autowired
    KmipService kmip;

    @Autowired
    KmsCryptographicObjectService kmsCryptographicObjectService;

    @PostMapping("/create")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> createSecKey(@RequestBody SecKeyCreateParam secKeyCreateParam){
        kmip.sendCreatRequest(secKeyCreateParam);
        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> listSeckey(KmsCryptographicObjectQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(kmsCryptographicObjectService.queryAll(criteria,pageable),HttpStatus.OK);
    }


}
