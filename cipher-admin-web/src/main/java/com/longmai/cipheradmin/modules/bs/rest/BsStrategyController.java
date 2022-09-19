package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsStrategy;
import com.longmai.cipheradmin.modules.bs.service.BsStrategyService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsStrategyQueryCriteria;
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
@Api(tags = "/strategy管理")
@RequestMapping("/api/strategy")
public class BsStrategyController {

    private final BsStrategyService bsStrategyService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('strategy:manage')")
    public void exportBsStrategy(HttpServletResponse response, BsStrategyQueryCriteria criteria) throws IOException {
        bsStrategyService.download(bsStrategyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/strategy")
    @ApiOperation("查询/strategy")
    @PreAuthorize("@el.check('strategy:manage')")
    public ResponseEntity<Object> queryBsStrategy(BsStrategyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsStrategyService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/strategy")
    @ApiOperation("新增/strategy")
    @PreAuthorize("@el.check('strategy:manage')")
    public ResponseEntity<Object> createBsStrategy(@Validated @RequestBody BsStrategy resources){
        return new ResponseEntity<>(bsStrategyService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/strategy")
    @ApiOperation("修改/strategy")
    @PreAuthorize("@el.check('strategy:manage')")
    public ResponseEntity<Object> updateBsStrategy(@Validated @RequestBody BsStrategy resources){
        bsStrategyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/strategy")
    @ApiOperation("删除/strategy")
    @PreAuthorize("@el.check('strategy:manage')")
    public ResponseEntity<Object> deleteBsStrategy(@RequestBody Long[] ids) {
        bsStrategyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}