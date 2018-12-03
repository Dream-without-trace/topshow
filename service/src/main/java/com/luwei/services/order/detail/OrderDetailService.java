package com.luwei.services.order.detail;

import com.luwei.models.order.Order;
import com.luwei.models.order.detail.OrderDetail;
import com.luwei.models.order.detail.OrderDetailDao;
import com.luwei.models.store.Store;
import com.luwei.models.user.User;
import com.luwei.models.user.receiving.ReceivingAddress;
import com.luwei.services.goods.web.GoodsSimpleVO;
import com.luwei.services.order.OrderService;
import com.luwei.services.order.cms.OrderGoodsDetailVO;
import com.luwei.services.order.detail.web.OrderDetailSimpleVO;
import com.luwei.services.order.detail.web.OrderDetailWebVO;
import com.luwei.services.order.detail.web.OrderGoodsWebVO;
import com.luwei.services.order.web.AffirmOrderVO;
import com.luwei.services.order.web.OrderGoodsSimpleVO;
import com.luwei.services.store.StoreService;
import com.luwei.services.user.UserService;
import com.luwei.services.user.address.UserReceivingAddressService;
import com.luwei.services.user.address.web.ReceivingAddressWebVO;
import com.luwei.services.user.coupon.UserCouponService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-25
 **/
@Slf4j
@Service
@Transactional
public class OrderDetailService {

    @Resource
    private OrderDetailDao orderDetailDao;

    @Resource
    private OrderService orderService;

    @Resource
    private StoreService storeService;

    @Resource
    private UserService userService;

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private UserReceivingAddressService userReceivingAddressService;

    /**
     * 根据订单id获取订单详情列表
     *
     * @param orderId
     * @return
     */
    public List<OrderGoodsDetailVO> findByOrderId(Integer orderId) {
        return orderDetailDao.findOrderDetailByOrderId(orderId);
    }

    /**
     * 根据订单id获取订单详情列表
     *
     * @param orderId
     * @return
     */
    public List<OrderDetail> list(Integer orderId) {
        return orderDetailDao.findOrderDetailsByOrderId(orderId);
    }

    /**
     * 查找订单商品详细信息
     *
     * @param orderId
     * @return
     */
    public List<OrderGoodsWebVO> findOrderGoods(Integer orderId) {
        return orderDetailDao.findOrderGoods(orderId);
    }

    /**
     * 删除订单详情
     *
     * @param orderId
     */
    public void delete(Integer orderId) {
        orderDetailDao.deleteByOrderId(orderId);
    }

    /**
     * 根据订单id和商品id查找
     *
     * @param orderId
     * @param goodsId
     * @return
     */
    public OrderDetail findByOrderIdAndGoodsId(Integer orderId, Integer goodsId) {
        OrderDetail orderDetail = orderDetailDao.findFirstByOrderIdAndGoodsId(orderId, goodsId);
        Assert.notNull(orderDetail, "订单详情不存在");
        return orderDetail;
    }

    /**
     * 保存订单详情
     *
     * @param orderDetail
     * @return
     */
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailDao.save(orderDetail);
    }

    /**
     * @param orderId 订单id
     * @param goodsId 商品id
     */
    public void updateFlagType(Integer orderId, Integer goodsId) {
        orderDetailDao.updateFlagType(orderId, goodsId);
    }

    /**
     * 查找订详情
     *
     * @param orderDetailId
     * @return
     */
    public OrderDetailSimpleVO findOne(Integer orderDetailId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderDetailId).get();
        Assert.notNull(orderDetail, "订单详情不存在");
        OrderDetailSimpleVO vo = new OrderDetailSimpleVO();
        BeanUtils.copyProperties(orderDetail, vo);
        return vo;
    }

    /**
     * 获取订单详情
     *
     * @param orderId
     * @param userId
     * @return
     */
    public AffirmOrderVO unreadOrderDetail(Integer orderId, Integer userId) {
        Order order = orderService.findOne(orderId);
        User user = userService.findOne(userId);
        AffirmOrderVO vo = new AffirmOrderVO();
        Store store = storeService.findOne(order.getStoreId());
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderIdAndDeletedIsFalse(orderId);

        ReceivingAddressWebVO addressVO = new ReceivingAddressWebVO();
        ReceivingAddress receivingAddress = userReceivingAddressService.findByUserId(userId);
        String address = order.getAddress();
        addressVO.setFullAddress(address);
        addressVO.setPhone(order.getPhone());
        addressVO.setUserId(user.getUserId());
        addressVO.setUsername(order.getUsername());
        addressVO.setReceivingAddressId(receivingAddress.getReceivingAddressId());
        OrderGoodsSimpleVO orderGoodsVO = new OrderGoodsSimpleVO();


        orderGoodsVO.setStoreName(store.getName());
        orderGoodsVO.setLogo(store.getLogo());
        orderGoodsVO.setStoreId(store.getStoreId());
        orderGoodsVO.setOrderId(order.getOrderId());
        orderGoodsVO.setGoodsSimpleVOList(orderDetailList.stream().map(e -> {
            GoodsSimpleVO goodsSimpleVO = new GoodsSimpleVO();
            BeanUtils.copyProperties(e, goodsSimpleVO);
            goodsSimpleVO.setGoodsName(e.getName());
            return goodsSimpleVO;
        }).collect(Collectors.toList()));

        vo.setOrderGoodsList(Collections.singletonList(orderGoodsVO));
        vo.setCouponList(userCouponService.findUserCanUsable(userId));
        vo.setAddress(addressVO);
        vo.setOrderTotal(order.getTotal());
        vo.setFreightTotal(order.getFreight());
        return vo;
    }

    /**
     * 已支付订单详情
     *
     * @param orderId
     * @param userId
     * @return
     */
    public OrderDetailWebVO paidOrderDetail(Integer orderId, Integer userId) {

        return null;
    }


}
