package com.luwei.models.shopping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.stream.DoubleStream;

/**
 * @author Leone
 * @since 2018-08-28
 **/
public interface ShoppingDao extends JpaRepository<Shopping, Integer>, JpaSpecificationExecutor<Shopping> {


    @Modifying
    @Query("delete from Shopping where shoppingId in ?1 and deleted <> 1 and userId = ?2")
    Integer delByIds(Set<Integer> ids, Integer userId);


    Page<Shopping> findByUserId(Integer userId, Pageable pageable);

    Shopping findFirstByUserIdAndSpecificationId(Integer userId, Integer specificationId);
}
