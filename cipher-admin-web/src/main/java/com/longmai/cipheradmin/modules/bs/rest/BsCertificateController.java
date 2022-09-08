package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsCertificate;
import com.longmai.cipheradmin.modules.bs.service.BsCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsCertificateQueryCriteria;
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
@Api(tags = "/certificate管理")
@RequestMapping("/api/bsCertificate")
public class BsCertificateController {

    private final BsCertificateService bsCertificateService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bsCertificate:list')")
    public void exportBsCertificate(HttpServletResponse response, BsCertificateQueryCriteria criteria) throws IOException {
        bsCertificateService.download(bsCertificateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/certificate")
    @ApiOperation("查询/certificate")
    @PreAuthorize("@el.check('bsCertificate:list')")
    public ResponseEntity<Object> queryBsCertificate(BsCertificateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsCertificateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/certificate")
    @ApiOperation("新增/certificate")
    @PreAuthorize("@el.check('bsCertificate:add')")
    public ResponseEntity<Object> createBsCertificate(@Validated @RequestBody BsCertificate resources){
        return new ResponseEntity<>(bsCertificateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/certificate")
    @ApiOperation("修改/certificate")
    @PreAuthorize("@el.check('bsCertificate:edit')")
    public ResponseEntity<Object> updateBsCertificate(@Validated @RequestBody BsCertificate resources){
        bsCertificateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/certificate")
    @ApiOperation("删除/certificate")
    @PreAuthorize("@el.check('bsCertificate:del')")
    public ResponseEntity<Object> deleteBsCertificate(@RequestBody Long[] ids) {
        bsCertificateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}