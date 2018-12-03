package com.luwei.controller;

import com.luwei.common.enums.type.CollectType;
import com.luwei.services.collect.CollectService;
import com.luwei.services.collect.web.CollectWebListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@RestController
@Api(tags = "收藏模块")
@RequestMapping("/api/collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    @PutMapping
    @ApiOperation("收藏|取消")
    public void save(@RequestParam @ApiParam("商品或活动id") Integer id,
                     @RequestParam @ApiParam("收藏类型") CollectType collectType,
                     @RequestParam @ApiParam("用户id") Integer userId) {
        collectService.save(id, collectType, userId);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("商品或活动id数组") Set<Integer> ids,
                       @RequestParam @ApiParam("收藏类型") CollectType collectType,
                       @RequestParam @ApiParam("用户id") Integer userId) {
        collectService.delete(ids, collectType, userId);
    }

    @GetMapping
    @ApiOperation("查找")
    public Page<CollectWebListVO> list(@RequestParam @ApiParam("收藏类型") CollectType collectType,
                                       @RequestParam @ApiParam("用户id") Integer userId,
                                       @PageableDefault Pageable pageable) {
        return collectService.collectList(collectType, userId, pageable);

    }


}
