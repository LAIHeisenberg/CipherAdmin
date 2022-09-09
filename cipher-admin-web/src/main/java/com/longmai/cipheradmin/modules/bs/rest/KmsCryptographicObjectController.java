package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.KmsCryptographicObject;
import com.longmai.cipheradmin.modules.bs.service.KmsCryptographicObjectService;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsCryptographicObjectQueryCriteria;
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
@Api(tags = "/cryptographicObject管理")
@RequestMapping("/api/kmsCryptographicObject")
public class KmsCryptographicObjectController {

    private final KmsCryptographicObjectService kmsCryptographicObjectService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('kmsCryptographicObject:list')")
    public void exportKmsCryptographicObject(HttpServletResponse response, KmsCryptographicObjectQueryCriteria criteria) throws IOException {
        kmsCryptographicObjectService.download(kmsCryptographicObjectService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/cryptographicObject")
    @ApiOperation("查询/cryptographicObject")
    @PreAuthorize("@el.check('kmsCryptographicObject:list')")
    public ResponseEntity<Object> queryKmsCryptographicObject(KmsCryptographicObjectQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(kmsCryptographicObjectService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/cryptographicObject")
    @ApiOperation("新增/cryptographicObject")
    @PreAuthorize("@el.check('kmsCryptographicObject:add')")
    public ResponseEntity<Object> createKmsCryptographicObject(@Validated @RequestBody KmsCryptographicObject resources){
        return new ResponseEntity<>(kmsCryptographicObjectService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/cryptographicObject")
    @ApiOperation("修改/cryptographicObject")
    @PreAuthorize("@el.check('kmsCryptographicObject:edit')")
    public ResponseEntity<Object> updateKmsCryptographicObject(@Validated @RequestBody KmsCryptographicObject resources){
        kmsCryptographicObjectService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/cryptographicObject")
    @ApiOperation("删除/cryptographicObject")
    @PreAuthorize("@el.check('kmsCryptographicObject:del')")
    public ResponseEntity<Object> deleteKmsCryptographicObject(@RequestBody Long[] ids) {
        kmsCryptographicObjectService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}