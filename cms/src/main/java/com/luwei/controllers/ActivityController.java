package com.luwei.controllers;

import com.luwei.common.Response;
import com.luwei.models.activity.subcard.ActivitySubCardDTO;
import com.luwei.services.activity.ActivityService;
import com.luwei.services.activity.cms.ActivityAddDTO;
import com.luwei.services.activity.cms.ActivityEditDTO;
import com.luwei.services.activity.cms.ActivityOrderCMSPageVO;
import com.luwei.services.activity.cms.ActivityPageVO;
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
 * @since 2018-08-10
 **/
@Slf4j
@RestController
@Api(tags = "活动模块")
@RequestMapping("api/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @PostMapping
    @ApiOperation("添加")
    public void save(@RequestBody @Valid ActivityAddDTO dto) {
        activityService.save(dto);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        activityService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public ActivityPageVO update(@RequestBody ActivityEditDTO dto) {
        return activityService.update(dto);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public ActivityPageVO one(@RequestParam Integer activityId) {
        return activityService.one(activityId);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<ActivityPageVO> page(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) Integer activityCategoryId,
                                     @PageableDefault Pageable pageable) {
        return activityService.page(pageable, title, activityCategoryId);
    }

    @GetMapping("/recommend")
    @ApiOperation("推荐")
    public ActivityPageVO recommend(@RequestParam Integer activityId) {
        return activityService.recommend(activityId);
    }


    @GetMapping("/order/page")
    @ApiOperation("分页")
    public Page<ActivityOrderCMSPageVO> activityOrderPage(@RequestParam(required = false) String phone,
                                                          @PageableDefault Pageable pageable) {
        return activityService.activityOrderPage(pageable, phone);
    }

    @GetMapping("/order/check")
    @ApiOperation("验单")
    public Response checkActivityOrder(@RequestParam Integer activityOrderId){
        return activityService.checkActivityOrder(activityOrderId);
    }

    @PostMapping("/subCard")
    @ApiOperation("添加活动次卡")
    public Response saveActivitySubCard(@RequestBody @Valid ActivitySubCardDTO dto) {
        return activityService.saveActivitySubCardDTO(dto);
    }

    @PutMapping("/subCard")
    @ApiOperation("修改活动次卡")
    public Response updateActivitySubCard(@RequestBody ActivitySubCardDTO dto) {
        return activityService.saveActivitySubCardDTO(dto);
    }

    @GetMapping("/subCard/one")
    @ApiOperation("根据id查找")
    public Response findActivitySubCardById(@RequestParam Integer activitySubCardId) {
        return Response.build(2000,"成功！",activityService.findActivitySubCardById(activitySubCardId));
    }

    @DeleteMapping("/subCard")
    @ApiOperation("删除")
    public Response deleteActivitySubCard(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        activityService.deleteActivitySubCard(ids);
        return Response.success("成功！");
    }

    @GetMapping("/subCard")
    @ApiOperation("根据课程ID查询活动次卡列表")
    public Response findActivitySubCardsByActivityId(@RequestParam Integer activityId) {
        return Response.build(2000,"成功！",activityService.findActivitySubCardsByActivityId(activityId));
    }
}
