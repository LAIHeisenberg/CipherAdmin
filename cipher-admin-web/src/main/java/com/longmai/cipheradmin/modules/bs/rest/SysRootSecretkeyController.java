package com.longmai.cipheradmin.modules.bs.rest;

import com.longmai.cipheradmin.annotation.Log;
import com.longmai.cipheradmin.modules.bs.domain.SysRootSecretkey;
import com.longmai.cipheradmin.modules.bs.service.SysRootSecretkeyService;
import com.longmai.cipheradmin.modules.bs.service.dto.SysRootSecretkeyQueryCriteria;
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
@Api(tags = "/rootSecretkey管理")
@RequestMapping("/api/sysRootSecretkey")
public class SysRootSecretkeyController {

    private final SysRootSecretkeyService sysRootSecretkeyService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysRootSecretkey:list')")
    public void exportSysRootSecretkey(HttpServletResponse response, SysRootSecretkeyQueryCriteria criteria) throws IOException {
        sysRootSecretkeyService.download(sysRootSecretkeyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/rootSecretkey")
    @ApiOperation("查询/rootSecretkey")
    @PreAuthorize("@el.check('sysRootSecretkey:list')")
    public ResponseEntity<Object> querySysRootSecretkey(SysRootSecretkeyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sysRootSecretkeyService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/rootSecretkey")
    @ApiOperation("新增/rootSecretkey")
    @PreAuthorize("@el.check('sysRootSecretkey:add')")
    public ResponseEntity<Object> createSysRootSecretkey(@Validated @RequestBody SysRootSecretkey resources){
        return new ResponseEntity<>(sysRootSecretkeyService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/rootSecretkey")
    @ApiOperation("修改/rootSecretkey")
    @PreAuthorize("@el.check('sysRootSecretkey:edit')")
    public ResponseEntity<Object> updateSysRootSecretkey(@Validated @RequestBody SysRootSecretkey resources){
        sysRootSecretkeyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除/rootSecretkey")
    @ApiOperation("删除/rootSecretkey")
    @PreAuthorize("@el.check('sysRootSecretkey:del')")
    public ResponseEntity<Object> deleteSysRootSecretkey(@RequestBody Long[] ids) {
        sysRootSecretkeyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}