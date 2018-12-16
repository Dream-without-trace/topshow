package com.luwei.models.membershipcard.order;

import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.models.activity.order.ActivityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: topshow
 * @description: 会员卡订单dao接口
 * @author: ZhangHongJie
 * @create: 2018-12-03 19:47
 **/
public interface MembershipCardOrderDao extends JpaRepository<MembershipCardOrder, Integer>, JpaSpecificationExecutor<MembershipCardOrder> {

    List<MembershipCardOrder> findMembershipCardOrdersByUserIdAndDeletedIsFalse(Integer userId);

    List<MembershipCardOrder> findMembershipCardOrdersByOutTradeNoAndStatusAndDeletedFalse(String outTradeNo,MembershipCardOrderStatus status);

    MembershipCardOrder findMembershipCardOrdersByMembershipCardOrderIdAndDeletedFalse(Integer id);

    List<MembershipCardOrder> findAllByUserIdAndDeletedIsFalse(Integer userId);

    List<MembershipCardOrder> findAllByUserIdAndStatusAndDeletedIsFalseOrderByPayTimeDesc(Integer userId, MembershipCardOrderStatus status);

    //@Modifying
    //@Query("update MembershipCardOrder set deleted = 1 where userId = ?1 and status = 0")
    void deleteAllByUserIdAndStatus(Integer userId, MembershipCardOrderStatus create);

    List<MembershipCardOrder> findAllByUserIdAndTitleAndDeletedFalse(Integer userId, String title1);

    List<MembershipCardOrder> findAllByUserIdAndStatusAndAreaIdAndDeletedIsFalseOrderByPayTimeDesc(Integer userId, MembershipCardOrderStatus status, Integer areaId);
}
