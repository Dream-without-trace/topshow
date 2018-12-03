package com.luwei.controllers;

import com.luwei.services.activity.series.ActivitySeriesService;
import com.luwei.services.activity.series.cms.ActivitySeriesAddDTO;
import com.luwei.services.activity.series.cms.ActivitySeriesEditDTO;
import com.luwei.services.activity.series.cms.ActivitySeriesPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-09
 **/
@Slf4j
@RestController
@Api(tags = "活动系列模块")
@RequestMapping("api/activity/series")
public class ActivitySeriesController {

    @Resource
    private ActivitySeriesService activitySeriesService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid ActivitySeriesAddDTO dto) {
        activitySeriesService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        activitySeriesService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public ActivitySeriesPageVO update(@RequestBody ActivitySeriesEditDTO dto) {
        return activitySeriesService.update(dto);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<ActivitySeriesPageVO> page(@RequestParam(required = false) String title, @PageableDefault Pageable pageable) {
        return activitySeriesService.page(pageable, title);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public ActivitySeriesPageVO one(@RequestParam Integer id) {
        return activitySeriesService.one(id);
    }

}
