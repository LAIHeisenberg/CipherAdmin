package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsKnownCa;
import com.longmai.cipheradmin.modules.bs.service.BsKnownCaService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsKnownCaQueryCriteria;
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
* @date 2022-09-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/knownCa管理")
@RequestMapping("/api/bsKnownCa")
public class BsKnownCaController {

    private final BsKnownCaService bsKnownCaService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('knownCa:manage')")
    public void exportBsKnownCa(HttpServletResponse response, BsKnownCaQueryCriteria criteria) throws IOException {
        bsKnownCaService.download(bsKnownCaService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/knownCa")
    @ApiOperation("查询/knownCa")
    @PreAuthorize("@el.check('knownCa:manage')")
    public ResponseEntity<Object> queryBsKnownCa(BsKnownCaQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsKnownCaService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/knownCa")
    @ApiOperation("新增/knownCa")
    @PreAuthorize("@el.check('knownCa:manage')")
    public ResponseEntity<Object> createBsKnownCa(@Validated @RequestBody BsKnownCa resources){
        return new ResponseEntity<>(bsKnownCaService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/knownCa")
    @ApiOperation("修改/knownCa")
    @PreAuthorize("@el.check('knownCa:manage')")
    public ResponseEntity<Object> updateBsKnownCa(@Validated @RequestBody BsKnownCa resources){
        bsKnownCaService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/knownCa")
    @ApiOperation("删除/knownCa")
    @PreAuthorize("@el.check('knownCa:manage')")
    public ResponseEntity<Object> deleteBsKnownCa(@RequestBody Long[] ids) {
        bsKnownCaService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}