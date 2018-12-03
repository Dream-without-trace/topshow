package com.luwei.models.activity.series;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Leone
 * @since 2018-08-09
 **/
public interface ActivitySeriesDao extends JpaRepository<ActivitySeries, Integer>, JpaSpecificationExecutor<ActivitySeries> {


    @Modifying
    @Query("update ActivitySeries set deleted = 1 where activitySeriesId in ?1")
    Integer delByIds(List<Integer> ids);


}
