package com.luwei.controllers;

import com.luwei.common.constants.RoleConstant;
import com.luwei.common.exception.ValidateException;
import com.luwei.services.manager.ManagerService;
import com.luwei.services.manager.cms.ManagerAddDTO;
import com.luwei.services.manager.cms.ManagerEditDTO;
import com.luwei.services.manager.cms.ManagerPageVO;
import com.luwei.services.manager.cms.ManagerQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@Slf4j
@RestController
@Api(tags = "超级管理员模块")
@RequestMapping("/api/manager")
public class ManagerController {

    @Resource
    private ManagerService managerService;

    @PostMapping
    @ApiOperation("添加")
    @RequiresRoles(logical = Logical.OR, value = RoleConstant.ROOT)
    public void save(@RequestBody @Valid ManagerAddDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(e -> sb.append(e.getDefaultMessage()).append("."));
            throw new ValidateException(40001, sb.toString());
        }
        managerService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    @RequiresRoles(logical = Logical.OR, value = RoleConstant.ROOT)
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        managerService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public ManagerPageVO update(@RequestBody ManagerEditDTO dto) {
        return managerService.update(dto);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public Page<ManagerPageVO> page(@ModelAttribute ManagerQueryDTO dto, @PageableDefault Pageable pageable) {
        return managerService.page(dto, pageable);
    }

    @PutMapping("/state")
    @ApiOperation("禁用|开启")
    @RequiresRoles(logical = Logical.OR, value = RoleConstant.ROOT)
    public ManagerPageVO handleDisabled(@RequestParam Integer managerId) {
        return managerService.update(managerId);
    }

    @PutMapping("/reset")
    @ApiOperation("重置密码")
    @RequiresRoles(logical = Logical.OR, value = {RoleConstant.ROOT})
    public ManagerPageVO resetPassword(@RequestBody @Valid ManagerEditDTO dto) {
        return managerService.resetPassword(dto);
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    @RequiresRoles(logical = Logical.OR, value = RoleConstant.ROOT)
    public List<ManagerPageVO> list(@ModelAttribute ManagerQueryDTO dto, @PageableDefault Pageable pageable) {
        return managerService.list(dto);
    }

}
