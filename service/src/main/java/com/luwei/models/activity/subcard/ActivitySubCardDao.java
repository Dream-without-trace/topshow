package com.luwei.models.activity.subcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: topshow
 * @description: 活动次数Dao
 * @author: ZhangHongJie
 * @create: 2018-12-08 23:19
 **/
public interface ActivitySubCardDao extends JpaRepository<ActivitySubCard, Integer>, JpaSpecificationExecutor<ActivitySubCard> {

    List<ActivitySubCard> findAllByActivityId(Integer activityId);
}
