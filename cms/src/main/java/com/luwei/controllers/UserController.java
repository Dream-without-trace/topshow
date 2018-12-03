package com.luwei.controllers;

import com.luwei.services.user.UserService;
import com.luwei.services.user.cms.UserEditDTO;
import com.luwei.services.user.cms.UserPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Leone
 * @since Saturday August
 **/
@RestController
@Api(tags = "用户模块")
@RequestMapping("api/user")
public class UserController {

    @Resource
    private UserService userService;

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        this.userService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public UserPageVO update(@RequestBody UserEditDTO dto) {
        return userService.update(dto);
    }

    @PutMapping("/status")
    @ApiOperation("启用|禁用")
    public UserPageVO updateStatus(@RequestParam Integer userId) {
        return userService.updateStatus(userId);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public UserPageVO one(@RequestParam Integer userId) {
        return userService.one(userId);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<UserPageVO> page(@RequestParam(required = false) String nickname, @RequestParam(required = false) String phone, @PageableDefault Pageable pageable) {
        return userService.page(pageable, nickname, phone);
    }

}
