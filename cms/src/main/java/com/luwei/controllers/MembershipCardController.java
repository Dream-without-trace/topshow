package com.luwei.controllers;



import com.luwei.common.Response;
import com.luwei.services.activity.cms.ActivityAddDTO;
import com.luwei.services.activity.cms.ActivityEditDTO;
import com.luwei.services.activity.cms.ActivityPageVO;
import com.luwei.services.membershipCard.MembershipCardService;
import com.luwei.services.membershipCard.cms.MembershipCardDTO;
import com.luwei.services.membershipCard.order.MembershipCardOrderService;
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
 * @program: topshow
 * @description: 会员卡
 * @author: ZhangHongJie
 * @create: 2018-12-12 14:28
 **/
@Slf4j
@RestController
@Api(tags = "会员卡模块")
@RequestMapping("api/membershipCard")
public class MembershipCardController {

    @Resource
    private MembershipCardService membershipCardService;

    @Resource
    private MembershipCardOrderService membershipCardOrderService;

    @PostMapping
    @ApiOperation("添加")
    public Response save(@RequestBody @Valid MembershipCardDTO dto) {
        return membershipCardService.save(dto);
    }


    @DeleteMapping
    @ApiOperation("删除")
    public Response delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        return membershipCardService.delete(ids);
    }

    @PutMapping
    @ApiOperation("修改")
    public Response update(@RequestBody MembershipCardDTO dto) {
        return membershipCardService.save(dto);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public Response one(@RequestParam Integer membershipCardId) {
        return Response.build(2000,"成功！",membershipCardService.findOne(membershipCardId));
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<MembershipCardDTO> page(@RequestParam(required = false) String title,
                                     @PageableDefault Pageable pageable) {
        return membershipCardService.page(pageable, title);
    }


    @GetMapping("/order")
    @ApiOperation("线下办理会员卡")
    public Response order(@RequestParam Integer userId,@RequestParam Integer membershipCardId) {
        return membershipCardOrderService.saveOrder(userId, membershipCardId);
    }



}
