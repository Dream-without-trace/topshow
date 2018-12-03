package com.luwei.models.goods.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface GoodsCategoryDao extends JpaRepository<GoodsCategory, Integer>, JpaSpecificationExecutor<GoodsCategory> {

    @Modifying
    @Query("update GoodsCategory set deleted = 1 where categoryId in ?1")
    Integer delCategoryByIds(List<Integer> ids);

    @Query("select categoryId from GoodsCategory")
    List<Integer> findAllId();

}
