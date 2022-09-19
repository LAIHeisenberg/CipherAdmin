package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.modules.bs.param.*;
import com.longmai.cipheradmin.modules.bs.service.SeckeyService;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectQueryCriteria;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 密钥管理
 */
@Api(tags = "密钥：密钥管理")
@RestController
@RequestMapping("/api/seckey")
@RequiredArgsConstructor
public class SeckeyController {

    @Autowired
    SeckeyService seckeyService;

    /**
     * 创建密钥（支持对称、非对称、模板密钥）
     * @param secKeyCreateParam
     * @return
     */
    @PostMapping("/create")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> createSecKey(@RequestBody SecKeyCreateParam secKeyCreateParam){
        List<String> uuidKeys = seckeyService.createSecKeys(secKeyCreateParam);
        return new ResponseEntity<>(uuidKeys, HttpStatus.OK);
    }

    /**
     * 密钥列表
     * @param queryParam
     * @param pageable
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> listSeckey(SecKeyQueryParam queryParam, Pageable pageable){
        return new ResponseEntity<>(seckeyService.queryAll(queryParam,pageable),HttpStatus.OK);
    }

    /**
     * 密钥销毁
     * @return
     */
    @GetMapping("/destroy")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> destroySeckey(List<String> uuidKeys){
        SecKeyDestroyParam secKeyDestroyParam = new SecKeyDestroyParam();
        secKeyDestroyParam.setUuidKeys(uuidKeys);
        return new ResponseEntity<>(seckeyService.destroySecKeys(secKeyDestroyParam),HttpStatus.OK);
    }

    /**
     * 归档请求
     * @param uuidKeys
     * @return
     */
    @GetMapping("/archive")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> archiveSeckey(List<String> uuidKeys){
        SecKeyArchiveParam archiveParam = new SecKeyArchiveParam();
        archiveParam.setUuidKeys(uuidKeys);
        return new ResponseEntity<>(seckeyService.archiveSecKeys(archiveParam),HttpStatus.OK);
    }

    /**
     * 激活请求
     */
    @GetMapping("/activate")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> activateSeckey(List<String> uuidKeys){
        SecKeyArchiveParam archiveParam = new SecKeyArchiveParam();
        archiveParam.setUuidKeys(uuidKeys);
        return new ResponseEntity<>(seckeyService.archiveSecKeys(archiveParam),HttpStatus.OK);
    }

    /**
     * Revoke
     */
    @GetMapping("/revoke")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> revokeSeckey(SecKeyRevokeParam revokeParam){
        return new ResponseEntity<>(seckeyService.revokeSecKeys(revokeParam),HttpStatus.OK);
    }

    /**
     * 恢复
     * @param revokeParam
     * @return
     */
    @GetMapping("/recover")
    @PreAuthorize("@el.check('seckey:manage')")
    public ResponseEntity<Object> recoverSeckey(SecKeyDestroyParam revokeParam){
        return new ResponseEntity<>(seckeyService.recoverSecKeys(revokeParam),HttpStatus.OK);
    }

    /**
     * 删除
     */



}
