package com.luwei.controllers;

import com.luwei.common.Response;
import com.luwei.common.enums.type.EvaluateType;
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
    public List<EvaluateCmsVO> display(@RequestParam Integer evaluateId,@RequestParam EvaluateType evaluateType,
                                       @RequestParam Integer tripartiteId) {
        return evaluateService.display(evaluateId,evaluateType, tripartiteId);
    }

    @GetMapping
    @ApiOperation("根据关联id和类型获取评论列表")
    public List<EvaluateCmsVO> list(@RequestParam(required = false) Integer tripartiteId,
                                    @RequestParam(required = false) EvaluateType evaluateType) {
        return evaluateService.cmsVOList(tripartiteId, evaluateType);
    }



}
