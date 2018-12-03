package com.luwei.services.order;

import com.luwei.common.enums.status.OrderStatus;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.common.utils.ExcelUtil;
import com.luwei.common.utils.MathUtil;
import com.luwei.common.utils.RandomUtil;
import com.luwei.models.goods.Goods;
import com.luwei.models.order.Order;
import com.luwei.models.order.OrderDao;
import com.luwei.models.order.detail.OrderDetail;
import com.luwei.models.store.Store;
import com.luwei.models.user.User;
import com.luwei.models.user.coupon.UserCoupon;
import com.luwei.models.user.receiving.ReceivingAddress;
import com.luwei.services.goods.GoodsService;
import com.luwei.services.goods.web.GoodsSimpleVO;
import com.luwei.services.kuaidi.AliExpressService;
import com.luwei.services.kuaidi.KdVO;
import com.luwei.services.order.cms.DeliverDTO;
import com.luwei.services.order.cms.OrderEditDTO;
import com.luwei.services.order.cms.OrderPageVO;
import com.luwei.services.order.cms.OrderQueryDTO;
import com.luwei.services.order.detail.OrderDetailService;
import com.luwei.services.order.web.*;
import com.luwei.services.pay.PayService;
import com.luwei.services.specification.SpecificationService;
import com.luwei.services.store.StoreService;
import com.luwei.services.user.UserService;
import com.luwei.services.user.address.UserReceivingAddressService;
import com.luwei.services.user.address.web.ReceivingAddressWebVO;
import com.luwei.services.user.coupon.UserCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Leone
 * @since 2018-08-02
 **/
@Slf4j
@Service
@Transactional
public class OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private PayService payService;

    @Resource
    private UserService userService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private StoreService storeService;

    @Resource
    private UserCouponService userCouponService;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private SpecificationService specificationService;

    @Resource
    private UserReceivingAddressService userReceivingAddressService;

    @Resource
    private AliExpressService aliExpressService;

    /**
     * 分页
     *
     * @param pageable
     * @param query
     * @return
     */
    public Page<OrderPageVO> page(Pageable pageable, OrderQueryDTO query) {
        Specification<Order> specification = (Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            list.add(criteriaBuilder.equal(root.get("storeId").as(Integer.class), query.getStoreId()));


            if (!StringUtils.isEmpty(query.getOrderNo())) {
                list.add(criteriaBuilder.like(root.get("orderOutNo"), "%" + query.getOrderNo() + "%"));
            }

            if (query.getStart() != null && query.getEnd() != null) {
                list.add(criteriaBuilder.between(root.get("createTime"), query.getStart(), query.getEnd()));
            }

            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("orderId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return orderDao.findAll(specification, pageable).map(this::toOrderPageVO);
    }

    /**
     * 列表
     *
     * @param query
     * @return
     */
    public List<OrderPageVO> list(OrderQueryDTO query) {
        Specification<Order> specification = (Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            // 条件
            if (!StringUtils.isEmpty(query.getOrderNo())) {
                list.add(criteriaBuilder.like(root.get("orderOutNo"), "%" + query.getOrderNo() + "%"));
            }
            if (query.getStart() != null && query.getEnd() != null) {
                list.add(criteriaBuilder.between(root.get("createTime"), query.getStart(), query.getEnd()));
            }
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("orderId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return orderDao.findAll(specification).stream().map(this::toOrderPageVO).collect(Collectors.toList());
    }

    /**
     * 添加
     *
     * @param dto
     */
    public void save(OrderAddDTO dto) {
        Order order = new Order();
        BeanUtils.copyProperties(dto, order);
        orderDao.save(order);
    }

    /**
     * 保存
     *
     * @param dto
     */
    public Order save(Order dto) {
        return orderDao.save(dto);
    }

    /**
     * 查找一个
     *
     * @param orderId
     * @return
     */
    public Order findOne(Integer orderId) {
        Order order = orderDao.findById(orderId).orElse(null);
        Assert.notNull(order, "订单不存在");
        return order;
    }

    /**
     * 批量删除
     *
     * @param orderIds
     */
    public void delete(Set<Integer> orderIds) {
        orderDao.delByIds(new ArrayList<>(orderIds));
    }

    /**
     * 转换
     *
     * @param order
     * @return
     */
    public OrderPageVO toOrderPageVO(Order order) {
        OrderPageVO vo = new OrderPageVO();
        BeanUtils.copyProperties(order, vo);
        // 订单详情
        vo.setOrderDetail(orderDetailService.findByOrderId(order.getOrderId()));
        return vo;
    }


    /**
     * 导出excel
     *
     * @param pageable
     * @param query
     * @param response
     */
    public void export(Pageable pageable, OrderQueryDTO query, HttpServletResponse response) {
        Page<OrderPageVO> page = this.page(pageable, query);
        List<OrderPageVO> content = page.getContent();
        if (content.size() < 1) {
            return;
        }
        String[] title = new String[]{"订单编号", "创建时间", "商品数量", "总价", "收货人", "联系方式", "收货地址", "订单状态"};
        List<String[]> data = new ArrayList<>();
        for (OrderPageVO vo : content) {
            Date createTime = vo.getCreateTime();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(createTime.toInstant(), ZoneId.systemDefault());
            String dateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String[] field = new String[]{vo.getOrderOutNo(), dateTime, vo.getGoodsCount().toString(), vo.getTotal().toString(), vo.getUsername(), vo.getPhone(), vo.getAddress(), vo.getStatus().getValue()};
            data.add(field);
        }
        ExcelUtil.exportExcel(response, data, "订单信息", title);
    }

    /**
     * 查找一个
     *
     * @param orderId
     * @return
     */
    public OrderPageVO one(Integer orderId) {
        Order order = findOne(orderId);
        OrderPageVO vo = new OrderPageVO();
        BeanUtils.copyProperties(order, vo);
        return vo;
    }


    /**
     * 发货
     *
     * @param dto
     * @return
     */
    public OrderPageVO deliver(DeliverDTO dto) {
        Order order = this.findOne(dto.getOrderId());
        order.setStatus(OrderStatus.DELIVER);
        order.setDeliverTime(new Date());
        order.setLogisticsCompany(dto.getLogisticsCompany());
        order.setExpressNumber(dto.getExpressNumber());
        Order entity = orderDao.save(order);
        return this.toOrderPageVO(entity);
    }

    /**
     * 收货
     *
     * @param orderId
     * @param userId
     * @return
     */
    public void receiving(Integer orderId, Integer userId) {
        Order order = this.findOne(orderId);
        order.setStatus(OrderStatus.RECEIVING);
        Order entity = orderDao.save(order);
    }

    /**
     * 修改订单
     *
     * @param dto
     * @return
     */
    public OrderPageVO update(OrderEditDTO dto) {
        Order order = this.findOne(dto.getOrderId());
        BeanUtils.copyProperties(dto, order);
        Order entity = orderDao.save(order);
        return this.toOrderPageVO(entity);
    }

    /**
     * 小程序端分页
     *
     * @param pageable
     * @param userId
     * @param status
     * @return
     */
    public Page<OrderWebPageVO> page(Pageable pageable, Integer userId, OrderStatus status) {
        Specification<Order> specification = (Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            // 条件
            list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), userId));
            if (status != null) {
                list.add(criteriaBuilder.equal(root.get("status").as(OrderStatus.class), status));
            }
            list.add(criteriaBuilder.notEqual(root.get("status").as(OrderStatus.class), OrderStatus.CANCEL));
            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("orderId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return orderDao.findAll(specification, pageable).map(this::toOrderWebPageVO);
    }

    /**
     * 转换
     *
     * @param order
     * @return
     */
    public OrderWebPageVO toOrderWebPageVO(Order order) {
        OrderWebPageVO vo = new OrderWebPageVO();
        BeanUtils.copyProperties(order, vo);
        vo.setOrderGoods(orderDetailService.findOrderGoods(order.getOrderId()));
        return vo;
    }

    /**
     * 取消订单
     *
     * @param orderId
     */
    public OrderWebPageVO cancel(Integer orderId) {
        Order order = this.findOne(orderId);
        order.setStatus(OrderStatus.CANCEL);
        Order entity = orderDao.save(order);
        return this.toOrderWebPageVO(entity);
    }


    /**
     * 支付订单
     *
     * @param dto
     */
    public Map<String, String> payGoodsOrder(GoodsOrderPayDTO dto, HttpServletRequest request) {
        final Integer[] total = {0};
        List<Order> orderList = orderDao.findAllById(dto.getOrderIds());
        String outTradeNo = "G" + 1 + RandomUtil.getNum(14);
        orderList.forEach(e -> e.setOutTradeNo(outTradeNo));
        orderDao.saveAll(orderList);
        if (ObjectUtils.isEmpty(orderList)) {
            throw new ValidateException(ExceptionMessage.ORDER_NOT_EXIST);
        }

        if (!ObjectUtils.isEmpty(dto.getCouponId())) {
            UserCoupon userCoupon = userCouponService.findOne(dto.getCouponId());
            total[0] = MathUtil.discount(total[0], userCoupon.getDiscount());
            // 计算每个订单的优惠金额
            orderList.forEach(e -> {
                e.setCoupon(MathUtil.discount(e.getAmount(), userCoupon.getDiscount()));
                orderDao.save(e);
            });
            orderList.forEach(e -> total[0] = total[0] + (MathUtil.discount(e.getAmount(), userCoupon.getDiscount()) + e.getFreight()));
        } else {
            orderList.forEach(e -> total[0] += e.getTotal());
        }
        // 调用第三方支付接口
        log.info("{}", total[0]);
        return payService.xcxPay(total[0], dto.getOpenId(), outTradeNo, request);
    }


    /**
     * 创建单个订单
     *
     * @param dto
     * @return
     */
    public AffirmOrderVO createOneOrder(OrderAddOneDTO dto) {
        AffirmOrderVO vo = new AffirmOrderVO();
        Store store = storeService.findOne(dto.getStoreId());
        Goods goods = goodsService.findOne(dto.getGoodsId());
        com.luwei.models.specification.Specification specification = specificationService.findOne(dto.getSpecificationId());
        User user = userService.findOne(dto.getUserId());
        ReceivingAddress receivingAddress = userReceivingAddressService.findByUserId(dto.getUserId());
        ReceivingAddressWebVO addressVO = new ReceivingAddressWebVO();
        BeanUtils.copyProperties(receivingAddress, addressVO);
        addressVO.setFullAddress(receivingAddress.getProvince() + " " + receivingAddress.getCity() + " " + receivingAddress.getRegion() + " " + receivingAddress.getAddress());
        vo.setAddress(addressVO);
        vo.setCouponList(userCouponService.findUserCanUsable(dto.getUserId()));
        List<OrderGoodsSimpleVO> orderGoodsList = new ArrayList<>();
        OrderGoodsSimpleVO orderGoodsVO = new OrderGoodsSimpleVO();
        //生成订单
        Order order = createOrder(dto, user, goods, specification, receivingAddress);

        orderGoodsVO.setStoreName(store.getName());
        orderGoodsVO.setLogo(store.getLogo());
        orderGoodsVO.setStoreId(store.getStoreId());
        orderGoodsVO.setOrderId(order.getOrderId());
        GoodsSimpleVO goodsSimpleVO = new GoodsSimpleVO();
        goodsSimpleVO.setSpecificationName(specification.getName());
        goodsSimpleVO.setSpecificationId(specification.getSpecificationId());
        goodsSimpleVO.setPrice(specification.getPrice());
        goodsSimpleVO.setPicture(specification.getPicture());
        goodsSimpleVO.setCount(dto.getCount());
        goodsSimpleVO.setGoodsId(goods.getGoodsId());
        goodsSimpleVO.setGoodsName(goods.getName());
        orderGoodsVO.setGoodsSimpleVOList(Collections.singletonList(goodsSimpleVO));
        orderGoodsList.add(orderGoodsVO);
        vo.setOrderGoodsList(orderGoodsList);
        vo.setOrderTotal(order.getTotal());
        vo.setFreightTotal(order.getFreight());
        return vo;
    }


    /**
     * 根据购物车生成订单
     *
     * @param dto
     */
    public Order createOrder(OrderAddOneDTO dto, User user, Goods goods,
                             com.luwei.models.specification.Specification specification,
                             ReceivingAddress receivingAddress) {
        Order order = new Order();
        String outTradeNo = "G" + RandomUtil.getNum(15);
        Integer freightTotal = goods.getFreight();
        Integer amountTotal = specification.getPrice() * dto.getCount();
        order.setStatus(OrderStatus.CREATE);
        order.setAmount(amountTotal);
        order.setGoodsCount(dto.getCount());
        order.setTotal(amountTotal + freightTotal);
        order.setRemark("");
        order.setUserId(user.getUserId());
        order.setUsername(user.getUsername());
        order.setPhone(user.getPhone());
        order.setAddress(receivingAddress.getProvince() + " " + receivingAddress.getCity() + " " + receivingAddress.getAddress());
        order.setFreight(freightTotal);
        order.setStoreId(dto.getStoreId());
        order.setCoupon(0);
        order.setOutTradeNo(outTradeNo);
        order.setOrderOutNo("TS" + RandomUtil.currentTimestamp() + RandomUtil.getNum(4));
        orderDao.save(order);

        // 减库存
        specificationService.inventoryReduction(dto.getSpecificationId(), dto.getCount());

        // 生成订单详情
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCount(dto.getCount());
        orderDetail.setGoodsId(dto.getGoodsId());
        orderDetail.setName(goods.getName());
        orderDetail.setPicture(specification.getPicture());
        orderDetail.setSpecificationId(dto.getSpecificationId());
        orderDetail.setOrderId(order.getOrderId());
        orderDetail.setPrice(specification.getPrice());
        orderDetail.setSpecificationName(specification.getName());
        orderDetail.setFlagType(FlagType.DENY);
        orderDetailService.save(orderDetail);
        return orderDao.save(order);
    }

    /**
     * 收货
     *
     * @param orderId
     */
    public void receiving(Integer orderId) {
        Order order = this.findOne(orderId);
        if (!order.getStatus().equals(OrderStatus.DELIVER)) {
            throw new ValidateException(ExceptionMessage.ORDER_STATUS_FAIL);
        }
        order.setStatus(OrderStatus.RECEIVING);
        order.setDeliverTime(new Date());
        orderDao.save(order);
    }

    /**
     * 获取单个订单详情
     *
     * @param userId
     * @param orderId
     * @return
     */
    public OrderWebPageVO findOne(Integer userId, Integer orderId) {
        Order order = this.findOne(orderId);
        return this.toOrderWebPageVO(order);
    }


    /**
     * 根据订单号查询订单
     *
     * @param outTradeNo
     * @return
     */
    public List<Order> findByOutTradeNo(String outTradeNo) {
        return orderDao.findOrdersByOutTradeNo(outTradeNo);
    }

    /**
     * 批量保存
     *
     * @param orderList
     */
    public void saveAll(List<Order> orderList) {
        orderDao.saveAll(orderList);
    }


    /**
     * 批量修改订单地址
     *
     * @param orderIds
     */
    public void updateAddress(Set<Integer> orderIds, String address, String username, String phone) {
        List<Order> orderList = orderDao.findAllById(new ArrayList<>(orderIds));
        orderList.forEach(e -> {
            e.setAddress(address);
            e.setUsername(username);
            e.setPhone(phone);
        });
        orderDao.saveAll(orderList);
    }

    public KdVO findKd(Integer orderId) {
        Order order = this.findOne(orderId);
        if (!StringUtils.isEmpty(order.getExpressNumber()) && !StringUtils.isEmpty(order.getLogisticsCompany())) {
            return aliExpressService.findKd(order.getExpressNumber(), order.getLogisticsCompany());
        } else {
            throw new ValidateException(ExceptionMessage.CURRENT_ORDER_IS_NOT_SHIPPED);
        }
    }
}

