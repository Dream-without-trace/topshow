package com.luwei.controllers;

import com.luwei.services.refund.RefundService;
import com.luwei.services.refund.cms.RefundEditDTO;
import com.luwei.services.refund.cms.RefundPageVO;
import com.luwei.services.refund.cms.RefundQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-21
 **/
@Slf4j
@RestController
@Api(tags = "售后模块")
@RequestMapping("api/refund")
public class RefundController {

    @Resource
    private RefundService refundService;

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        refundService.delete(ids);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public RefundPageVO one(@RequestParam Integer refundId) {
        return refundService.one(refundId);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<RefundPageVO> page(@ModelAttribute RefundQueryDTO dto, @PageableDefault Pageable pageable) {
        return refundService.page(pageable, dto);
    }

    @PutMapping
    @ApiOperation("修改售后订单状态")
    public RefundPageVO update(@RequestBody @Valid RefundEditDTO dto) {
        return refundService.update(dto);
    }


}
