package com.luwei.controllers;

import com.luwei.common.constants.RoleConstant;
import com.luwei.services.store.StoreService;
import com.luwei.services.store.cms.StoreAddDTO;
import com.luwei.services.store.cms.StoreEditDTO;
import com.luwei.services.store.cms.StorePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-12
 **/
@Slf4j
@RestController
@Api(tags = "商家模块")
@RequestMapping("api/store")
public class StoreController {

    @Resource
    private StoreService storeService;

    @PostMapping
    @ApiOperation("添加")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public void save(@RequestBody @Valid StoreAddDTO dto) {
        storeService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        storeService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN,RoleConstant.STORE})
    public StorePageVO update(@RequestBody StoreEditDTO dto) {
        return storeService.update(dto);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN,RoleConstant.STORE})
    public StorePageVO one(@RequestParam Integer storeId) {
        return storeService.one(storeId);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT, RoleConstant.ADMIN})
    public Page<StorePageVO> page(String name, @PageableDefault Pageable pageable) {
        return storeService.page(pageable, name);
    }

}
