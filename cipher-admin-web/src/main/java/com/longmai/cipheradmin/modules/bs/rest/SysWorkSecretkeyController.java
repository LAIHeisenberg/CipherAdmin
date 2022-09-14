package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.SysWorkSecretkey;
import com.longmai.cipheradmin.modules.bs.service.SysWorkSecretkeyService;
import com.longmai.cipheradmin.modules.bs.service.dto.BsTemplateDto;
import com.longmai.cipheradmin.modules.bs.service.dto.SysWorkSecretkeyQueryCriteria;
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
@Api(tags = "/workSecretkey管理")
@RequestMapping("/api/sysWorkSecretkey")
public class SysWorkSecretkeyController {

    private final SysWorkSecretkeyService sysWorkSecretkeyService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysWorkSecretkey:list')")
    public void exportSysWorkSecretkey(HttpServletResponse response, SysWorkSecretkeyQueryCriteria criteria) throws IOException {
        sysWorkSecretkeyService.download(sysWorkSecretkeyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/workSecretkey")
    @ApiOperation("查询/workSecretkey")
    @PreAuthorize("@el.check('sysWorkSecretkey:list')")
    public ResponseEntity<Object> querySysWorkSecretkey(SysWorkSecretkeyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sysWorkSecretkeyService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/workSecretkey")
    @ApiOperation("新增/workSecretkey")
    @PreAuthorize("@el.check('sysWorkSecretkey:add')")
    public ResponseEntity<Object> createSysWorkSecretkey(@Validated @RequestBody BsTemplateDto resources){
        return new ResponseEntity<>(sysWorkSecretkeyService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/workSecretkey")
    @ApiOperation("修改/workSecretkey")
    @PreAuthorize("@el.check('sysWorkSecretkey:edit')")
    public ResponseEntity<Object> updateSysWorkSecretkey(@Validated @RequestBody SysWorkSecretkey resources){
        sysWorkSecretkeyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/workSecretkey")
    @ApiOperation("删除/workSecretkey")
    @PreAuthorize("@el.check('sysWorkSecretkey:del')")
    public ResponseEntity<Object> deleteSysWorkSecretkey(@RequestBody Long[] ids) {
        sysWorkSecretkeyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}