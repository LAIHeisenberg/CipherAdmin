package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsLocalCa;
import com.longmai.cipheradmin.modules.bs.service.BsLocalCaService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsLocalCaQueryCriteria;
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
@Api(tags = "/localCa管理")
@RequestMapping("/api/bsLocalCa")
public class BsLocalCaController {

    private final BsLocalCaService bsLocalCaService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bsLocalCa:list')")
    public void exportBsLocalCa(HttpServletResponse response, BsLocalCaQueryCriteria criteria) throws IOException {
        bsLocalCaService.download(bsLocalCaService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/localCa")
    @ApiOperation("查询/localCa")
    @PreAuthorize("@el.check('bsLocalCa:list')")
    public ResponseEntity<Object> queryBsLocalCa(BsLocalCaQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsLocalCaService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/localCa")
    @ApiOperation("新增/localCa")
    @PreAuthorize("@el.check('bsLocalCa:add')")
    public ResponseEntity<Object> createBsLocalCa(@Validated @RequestBody BsLocalCa resources){
        return new ResponseEntity<>(bsLocalCaService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/localCa")
    @ApiOperation("修改/localCa")
    @PreAuthorize("@el.check('bsLocalCa:edit')")
    public ResponseEntity<Object> updateBsLocalCa(@Validated @RequestBody BsLocalCa resources){
        bsLocalCaService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/localCa")
    @ApiOperation("删除/localCa")
    @PreAuthorize("@el.check('bsLocalCa:del')")
    public ResponseEntity<Object> deleteBsLocalCa(@RequestBody Long[] ids) {
        bsLocalCaService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}