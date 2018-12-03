package com.luwei.models.order.detail;

import com.luwei.services.order.cms.OrderGoodsDetailVO;
import com.luwei.services.order.detail.web.OrderGoodsWebVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer>, JpaSpecificationExecutor<OrderDetail> {

    List<OrderDetail> findByOrderIdAndDeletedIsFalse(Integer orderId);

    @Query("select new com.luwei.services.order.cms.OrderGoodsDetailVO(od.goodsId,od.picture,od.count,od.price,od.specificationName,od.specificationId) from OrderDetail od where orderId = ?1")
    List<OrderGoodsDetailVO> findOrderDetailByOrderId(Integer orderId);

    List<OrderDetail> findOrderDetailsByOrderId(Integer orderId);

    @Query("select new com.luwei.services.order.detail.web.OrderGoodsWebVO(od.orderDetailId,od.goodsId,od.name,od.price,od.count,od.specificationId,od.picture,od.specificationName,od.flagType) from OrderDetail od where orderId = ?1")
    List<OrderGoodsWebVO> findOrderGoods(Integer orderId);

    @Modifying
    @Query("delete from OrderDetail where orderId = ?1")
    Integer deleteByOrderId(Integer orderId);

    OrderDetail findFirstByOrderIdAndGoodsId(Integer orderId, Integer goodsId);

    @Modifying
    @Query("update OrderDetail set flagType = com.luwei.common.enums.type.FlagType.RIGHT where goodsId = ?2 and orderId = ?1")
    Integer updateFlagType(Integer id, Integer tripartiteId);
}
