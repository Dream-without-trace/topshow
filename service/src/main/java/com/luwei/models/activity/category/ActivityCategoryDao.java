package com.luwei.models.activity.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-09
 **/
public interface ActivityCategoryDao extends JpaRepository<ActivityCategory, Integer>, JpaSpecificationExecutor<ActivityCategory> {


    @Modifying
    @Query("update ActivityCategory set deleted = 1 where activityCategoryId in ?1")
    Integer delByIds(List<Integer> ids);

    List<ActivityCategory> findActivityCategoriesByDeletedIsFalse();

}
