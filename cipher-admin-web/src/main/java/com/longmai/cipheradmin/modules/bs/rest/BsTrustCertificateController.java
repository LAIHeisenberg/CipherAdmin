package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsTrustCertificate;
import com.longmai.cipheradmin.modules.bs.service.BsTrustCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTrustCertificateQueryCriteria;
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
@Api(tags = "/trustCertificate管理")
@RequestMapping("/api/bsTrustCertificate")
public class BsTrustCertificateController {

    private final BsTrustCertificateService bsTrustCertificateService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bsTrustCertificate:list')")
    public void exportBsTrustCertificate(HttpServletResponse response, BsTrustCertificateQueryCriteria criteria) throws IOException {
        bsTrustCertificateService.download(bsTrustCertificateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/trustCertificate")
    @ApiOperation("查询/trustCertificate")
    @PreAuthorize("@el.check('bsTrustCertificate:list')")
    public ResponseEntity<Object> queryBsTrustCertificate(BsTrustCertificateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsTrustCertificateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/trustCertificate")
    @ApiOperation("新增/trustCertificate")
    @PreAuthorize("@el.check('bsTrustCertificate:add')")
    public ResponseEntity<Object> createBsTrustCertificate(@Validated @RequestBody BsTrustCertificate resources){
        return new ResponseEntity<>(bsTrustCertificateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/trustCertificate")
    @ApiOperation("修改/trustCertificate")
    @PreAuthorize("@el.check('bsTrustCertificate:edit')")
    public ResponseEntity<Object> updateBsTrustCertificate(@Validated @RequestBody BsTrustCertificate resources){
        bsTrustCertificateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/trustCertificate")
    @ApiOperation("删除/trustCertificate")
    @PreAuthorize("@el.check('bsTrustCertificate:del')")
    public ResponseEntity<Object> deleteBsTrustCertificate(@RequestBody Long[] ids) {
        bsTrustCertificateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}