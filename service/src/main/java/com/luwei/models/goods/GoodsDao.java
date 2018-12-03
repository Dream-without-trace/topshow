package com.luwei.models.goods;

import com.luwei.common.enums.status.SalesStatus;
import com.luwei.common.enums.type.GoodsType;
import com.luwei.services.banner.cms.GoodsActivityVO;
import com.luwei.services.goods.web.StoreGoodsListVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-03
 **/
public interface GoodsDao extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {

    @Modifying
    @Query("update Goods set status = ?2 where goodsId = ?1 and deleted = ?3")
    Integer updateGoodsUpDownStatus(Integer goodsId, SalesStatus status, Boolean deleted);


    @Modifying
    @Query("update Goods set deleted = 1 where goodsId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);


    @Modifying
    @Query("update Goods set goodsType = ?1 where goodsId = ?2")
    Integer updateGoodsType(GoodsType goodsType, Integer goodsId);


    @Query("select new com.luwei.services.goods.web.StoreGoodsListVO(g.goodsId,g.name,g.picture,g.price) from Goods g where storeId = ?1 and deleted = 0 and status = 0")
    List<StoreGoodsListVO> findByStoreId(Integer storeId);

    @Query("select categoryId from Goods where goodsId in ?1")
    List<Integer> findCategoryIdByGoodsId(List<Integer> goodsId);

    @Query("select new com.luwei.services.banner.cms.GoodsActivityVO(g.goodsId,g.name,1) from Goods g where g.deleted = 0 and g.name like ?1")
    List<GoodsActivityVO> findAllGoods(String name);

    @Query("select g.goodsId from Goods g where status = ?1")
    List<Integer> findSoldOutGoods(SalesStatus salesStatus);
}
