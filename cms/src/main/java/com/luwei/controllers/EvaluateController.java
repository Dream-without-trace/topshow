package com.luwei.controllers;

import com.luwei.services.evaluate.EvaluateService;
import com.luwei.services.evaluate.cms.EvaluateCmsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Slf4j
@RestController
@Api(tags = "评论模块")
@RequestMapping("/api/evaluate")
public class EvaluateController {

    @Resource
    private EvaluateService evaluateService;

    @PutMapping("/display")
    @ApiOperation("显示|隐藏")
    public List<EvaluateCmsVO> display(@RequestParam Integer evaluateId, @RequestParam Integer activityId) {
        return evaluateService.display(evaluateId, activityId);
    }

    @GetMapping
    @ApiOperation("根据活动id获取评论列表")
    public List<EvaluateCmsVO> list(@RequestParam Integer activityId) {
        return evaluateService.list(activityId);
    }


}
