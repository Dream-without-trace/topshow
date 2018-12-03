package com.luwei.controller;

import com.luwei.common.Response;
import com.luwei.module.shiro.service.UserHelper;
import com.luwei.services.activity.ActivityService;
import com.luwei.services.activity.order.ActivityOrderService;
import com.luwei.services.activity.order.web.ActivityAddDTO;
import com.luwei.services.activity.series.web.ActivitySeriesListVO;
import com.luwei.services.activity.series.web.SeriesListVO;
import com.luwei.services.activity.web.*;
import com.luwei.services.area.web.CityListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Leone
 * @since Tuesday September
 **/
@Slf4j
@RestController
@Api(tags = "活动模块")
@RequestMapping("/api/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityOrderService activityOrderService;

    @GetMapping("/handpick")
    @ApiOperation("小程序首页")
    public HandpickVO handpick(@RequestParam Integer areaId, @RequestParam Integer activityCategoryId) {
        return activityService.handpick(areaId, activityCategoryId);
    }

    @GetMapping("/list")
    @ApiOperation("活动列表")
    public Page<ActivityWebListVO> list(@RequestParam Integer areaId, @RequestParam Integer activityCategoryId, @PageableDefault Pageable pageable) {
        return activityService.indexActivityList(areaId, activityCategoryId, pageable);
    }

    @GetMapping("/search")
    @ApiOperation("搜索")
    public Page<ActivityWebListVO> search(@RequestParam Integer areaId, @RequestParam(required = false) String title, @PageableDefault Pageable pageable) {
        return activityService.searchActivityList(areaId, title, pageable);
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public ActivityDetailVO activityWebDetail(@RequestParam Integer activityId, @RequestParam Integer userId) {
        return activityService.activityWebDetail(activityId, userId);
    }


    @GetMapping("/series")
    @ApiOperation("系列活动")
    public List<ActivitySeriesListVO> seriesActivity(@RequestParam Integer activityId, @RequestParam Integer areaId) {
        return activityService.activitySeriesList(activityId, areaId);
    }

    @PostMapping("/apply")
    @ApiOperation("报名活动")
    public ActivityApplyVO applyActivity(@RequestParam Integer activityId) {
        return activityService.applyActivity(activityId);
    }

    @PostMapping("/affirm")
    @ApiOperation("确认订单页面")
    public ActivityAffirmVO affirmActivity(@RequestBody ActivityApplyDTO dto) {
        Integer userId = UserHelper.getId();
        return activityService.affirmActivity(dto);
    }

    @PostMapping("/commit")
    @ApiOperation("提交活动订单")
    public Response commit(@RequestBody ActivityAddDTO dto, HttpServletRequest request) throws Exception {
        Integer userId = UserHelper.getId();
        return Response.build(20000, "success", activityOrderService.commit(dto, request));
    }


    @GetMapping("/city")
    @ApiOperation("活动城市列表")
    public List<CityListVO> activityCityList() {
        return activityService.activityCityList();
    }

    @GetMapping("/recommend")
    @ApiOperation("推荐活动")
    public Page<ActivityWebListVO> recommend(@RequestParam Integer areaId, @PageableDefault Pageable pageable) {
        return activityService.recommendActivityList(areaId, pageable);
    }

    @GetMapping("/like")
    @ApiOperation("猜你喜欢")
    public Page<ActivityWebListVO> guessLike(@RequestParam Integer areaId, @PageableDefault Pageable pageable) {
        return activityService.guessLike(areaId, pageable);
    }

    @GetMapping("/series/list")
    @ApiOperation("系列活动列表")
    public List<SeriesListVO> seriesActivityList() {
        return activityService.seriesActivityList();
    }

    @GetMapping("/category")
    @ApiOperation("根据活动分类获取活动")
    public Page<ActivityWebListVO> activityCategoryPage(@RequestParam Integer areaId, @RequestParam Integer categoryId, @PageableDefault Pageable pageable) {
        return activityService.activityCategoryPage(areaId, categoryId, pageable);
    }

}
