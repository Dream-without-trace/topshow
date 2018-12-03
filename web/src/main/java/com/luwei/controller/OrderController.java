package com.luwei.controller;

import com.luwei.common.Response;
import com.luwei.common.enums.status.OrderStatus;
import com.luwei.services.order.OrderService;
import com.luwei.services.order.web.AffirmOrderVO;
import com.luwei.services.order.web.GoodsOrderPayDTO;
import com.luwei.services.order.web.OrderAddOneDTO;
import com.luwei.services.order.web.OrderWebPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-01
 **/
@Slf4j
@RestController
@Api(tags = "订单模块")
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Page<OrderWebPageVO> page(@PageableDefault Pageable pageable,
                                     @RequestParam Integer userId,
                                     @RequestParam(required = false) OrderStatus status) {
        return orderService.page(pageable, userId, status);
    }

    @GetMapping("/one")
    @ApiOperation("获取单个订单")
    public OrderWebPageVO findOne(Integer userId, Integer orderId) {
        return orderService.findOne(userId, orderId);
    }

    @DeleteMapping
    @ApiOperation("删除订单")
    public void delete(@RequestParam Set<Integer> orderIds) {
        orderService.delete(orderIds);
    }

    @DeleteMapping("/cancel")
    @ApiOperation("取消订单")
    public OrderWebPageVO cancel(@RequestParam Integer orderId) {
        return orderService.cancel(orderId);
    }


    @PostMapping("/pay")
    @ApiOperation("支付商品订单")
    public Map<String, String> payGoodsOrder(@RequestBody GoodsOrderPayDTO dto, HttpServletRequest request) {
        return orderService.payGoodsOrder(dto, request);
    }

    @PostMapping("/create")
    @ApiOperation("提交订单")
    public AffirmOrderVO commit(@RequestBody OrderAddOneDTO dto) {
        return orderService.createOneOrder(dto);
    }

    @PutMapping("/receiving")
    @ApiOperation("收货")
    public Response receiving(@RequestParam Integer orderId) {
        orderService.receiving(orderId);
        return Response.success("收货成功");
    }

    @PutMapping("/address")
    @ApiOperation("批量修改收货地址")
    public Response updateAddress(@RequestParam Set<Integer> orderIds,
                                  @RequestParam String address,
                                  @RequestParam String username,
                                  @RequestParam String phone) {
        orderService.updateAddress(orderIds, address, username, phone);
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        map.put("username", username);
        map.put("phone", phone);
        return Response.build(20000, "success", map);
    }


}
