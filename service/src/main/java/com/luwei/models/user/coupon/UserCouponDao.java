package com.luwei.models.user.coupon;

import com.luwei.common.enums.type.FlagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-24
 **/
public interface UserCouponDao extends JpaRepository<UserCoupon, Integer>, JpaSpecificationExecutor<UserCoupon> {

    List<UserCoupon> findUserCouponsByUserId(Integer userId);

    List<UserCoupon> findUserCouponsByUserIdAndFlagTypeAndDeletedIsFalse(Integer userId, FlagType flagType);

    UserCoupon findFirstByUserIdAndCouponId(Integer userId, Integer couponId);

    UserCoupon findFirstByUserIdAndCouponIdAndFlagType(Integer userId, Integer couponId, FlagType flagType);

    UserCoupon findFirstByUserIdAndUserCouponIdAndFlagType(Integer userId, Integer userCouponId, FlagType flagType);

}
