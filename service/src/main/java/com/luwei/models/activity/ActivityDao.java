package com.luwei.models.activity;

import com.luwei.common.enums.status.ActivityStatus;
import com.luwei.common.enums.type.ActivityType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.services.banner.cms.GoodsActivityVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author Leone
 * @since 2018-08-09
 **/
public interface ActivityDao extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {

    @Modifying
    @Query("update Activity set deleted = 1 where activityId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);

    List<Activity> findActivitiesByCityAndActivityCategoryId(String city, Integer activityCategoryId);

    List<Activity> findActivitiesByAreaIdAndRecommendAndStatusAndActivityCategoryIdAndDeletedIsFalse(Integer areaId, FlagType flagType, ActivityStatus status, Integer activityCategoryId);

    List<Activity> findActivitiesByAreaIdAndStatusOrderByCreateTimeDesc(Integer areaId, ActivityStatus status);

    List<Activity> findActivitiesByAreaIdAndStatusAndActivityTypeAndActivityCategoryIdAndDeletedIsFalse(Integer areaId, ActivityStatus status, ActivityType activityType, Integer activityCategoryId);

    List<Activity> findActivitiesByActivitySeriesIdAndAreaIdAndStatus(Integer seriesId, Integer areaId, ActivityStatus status);

    @Query("select areaId from Activity group by areaId")
    List<Integer> findAreaIds();

    Page<Activity> findActivitiesByAreaIdAndRecommendAndStatus(Integer areaId, FlagType flagType, ActivityStatus activityStatus, Pageable pageable);

    Page<Activity> findActivitiesByAreaIdAndActivityCategoryIdAndStatus(Integer areaId, Integer categoryId, ActivityStatus status, Pageable pageable);

    @Query("select new com.luwei.services.banner.cms.GoodsActivityVO(a.activityId,a.title,2) from Activity a where a.deleted = 0 and a.title like ?1")
    List<GoodsActivityVO> findAllActivity(String name);

    Page<Activity> findActivitiesByAreaIdAndDeletedIsFalseAndStatusOrderByCreateTimeAsc(Integer areaId, ActivityStatus status, Pageable pageable);

    Page<Activity> findActivitiesByAreaIdAndDeletedIsFalseAndStatusAndActivityType(Integer areaId, ActivityStatus status,ActivityType activityType, Pageable pageable);

    @Query("select count(a.activityId) from Activity a where a.activitySeriesId = ?1")
    Integer countByActivitySeriesId(Integer seriesId);

    List<Activity> findActivityByStartTimeLessThan(Date date);

    List<Activity> findActivityByStartTimeLessThanAndEndTimeGreaterThan(Date start, Date end);

    List<Activity> findActivityByEndTimeLessThan(Date date);

    @Query("select activityId from Activity where areaId = ?1")
    List<Integer> findByAreaId(Integer areaId);

}