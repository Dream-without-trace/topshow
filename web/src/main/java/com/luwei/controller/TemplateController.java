package com.luwei.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@RestController
@Api(tags = "模板模块")
@RequestMapping("/api/template")
public class TemplateController {

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid Object object) {
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam Set<Integer> ids) {

    }

    @PutMapping
    @ApiOperation("修改")
    public void update(@RequestParam Object object) {
    }

    @GetMapping
    @ApiOperation("查找")
    public void findOne(@RequestParam Integer id) {
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public void page(@PageableDefault Pageable pageable, Object query) {

    }

//    @Autowired
//    private JpushService jpushService;

//    @GetMapping("/push")
//    @ApiOperation("推送")
//    public void push() {
//        jpushService.sendToAllAndroid("标题", "内容", "消息内容", "扩展字段");
//    }


}
