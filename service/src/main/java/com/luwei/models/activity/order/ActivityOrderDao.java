package com.luwei.models.activity.order;

import com.luwei.common.enums.status.ActivityOrderStatus;
import com.luwei.services.activity.cms.ActivityCmsVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-24
 **/
public interface ActivityOrderDao extends JpaRepository<ActivityOrder, Integer>, JpaSpecificationExecutor<ActivityOrder> {


    @Query("select new com.luwei.services.activity.cms.ActivityCmsVO(ao.activityOrderId,ao.activityId,ao.activityTitle,ao.startTime,ao.address) from ActivityOrder ao where userId = ?1 and status not in ?2")
    List<ActivityCmsVO> findByUserId(Integer userId, List<ActivityOrderStatus> statusList);

    List<ActivityOrder> findActivityOrdersByEndTimeLessThanAndStatus(Date date, ActivityOrderStatus status);

    ActivityOrder findActivityOrdersByActivityOrderIdAndUserIdAndDeletedIsFalse(Integer activityOrderId, Integer userId);

    ActivityOrder findFirstByOutTradeNo(String outTradeNo);

    List<ActivityOrder> findAllByActivityIdAndDeletedIsFalse(Integer activityId);

    List<ActivityOrder> findAllByActivityIdAndUserIdAndStatusAndDeletedIsFalse
            (Integer activityId, Integer userId,ActivityOrderStatus status);

    List<ActivityOrder> findAllByUserIdAndAreaIdAndDeletedIsFalse(Integer userId,Integer areaId);
}
