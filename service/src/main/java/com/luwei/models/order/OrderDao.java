package com.luwei.models.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface OrderDao extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @Modifying
    @Query("update Order set deleted = 1 where orderId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);

    List<Order> findOrdersByOutTradeNo(String outTradeNo);

}
