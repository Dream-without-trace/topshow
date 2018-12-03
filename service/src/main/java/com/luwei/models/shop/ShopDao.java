package com.luwei.models.shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: topshow
 * @description: 门店dao
 * @author: ZhangHongJie
 * @create: 2018-12-03 15:22
 **/
public interface ShopDao extends JpaRepository<Shop, Integer>, JpaSpecificationExecutor<Shop> {

    List<Shop> findShopsByTitleAndAreaId(String title,Integer areaId);
}
