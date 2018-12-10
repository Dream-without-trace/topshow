package com.luwei.models.activity.order.subcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: topshow
 * @description: 活动次卡订单Dao
 * @author: ZhangHongJie
 * @create: 2018-12-09 00:28
 **/
public interface ActivitySubCardOrderDao  extends JpaRepository<ActivitySubCardOrder, Integer>, JpaSpecificationExecutor<ActivitySubCardOrder> {


    void deleteAllByUserIdAndActivityIdAndStatus(Integer userId, Integer activityId, Integer status);

    List<ActivitySubCardOrder> findAllByUserIdAndActivityIdAndStatusOrderByPayTimeDesc(Integer userId, Integer activityId, Integer status);

    List<ActivitySubCardOrder> findAllByOutTradeNoOrderByPayTimeDesc(String outTradeNo);
}
