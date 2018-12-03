package com.luwei.controllers;

import com.luwei.services.kuaidi.KdVO;
import com.luwei.services.order.OrderService;
import com.luwei.services.order.cms.DeliverDTO;
import com.luwei.services.order.cms.OrderEditDTO;
import com.luwei.services.order.cms.OrderPageVO;
import com.luwei.services.order.cms.OrderQueryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-06
 **/
@Slf4j
@RestController
@Api(tags = "订单模块")
@RequestMapping("api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @DeleteMapping
    @ApiOperation("删除")
    public void delete(@RequestParam @ApiParam("id列表") Set<Integer> ids) {
        orderService.delete(ids);
    }

    @GetMapping("/one")
    @ApiOperation("根据id查找")
    public OrderPageVO one(@RequestParam Integer orderId) {
        return orderService.one(orderId);
    }

    @GetMapping("/page")
    @ApiOperation("分页")
    public Page<OrderPageVO> page(@ModelAttribute OrderQueryDTO query, @PageableDefault Pageable pageable) {
        return orderService.page(pageable, query);
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public List<OrderPageVO> list(@ModelAttribute OrderQueryDTO query) {
        return orderService.list(query);
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(Pageable pageable, @RequestBody OrderQueryDTO query, HttpServletResponse response) {
        orderService.export(pageable, query, response);
    }

    @PutMapping("/deliver")
    @ApiOperation("发货")
    public OrderPageVO deliver(@RequestBody DeliverDTO dto) {
        return orderService.deliver(dto);
    }

    @PutMapping
    @ApiOperation("修改订单")
    public OrderPageVO update(@RequestBody OrderEditDTO dto) {
        return orderService.update(dto);
    }

    @GetMapping("/express")
    @ApiOperation("查看快递")
    public KdVO express(@RequestParam Integer orderId) {
        return orderService.findKd(orderId);
    }

}
