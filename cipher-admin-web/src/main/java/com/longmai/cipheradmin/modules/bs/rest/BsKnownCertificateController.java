package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsKnownCertificate;
import com.longmai.cipheradmin.modules.bs.service.BsKnownCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsKnownCertificateQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author huangsi
* @date 2022-09-08
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/knownCertificate管理")
@RequestMapping("/api/bsKnownCertificate")
public class BsKnownCertificateController {

    private final BsKnownCertificateService bsKnownCertificateService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bsKnownCertificate:list')")
    public void exportBsKnownCertificate(HttpServletResponse response, BsKnownCertificateQueryCriteria criteria) throws IOException {
        bsKnownCertificateService.download(bsKnownCertificateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/knownCertificate")
    @ApiOperation("查询/knownCertificate")
    @PreAuthorize("@el.check('bsKnownCertificate:list')")
    public ResponseEntity<Object> queryBsKnownCertificate(BsKnownCertificateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsKnownCertificateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/knownCertificate")
    @ApiOperation("新增/knownCertificate")
    @PreAuthorize("@el.check('bsKnownCertificate:add')")
    public ResponseEntity<Object> createBsKnownCertificate(@Validated @RequestBody BsKnownCertificate resources){
        return new ResponseEntity<>(bsKnownCertificateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/knownCertificate")
    @ApiOperation("修改/knownCertificate")
    @PreAuthorize("@el.check('bsKnownCertificate:edit')")
    public ResponseEntity<Object> updateBsKnownCertificate(@Validated @RequestBody BsKnownCertificate resources){
        bsKnownCertificateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/knownCertificate")
    @ApiOperation("删除/knownCertificate")
    @PreAuthorize("@el.check('bsKnownCertificate:del')")
    public ResponseEntity<Object> deleteBsKnownCertificate(@RequestBody Long[] ids) {
        bsKnownCertificateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}