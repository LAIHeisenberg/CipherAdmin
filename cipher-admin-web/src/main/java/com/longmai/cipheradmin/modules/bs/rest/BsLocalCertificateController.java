package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsLocalCertificate;
import com.longmai.cipheradmin.modules.bs.service.BsLocalCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCertificateQueryCriteria;
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
@Api(tags = "/localCertificate管理")
@RequestMapping("/api/bsLocalCertificate")
public class BsLocalCertificateController {

    private final BsLocalCertificateService bsLocalCertificateService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bsLocalCertificate:list')")
    public void exportBsLocalCertificate(HttpServletResponse response, BsLocalCertificateQueryCriteria criteria) throws IOException {
        bsLocalCertificateService.download(bsLocalCertificateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/localCertificate")
    @ApiOperation("查询/localCertificate")
    @PreAuthorize("@el.check('bsLocalCertificate:list')")
    public ResponseEntity<Object> queryBsLocalCertificate(BsLocalCertificateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsLocalCertificateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/localCertificate")
    @ApiOperation("新增/localCertificate")
    @PreAuthorize("@el.check('bsLocalCertificate:add')")
    public ResponseEntity<Object> createBsLocalCertificate(@Validated @RequestBody BsLocalCertificate resources){
        return new ResponseEntity<>(bsLocalCertificateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/localCertificate")
    @ApiOperation("修改/localCertificate")
    @PreAuthorize("@el.check('bsLocalCertificate:edit')")
    public ResponseEntity<Object> updateBsLocalCertificate(@Validated @RequestBody BsLocalCertificate resources){
        bsLocalCertificateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/localCertificate")
    @ApiOperation("删除/localCertificate")
    @PreAuthorize("@el.check('bsLocalCertificate:del')")
    public ResponseEntity<Object> deleteBsLocalCertificate(@RequestBody Long[] ids) {
        bsLocalCertificateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}