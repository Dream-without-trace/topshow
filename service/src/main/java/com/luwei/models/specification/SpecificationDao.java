package com.luwei.models.specification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface SpecificationDao extends JpaRepository<Specification, Integer>, JpaSpecificationExecutor<Specification> {

    List<Specification> findByGoodsId(Integer goodsId);

    @Modifying
    @Query("update Specification set inventory = ?1 where specificationId = ?2")
    Integer updateInventory(Integer inventory, Integer specificationId);
}
