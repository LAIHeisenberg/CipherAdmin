package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.BsGroup;
import com.longmai.cipheradmin.modules.bs.service.BsGroupService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsGroupQueryCriteria;
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
@Api(tags = "bsuserGroup管理")
@RequestMapping("/api/bsGroup")
public class BsGroupController {

    private final BsGroupService bsGroupService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('bsGroup:manage')")
    public void exportBsGroup(HttpServletResponse response, BsGroupQueryCriteria criteria) throws IOException {
        bsGroupService.download(bsGroupService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询bsuserGroup")
    @ApiOperation("查询bsuserGroup")
    @PreAuthorize("@el.check('bsGroup:manage')")
    public ResponseEntity<Object> queryBsGroup(BsGroupQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(bsGroupService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增bsuserGroup")
    @ApiOperation("新增bsuserGroup")
    @PreAuthorize("@el.check('bsGroup:manage')")
    public ResponseEntity<Object> createBsGroup(@Validated @RequestBody BsGroup resources){
        return new ResponseEntity<>(bsGroupService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改bsuserGroup")
    @ApiOperation("修改bsuserGroup")
    @PreAuthorize("@el.check('bsGroup:manage')")
    public ResponseEntity<Object> updateBsGroup(@Validated @RequestBody BsGroup resources){
        bsGroupService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除bsuserGroup")
    @ApiOperation("删除bsuserGroup")
    @PreAuthorize("@el.check('bsGroup:manage')")
    public ResponseEntity<Object> deleteBsGroup(@RequestBody Long[] ids) {
        bsGroupService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}