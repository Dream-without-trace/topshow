package com.luwei.models.membershipcard.order;

import com.luwei.models.activity.order.ActivityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: topshow
 * @description: 会员卡订单dao接口
 * @author: ZhangHongJie
 * @create: 2018-12-03 19:47
 **/
public interface MembershipCardOrderDao extends JpaRepository<MembershipCardOrder, Integer>, JpaSpecificationExecutor<MembershipCardOrder> {

    List<MembershipCardOrder> findMembershipCardOrdersByUserId(Integer userId);

    List<MembershipCardOrder> findMembershipCardOrdersByOutTradeNo(String outTradeNo);

    MembershipCardOrder findMembershipCardOrdersByMembershipCardOrderId(Integer id);
}
