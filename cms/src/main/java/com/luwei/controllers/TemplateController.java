package com.luwei.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Slf4j
@RestController
@Api(tags = "模板模块")
@RequestMapping("api/template")
public class TemplateController {

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid Object dto) {
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
    }

    @PutMapping
    @ApiOperation("修改")
    public Object update(@RequestBody Object dto) {
        return null;
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public Object one(@RequestParam Integer id) {
        return null;
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<Object> page(@ModelAttribute Object dto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return null;
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public List<Object> list(@ModelAttribute Object dto) {
        return null;
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(Pageable pageable, @RequestBody Object query, HttpServletResponse response) {

    }

}
