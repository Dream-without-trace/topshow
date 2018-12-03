package com.luwei.models.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public interface StoreDao extends JpaRepository<Store, Integer>, JpaSpecificationExecutor<Store> {

    @Modifying
    @Query("update Store set deleted = 1 where storeId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);

    Store findFirstByCustomerId(String customerId);
}
