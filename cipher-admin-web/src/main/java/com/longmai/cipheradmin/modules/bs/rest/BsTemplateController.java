package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsTemplate;
import com.longmai.cipheradmin.modules.bs.service.BsTemplateService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTemplateQueryCriteria;
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
@Api(tags = "/template管理")
@RequestMapping("/api/template")
public class BsTemplateController {

    private final BsTemplateService bsTemplateService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('templates:manage')")
    public void exportBsTemplate(HttpServletResponse response, BsTemplateQueryCriteria criteria) throws IOException {
        bsTemplateService.download(bsTemplateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/template")
    @ApiOperation("查询/template")
    @PreAuthorize("@el.check('templates:manage')")
    public ResponseEntity<Object> queryBsTemplate(BsTemplateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsTemplateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/template")
    @ApiOperation("新增/template")
    @PreAuthorize("@el.check('templates:manage')")
    public ResponseEntity<Object> createBsTemplate(@Validated @RequestBody BsTemplate resources){
        return new ResponseEntity<>(bsTemplateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/template")
    @ApiOperation("修改/template")
    @PreAuthorize("@el.check('templates:manage')")
    public ResponseEntity<Object> updateBsTemplate(@Validated @RequestBody BsTemplate resources){
        bsTemplateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/template")
    @ApiOperation("删除/template")
    @PreAuthorize("@el.check('templates:manage')")
    public ResponseEntity<Object> deleteBsTemplate(@RequestBody Long[] ids) {
        bsTemplateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}