package com.luwei.services.activity.order;

import com.luwei.common.Response;
import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.utils.MathUtil;
import com.luwei.common.utils.RandomUtil;
import com.luwei.models.activity.Activity;
import com.luwei.models.activity.order.ActivityOrder;
import com.luwei.models.activity.order.ActivityOrderDao;
import com.luwei.models.coupon.Coupon;
import com.luwei.models.user.User;
import com.luwei.models.user.coupon.UserCoupon;
import com.luwei.services.activity.ActivityService;
import com.luwei.services.activity.cms.ActivityCmsVO;
import com.luwei.services.activity.order.web.ActivityAddDTO;
import com.luwei.services.activity.order.web.ActivityOrderWebPageVO;
import com.luwei.services.activity.web.ActivityAffirmVO;
import com.luwei.services.activity.web.ActivityApplyDTO;
import com.luwei.services.coupon.CouponService;
import com.luwei.services.coupon.web.CouponOrderListVO;
import com.luwei.services.pay.PayService;
import com.luwei.services.user.UserService;
import com.luwei.services.user.coupon.UserCouponService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-23
 **/
@Slf4j
@Service
public class ActivityOrderService {

    @Resource
    private ActivityOrderDao activityOrderDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private ActivityService activityService;

    @Resource
    private UserService userService;

    @Resource
    private CouponService couponService;

    @Resource
    private PayService payService;

    @Resource
    private UserCouponService userCouponService;


    /**
     * 保存
     *
     * @param activityOrder
     */
    public void save(ActivityOrder activityOrder) {
        activityOrderDao.save(activityOrder);
    }


    /**
     * 保存活动订单
     *
     * @param activity
     * @param user
     * @param dto
     */
    public ActivityOrder saveActivityOrder(Activity activity, User user, ActivityApplyDTO dto) {
        ActivityOrder activityOrder = new ActivityOrder();
        String outTradeNo = "A" + RandomUtil.getNum(15);
        activityOrder.setUserId(user.getUserId());
        activityOrder.setActivityId(activity.getActivityId());
        activityOrder.setActivityTitle(activity.getTitle());
        activityOrder.setAddress(activity.getProvince() + " " + activity.getCity() + " " + activity.getAddress());
        activityOrder.setCount(dto.getCount());
        activityOrder.setStartTime(activity.getStartTime());
        activityOrder.setEndTime(activity.getEndTime());
        activityOrder.setStatus(ActivityOrderStatus.CREATE);
        activityOrder.setTotal(dto.getCount() * activity.getPrice());
        activityOrder.setFlagType(FlagType.DENY);
        activityOrder.setOutTradeNo(outTradeNo);
        return activityOrderDao.save(activityOrder);
    }


    /**
     * 提交活动订单
     *
     * @param dto
     */
    public Response commit(ActivityAddDTO dto, HttpServletRequest request) throws Exception {
        ActivityOrder activityOrder = findOne(dto.getActivityOrderId());
        Activity activity = activityService.findOne(activityOrder.getActivityId());
        String outTradeNo = "A" + RandomUtil.getNum(15);
        activityOrder.setOutTradeNo(outTradeNo);
        activityOrderDao.save(activityOrder);
        User user = userService.findOne(dto.getUserId());
        Integer total = 0;
        if (activity.getPrice() > 0) {
            if (ObjectUtils.isEmpty(dto.getCouponId())) {
                total = activityOrder.getCount() * activity.getPrice();
            } else {
                // 抵扣优惠券
                UserCoupon userCoupon = userCouponService.findUserCanUsable(user.getUserId(), dto.getCouponId());
                Coupon coupon = couponService.findOne(userCoupon.getCouponId());
                total = MathUtil.discount(activityOrder.getCount() * activity.getPrice(), coupon.getDiscount());
                userCouponService.consume(coupon.getCouponId(), user.getUserId());
            }
        }
        activityOrder.setTotal(total);
        activityOrder.setStatus(ActivityOrderStatus.CREATE);
        ActivityOrder entity = this.activityOrderDao.save(activityOrder);
        // 购买活动给用户添加积分默认10
        userService.addIntegral(user.getUserId(), 10);
        if (activityOrder.getTotal() < 1) {
            // 免费票不用调用微信支付
            activityOrder.setStatus(ActivityOrderStatus.PAY);
            activityOrderDao.save(activityOrder);
            return Response.build(20001, "success", null);
        } else {
            return Response.build(20000, "success", payService.xcxPay(total, dto.getOpenid(), activityOrder.getOutTradeNo(), request));
        }
    }

    /**
     * 根据用户id查询用户参加的活动
     *
     * @param userId
     * @return
     */
    public List<ActivityCmsVO> findByUserId(Integer userId) {
        List<ActivityOrderStatus> statusList = Arrays.asList(ActivityOrderStatus.CREATE, ActivityOrderStatus.CLOSE);
        return activityOrderDao.findByUserId(userId, statusList);
    }


    /**
     * 小程序端分页
     *
     * @param pageable
     * @param userId
     * @param status
     * @return
     */
    public PageImpl page(Pageable pageable, Integer userId, ActivityOrderStatus status) {

        String contentSql = "select new com.luwei.services.activity.order.web.ActivityOrderWebPageVO " +
                "(ao.activityOrderId,ao.userId,a.activityId,ao.activityTitle,a.city,a.province,a.picture,a.startTime,ao.status,ao.flagType)" +
                " from ActivityOrder ao, Activity a where ao.activityId = a.activityId and ao.userId = ?0";

        String countSql = "select count(a.activityId) from ActivityOrder ao, Activity a where ao.activityId = a.activityId and ao.userId = ?0 ";

        if (!ObjectUtils.isEmpty(status)) {
            countSql += " and ao.status = ?1 order by ao.id desc";
            contentSql += " and ao.status = ?1";
        } else {
            contentSql += " order by ao.id desc";
        }

        Query countQuery = entityManager.createQuery(countSql);
        Query contentQuery = entityManager.createQuery(contentSql);

        countQuery.setParameter(0, userId);
        contentQuery.setParameter(0, userId);
        if (!ObjectUtils.isEmpty(status)) {
            countQuery.setParameter(1, status);
            contentQuery.setParameter(1, status);
        }

        contentQuery.setMaxResults(pageable.getPageSize());
        contentQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        Long total = (Long) countQuery.getSingleResult();
        List content = contentQuery.getResultList();
        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 查找一个
     *
     * @param activityOrderId
     * @return
     */
    public ActivityOrder findOne(Integer activityOrderId) {
        ActivityOrder activityOrder = activityOrderDao.findById(activityOrderId).orElse(null);
        Assert.notNull(activityOrder, "活动订单不存在");
        return activityOrder;
    }


    /**
     * 查询小于当前时间的活动订单
     *
     * @return
     */
    public List<ActivityOrder> findOverdue(Date date, ActivityOrderStatus status) {
        return activityOrderDao.findActivityOrdersByEndTimeLessThanAndStatus(date, status);
    }

    /**
     * 修改活动状态
     *
     * @param activityOrderList
     */
    public void updateActivityOrderStatus(List<ActivityOrder> activityOrderList) {
        activityOrderDao.saveAll(activityOrderList);
    }


    /**
     * 删除活动订单
     *
     * @param userId
     * @param activityOrderId
     */
    public void delete(Integer userId, Integer activityOrderId) {
        ActivityOrder activityOrder = activityOrderDao.findActivityOrdersByActivityOrderIdAndUserIdAndDeletedIsFalse(activityOrderId, userId);
        Assert.notNull(activityOrder, "活动订单不存在");
        activityOrderDao.delete(activityOrder);
    }


    /**
     * 获取一个
     *
     * @param userId
     * @param activityOrderId
     * @return
     */
    public ActivityOrderWebPageVO findOne(Integer userId, Integer activityOrderId) {
        ActivityOrder activityOrder = activityOrderDao.findActivityOrdersByActivityOrderIdAndUserIdAndDeletedIsFalse(activityOrderId, userId);
        Assert.notNull(activityOrder, "活动订单不存在");
        return this.toActivityOrderWebPageVO(activityOrder);
    }

    /**
     * 转换
     *
     * @param activityOrder
     * @return
     */
    private ActivityOrderWebPageVO toActivityOrderWebPageVO(ActivityOrder activityOrder) {
        ActivityOrderWebPageVO vo = new ActivityOrderWebPageVO();
        BeanUtils.copyProperties(activityOrder, vo);
        Activity activity = activityService.findOne(activityOrder.getActivityId());
        vo.setCity(activity.getCity());
        vo.setProvince(activity.getProvince());
        vo.setPicture(activity.getPicture());
        return vo;
    }

    /**
     * 根据外部订单号查找活动
     *
     * @param outTradeNo
     * @return
     */
    public ActivityOrder findByOutTradeNo(String outTradeNo) {
        return activityOrderDao.findFirstByOutTradeNo(outTradeNo);
    }

    /**
     * 获取订单详情
     *
     * @param activityOrderId
     * @return
     */
    public ActivityAffirmVO findOrderDetail(Integer activityOrderId) {
        ActivityOrder activityOrder = findOne(activityOrderId);
        Activity activity = activityService.findOne(activityOrder.getActivityId());
        User user = userService.findOne(activityOrder.getUserId());
        ActivityAffirmVO vo = new ActivityAffirmVO();
        List<CouponOrderListVO> couponOrderVOList = userCouponService.findUserCanUsable(user.getUserId());
        BeanUtils.copyProperties(activity, vo);
        vo.setUsername(user.getUsername());
        vo.setUserId(user.getUserId());
        vo.setPhone(user.getPhone());
        vo.setCouponList(couponOrderVOList);
        vo.setCount(activityOrder.getCount());
        vo.setActivityOrderId(activityOrder.getActivityOrderId());
        return vo;
    }

    public Integer findRegisteredNumByActivityId(Integer activityId) {
        Integer countNum = 0;
        List<ActivityOrder> list =  activityOrderDao.findAllByActivityIdAndDeletedIsFalse(activityId);
        if (list != null && list.size()>0) {
            for (ActivityOrder activityOrder:list) {
                if (activityOrder != null) {
                    ActivityOrderStatus status = activityOrder.getStatus();
                    if (status != null && (status.equals(ActivityOrderStatus.PAY)||status.equals(ActivityOrderStatus.JOIN)
                            ||status.equals(ActivityOrderStatus.COMPLETE))) {
                        countNum+=activityOrder.getCount();
                    }
                }
            }

        }
        return countNum;
    }

    public List<ActivityOrder> findAllByActivityIdAndUserIdAndStatus(Integer activityId, Integer userId, ActivityOrderStatus activityOrderStatus) {
        return activityOrderDao.findAllByActivityIdAndUserIdAndStatusAndDeletedIsFalse(activityId,userId,activityOrderStatus);
    }
}
