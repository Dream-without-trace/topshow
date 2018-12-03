package com.luwei.models.refund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-20
 **/
public interface RefundDao extends JpaRepository<Refund, Integer>, JpaSpecificationExecutor<Refund> {

    @Modifying
    @Query("update Refund set deleted = 1 where refundId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);

    Refund findFirstByRefundIdAndStoreId(Integer refundId, Integer storeId);

    Refund findFirstByOrderIdAndGoodsId(Integer orderId, Integer goodsId);

}
