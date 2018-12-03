package com.luwei.services.shopping;

import com.luwei.common.enums.status.OrderStatus;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.common.utils.RandomUtil;
import com.luwei.models.goods.Goods;
import com.luwei.models.order.Order;
import com.luwei.models.order.detail.OrderDetail;
import com.luwei.models.shopping.Shopping;
import com.luwei.models.shopping.ShoppingDao;
import com.luwei.models.specification.Specification;
import com.luwei.models.store.Store;
import com.luwei.models.user.User;
import com.luwei.models.user.receiving.ReceivingAddress;
import com.luwei.services.goods.GoodsService;
import com.luwei.services.goods.web.GoodsSimpleVO;
import com.luwei.services.order.OrderService;
import com.luwei.services.order.detail.OrderDetailService;
import com.luwei.services.order.web.AffirmOrderVO;
import com.luwei.services.order.web.OrderAddDTO;
import com.luwei.services.order.web.OrderGoodsSimpleVO;
import com.luwei.services.shopping.web.ShoppingAddDTO;
import com.luwei.services.shopping.web.ShoppingEditDTO;
import com.luwei.services.shopping.web.ShoppingPageVO;
import com.luwei.services.specification.SpecificationService;
import com.luwei.services.store.StoreService;
import com.luwei.services.user.UserService;
import com.luwei.services.user.address.UserReceivingAddressService;
import com.luwei.services.user.address.web.ReceivingAddressWebVO;
import com.luwei.services.user.coupon.UserCouponService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Leone
 * @since 2018-08-02
 **/
@Slf4j
@Service
@Transactional
public class ShoppingService {

    @Resource
    private ShoppingDao shoppingDao;

    @Resource
    private SpecificationService specificationService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @Resource
    private StoreService storeService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private UserReceivingAddressService userReceivingAddressService;

    /**
     * web分页
     *
     * @param pageable
     * @param userId
     * @return
     */
    public Page<ShoppingPageVO> page(Pageable pageable, Integer userId) {
        return shoppingDao.findByUserId(userId, pageable).map(this::toShoppingPageVO);
    }

    /**
     * 转换
     *
     * @param shopping
     * @return
     */
    private ShoppingPageVO toShoppingPageVO(Shopping shopping) {
        ShoppingPageVO vo = new ShoppingPageVO();
        BeanUtils.copyProperties(shopping, vo);
        return vo;
    }


    /**
     * 添加到购物车
     *
     * @param dto
     */
    public void save(ShoppingAddDTO dto) {
        Shopping shopping = shoppingDao.findFirstByUserIdAndSpecificationId(dto.getUserId(), dto.getSpecificationId());
        if (ObjectUtils.isEmpty(shopping)) {
            shopping = new Shopping();
        } else {
            shopping.setCount(shopping.getCount() + dto.getCount());
            shoppingDao.save(shopping);
            return;
        }
        BeanUtils.copyProperties(dto, shopping);
        Goods goods = goodsService.findOne(dto.getGoodsId());
        Specification specification = specificationService.findOne(dto.getSpecificationId());
        shopping.setSpecificationName(specification.getName());
        shopping.setGoodsName(goods.getName());
        shopping.setUserId(dto.getUserId());
        shopping.setStoreId(dto.getStoreId());
        shopping.setPrice(specification.getPrice());
        shopping.setPicture(specification.getPicture());
        shoppingDao.save(shopping);
    }

    /**
     * 修改购物车中商品
     *
     * @param dto
     */
    public ShoppingPageVO update(ShoppingEditDTO dto) {
        Shopping shopping = this.findOne(dto.getShoppingId());

        Specification specification = specificationService.findOne(shopping.getSpecificationId());

        if (specification.getInventory() < dto.getCount()) {
            throw new ValidateException(ExceptionMessage.INVENTORY_SHORTAGE);
        }

        if (!shopping.getUserId().equals(dto.getUserId())) {
            throw new ValidateException(ExceptionMessage.USER_ID_AND_SHOPPING_CART_ID_DO_NOT_MATCH);
        }
        shopping.setCount(dto.getCount());
        Shopping entity = shoppingDao.save(shopping);
        return this.toShoppingPageVO(entity);
    }


    /**
     * 删除购物车商品
     *
     * @param ids
     */
    public void delete(Set<Integer> ids, Integer userId) {
        shoppingDao.delByIds(ids, userId);
    }

    /**
     * 查找一个
     *
     * @param shoppingId
     * @return
     */
    public Shopping findOne(Integer shoppingId) {
        Shopping shopping = shoppingDao.findById(shoppingId).orElse(null);
        Assert.notNull(shopping, "购物车商品不存在");
        return shopping;
    }


    /**
     * 清空购物车
     *
     * @param dto
     * @return
     */
    public AffirmOrderVO clearing(OrderAddDTO dto) {
        String outTradeNo = "G" + RandomUtil.getNum(15);
        User user = userService.findOne(dto.getUserId());
        AffirmOrderVO vo = new AffirmOrderVO();
        ReceivingAddress address = userReceivingAddressService.findByUserId(dto.getUserId());
        ReceivingAddressWebVO addressVO = new ReceivingAddressWebVO();
        BeanUtils.copyProperties(address, addressVO);
        addressVO.setFullAddress(address.getProvince() + " " + address.getCity() + " " + address.getRegion() + " " + address.getAddress());

        List<Shopping> shoppingList = shoppingDao.findAllById(dto.getShoppingIds());
        Set<Integer> storeIds = shoppingList.stream().map(Shopping::getStoreId).collect(Collectors.toSet());
        Integer[] freightTotal = {0};
        Integer[] amountTotal = {0};
        List<OrderGoodsSimpleVO> orderGoodsList = new ArrayList<>();
        storeIds.forEach(e -> {
            List<Shopping> storeShoppingList = new ArrayList<>();
            for (int i = 0; i < shoppingList.size(); i++) {
                if (shoppingList.get(i).getStoreId().equals(e)) {
                    storeShoppingList.add(shoppingList.get(i));
                }
            }
            Order order = createOrder(storeShoppingList, user, outTradeNo, address);
            freightTotal[0] += order.getFreight();
            amountTotal[0] += order.getAmount();
            Store store = storeService.findOne(e);
            OrderGoodsSimpleVO orderGoods = new OrderGoodsSimpleVO();
            BeanUtils.copyProperties(store, orderGoods);
            orderGoods.setStoreName(store.getName());
            orderGoods.setOrderId(order.getOrderId());
            orderGoods.setGoodsSimpleVOList(storeShoppingList.stream().map(b -> {
                GoodsSimpleVO goodsSimpleVO = new GoodsSimpleVO();
                BeanUtils.copyProperties(b, goodsSimpleVO);
                goodsSimpleVO.setGoodsName(b.getGoodsName());
                return goodsSimpleVO;
            }).collect(Collectors.toList()));
            orderGoodsList.add(orderGoods);
        });

        vo.setAddress(addressVO);
        vo.setFreightTotal(freightTotal[0]);
        vo.setOrderTotal(amountTotal[0] + freightTotal[0]);
        vo.setOrderGoodsList(orderGoodsList);
        vo.setCouponList(userCouponService.findUserCanUsable(dto.getUserId()));
        clearShopping(dto);
        return vo;
    }

    /**
     * 删除用户购物车商品
     */
    public void clearShopping(OrderAddDTO dto) {
        shoppingDao.delByIds(dto.getShoppingIds(), dto.getUserId());
    }


    /**
     * 根据购物车生成订单
     *
     * @param shoppingList
     */
    public Order createOrder(List<Shopping> shoppingList, User user, String outTradeNo, ReceivingAddress address) {
        Order order = new Order();
        Integer goodsCount = 0;
        Integer freightTotal = 0;
        Integer amountTotal = 0;

        order.setOutTradeNo(outTradeNo);
        order.setStatus(OrderStatus.CREATE);
        order.setAmount(amountTotal);
        order.setGoodsCount(goodsCount);
        order.setTotal(amountTotal + freightTotal);
        order.setRemark("");
        order.setUserId(user.getUserId());
        order.setFreight(freightTotal);
        order.setOrderOutNo("TS" + RandomUtil.getDateStr(0));
        order.setUsername(address.getUsername());
        order.setPhone(address.getPhone());
        order.setAddress(address.getProvince() + " " + address.getCity() + " " + address.getRegion() + " " + address.getAddress());
        orderService.save(order);

        for (int i = 0; i < shoppingList.size(); i++) {
            Shopping shopping = shoppingList.get(i);

            // 减库存
            specificationService.inventoryReduction(shopping.getSpecificationId(), shopping.getCount());

            Goods goods = goodsService.findOne(shopping.getGoodsId());
            freightTotal += goods.getFreight();
            goodsCount += shopping.getCount();
            Specification specification = specificationService.findOne(shopping.getSpecificationId());
            amountTotal += (specification.getPrice() * shopping.getCount());
            order.setStoreId(shopping.getStoreId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setFlagType(FlagType.DENY);
            orderDetail.setCount(shopping.getCount());
            orderDetail.setGoodsId(shopping.getGoodsId());
            orderDetail.setName(goods.getName());
            orderDetail.setPicture(shopping.getPicture());
            orderDetail.setSpecificationId(shopping.getSpecificationId());
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setPrice(shopping.getPrice());
            orderDetail.setSpecificationName(shopping.getSpecificationName());
            orderDetailService.save(orderDetail);
        }
        order.setCoupon(0);
        order.setAmount(amountTotal);
        order.setGoodsCount(goodsCount);
        order.setTotal(amountTotal + freightTotal);
        order.setUserId(user.getUserId());
        order.setFreight(freightTotal);
        return orderService.save(order);
    }


}

