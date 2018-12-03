package com.luwei.models.coupon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-13
 **/
public interface CouponDao extends JpaRepository<Coupon, Integer>, JpaSpecificationExecutor<Coupon> {

    @Modifying
    @Query("update Coupon set deleted = 1 where couponId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);



}
