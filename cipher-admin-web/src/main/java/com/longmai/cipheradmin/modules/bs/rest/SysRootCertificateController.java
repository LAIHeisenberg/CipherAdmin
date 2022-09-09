package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.SysRootCertificate;
import com.longmai.cipheradmin.modules.bs.service.SysRootCertificateService;
import com.longmai.cipheradmin.modules.bs.service.dto.SysRootCertificateQueryCriteria;
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
* @date 2022-09-09
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/rootCertificate管理")
@RequestMapping("/api/sysRootCertificate")
public class SysRootCertificateController {

    private final SysRootCertificateService sysRootCertificateService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysRootCertificate:list')")
    public void exportSysRootCertificate(HttpServletResponse response, SysRootCertificateQueryCriteria criteria) throws IOException {
        sysRootCertificateService.download(sysRootCertificateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/rootCertificate")
    @ApiOperation("查询/rootCertificate")
    @PreAuthorize("@el.check('sysRootCertificate:list')")
    public ResponseEntity<Object> querySysRootCertificate(SysRootCertificateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sysRootCertificateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/rootCertificate")
    @ApiOperation("新增/rootCertificate")
    @PreAuthorize("@el.check('sysRootCertificate:add')")
    public ResponseEntity<Object> createSysRootCertificate(@Validated @RequestBody SysRootCertificate resources){
        return new ResponseEntity<>(sysRootCertificateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/rootCertificate")
    @ApiOperation("修改/rootCertificate")
    @PreAuthorize("@el.check('sysRootCertificate:edit')")
    public ResponseEntity<Object> updateSysRootCertificate(@Validated @RequestBody SysRootCertificate resources){
        sysRootCertificateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/rootCertificate")
    @ApiOperation("删除/rootCertificate")
    @PreAuthorize("@el.check('sysRootCertificate:del')")
    public ResponseEntity<Object> deleteSysRootCertificate(@RequestBody Long[] ids) {
        sysRootCertificateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}