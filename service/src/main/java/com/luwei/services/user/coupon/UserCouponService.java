package com.luwei.services.user.coupon;

import com.luwei.common.enums.type.FlagType;
import com.luwei.models.user.coupon.UserCoupon;
import com.luwei.models.user.coupon.UserCouponDao;
import com.luwei.services.coupon.web.CouponOrderListVO;
import com.luwei.services.coupon.web.CouponOrderVO;
import com.luwei.services.coupon.web.CouponWebVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-24
 **/
@Slf4j
@Service
@Transactional
public class UserCouponService {

    @Resource
    private UserCouponDao userCouponDao;

    /**
     * 根据用户id查找
     *
     * @param userId
     * @return
     */
    public List<CouponWebVO> findByUserId(Integer userId) {
        List<UserCoupon> userCouponList = userCouponDao.findUserCouponsByUserId(userId);
        return userCouponList.stream().map(this::toCouponWebVO).collect(Collectors.toList());
    }


    /**
     * 根据用户id查找可用优惠券
     *
     * @param userId
     * @return
     */
    public List<CouponOrderListVO> findUserCanUsable(Integer userId) {
        List<UserCoupon> userCouponList = userCouponDao.findUserCouponsByUserIdAndFlagTypeAndDeletedIsFalse(userId, FlagType.DENY);
        return userCouponList.stream().map(e -> {
            CouponOrderListVO vo = new CouponOrderListVO();
            vo.setCouponId(e.getUserCouponId());
            vo.setPicture(e.getPicture());
            vo.setName(e.getCouponTitle());
            vo.setDiscount(e.getDiscount());
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 根据用户id查找可用优惠券
     *
     * @param userId
     * @return
     */
    @Deprecated
    public List<CouponOrderVO> findByUserIdUsable(Integer userId) {
        List<UserCoupon> userCouponList = userCouponDao.findUserCouponsByUserIdAndFlagTypeAndDeletedIsFalse(userId, FlagType.DENY);
        return userCouponList.stream().map(this::toCouponOrderVO).collect(Collectors.toList());
    }

    /**
     * 转换
     *
     * @param userCoupon
     * @return
     */
    private CouponOrderVO toCouponOrderVO(UserCoupon userCoupon) {
        CouponOrderVO vo = new CouponOrderVO();
        vo.setCouponId(userCoupon.getUserCouponId());
        vo.setPicture(userCoupon.getPicture());
        vo.setName(userCoupon.getCouponTitle());
        return vo;
    }

    /**
     * 转换
     *
     * @param userCoupon
     * @return
     */
    private CouponWebVO toCouponWebVO(UserCoupon userCoupon) {
        CouponWebVO vo = new CouponWebVO();
        vo.setCouponId(userCoupon.getCouponId());
        vo.setName(userCoupon.getCouponTitle());
        return vo;
    }


    /**
     * 转换
     *
     * @param userCoupon
     * @return
     */
    public void save(UserCoupon userCoupon) {
        userCouponDao.save(userCoupon);
    }

    /**
     * 消费优惠券
     *
     * @param couponId 优惠券id
     * @param userId   用户id
     */
    public void consume(Integer couponId, Integer userId) {
        UserCoupon userCoupon = userCouponDao.findFirstByUserIdAndCouponId(userId, couponId);
        Assert.notNull(userCoupon, "用户优惠券不存在");
        userCoupon.setFlagType(FlagType.RIGHT);
        userCouponDao.save(userCoupon);
    }

    /**
     * 用户可用优惠券
     *
     * @param userId
     * @param couponId
     * @return
     */
    public UserCoupon usableCoupon(Integer userId, Integer couponId) {
        UserCoupon userCoupon = userCouponDao.findFirstByUserIdAndCouponIdAndFlagType(userId, couponId, FlagType.DENY);
        Assert.notNull(userCoupon, "用户可用优惠券不存在");
        return userCoupon;
    }


    /**
     * 根据id查找
     *
     * @param couponId
     * @return
     */
    public UserCoupon findOne(Integer couponId) {
        Optional<UserCoupon> userCoupon = userCouponDao.findById(couponId);
        Assert.notNull(userCoupon.get(), "用户优惠券不存在");
        return userCoupon.get();
    }

    public UserCoupon findUserCanUsable(Integer userId, Integer couponId) {
        UserCoupon userCoupon = userCouponDao.findFirstByUserIdAndUserCouponIdAndFlagType(userId, couponId, FlagType.DENY);
        Assert.notNull(userCoupon, "用户可用优惠券不存在");
        return userCoupon;
    }
}
