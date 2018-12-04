package com.luwei.models.checkIn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: topshow
 * @description: 签到Dao
 * @author: ZhangHongJie
 * @create: 2018-12-04 14:54
 **/
public interface CheckInDao extends JpaRepository<CheckIn, Integer>, JpaSpecificationExecutor<CheckIn> {
    List<CheckIn> findAllByUserIdOrderByCheckInTimeDesc(Integer userId);
}
