package com.luwei.models.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: topshow
 * @description: 门店dao
 * @author: ZhangHongJie
 * @create: 2018-12-03 15:22
 **/
public interface ShopDao extends JpaRepository<Shop, Integer>, JpaSpecificationExecutor<Shop> {

    List<Shop> findShopsByTitleAndAreaId(String title,Integer areaId);

    List<Shop> findAllByAreaIdAndDeletedFalse(Integer areaId);

    @Modifying
    @Query("update Shop set deleted = 1 where shopId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);
}
