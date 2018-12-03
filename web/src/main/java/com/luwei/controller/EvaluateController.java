package com.luwei.controller;

import com.luwei.common.Response;
import com.luwei.common.enums.type.EvaluateType;
import com.luwei.services.evaluate.EvaluateService;
import com.luwei.services.evaluate.web.EvaluateDTO;
import com.luwei.services.evaluate.web.EvaluateWebListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @PostMapping
    @ApiOperation("添加评价(商品或活动)")
    public Response save(@RequestBody @Valid EvaluateDTO dto) {
        try {
            evaluateService.save(dto);
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
        return Response.success(dto);
    }

    @GetMapping
    @ApiOperation("获取评价(商品或活动)")
    public Page<EvaluateWebListVO> findEvaluate(@PageableDefault Pageable pageable,
                                                @RequestParam Integer id,
                                                @RequestParam EvaluateType type) {
        return evaluateService.page(pageable, id, type);
    }


}
