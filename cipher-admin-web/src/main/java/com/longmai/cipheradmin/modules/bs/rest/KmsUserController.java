package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.KmsUser;
import com.longmai.cipheradmin.modules.bs.service.KmsUserService;
import com.longmai.cipheradmin.modules.bs.service.dto.KmsUserQueryCriteria;
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
@Api(tags = "/bsUser管理")
@RequestMapping("/api/kmsUser")
public class KmsUserController {

    private final KmsUserService kmsUserService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('kmsUser:list')")
    public void exportKmsUser(HttpServletResponse response, KmsUserQueryCriteria criteria) throws IOException {
        kmsUserService.download(kmsUserService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/bsUser")
    @ApiOperation("查询/bsUser")
    @PreAuthorize("@el.check('kmsUser:list')")
    public ResponseEntity<Object> queryKmsUser(KmsUserQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(kmsUserService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/bsUser")
    @ApiOperation("新增/bsUser")
    @PreAuthorize("@el.check('kmsUser:add')")
    public ResponseEntity<Object> createKmsUser(@Validated @RequestBody KmsUser resources){
        return new ResponseEntity<>(kmsUserService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/bsUser")
    @ApiOperation("修改/bsUser")
    @PreAuthorize("@el.check('kmsUser:edit')")
    public ResponseEntity<Object> updateKmsUser(@Validated @RequestBody KmsUser resources){
        kmsUserService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/bsUser")
    @ApiOperation("删除/bsUser")
    @PreAuthorize("@el.check('kmsUser:del')")
    public ResponseEntity<Object> deleteKmsUser(@RequestBody Long[] ids) {
        kmsUserService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}