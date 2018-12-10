package com.luwei.models.checkIn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: topshow
 * @description: 签到Dao
 * @author: ZhangHongJie
 * @create: 2018-12-04 14:54
 **/
public interface CheckInDao extends JpaRepository<CheckIn, Integer>, JpaSpecificationExecutor<CheckIn> {


    List<CheckIn> findAllByUserIdAndCheckInTimeAfterAndCheckInTimeBeforeOrderByCheckInTimeDesc(Integer userId,Integer timesMonthmorning,
                                                                            Integer timesMonthnight);

    List<CheckIn> findAllByUserIdAndCheckInTime(Integer userId, int zeroTimestamp);

    @Query("select sum (giveIntegral) from CheckIn where userId = ?1")
    Integer findTotalIntegralByUserId(Integer userId);

    List<CheckIn> findAllByUserIdOrderByCheckInTimeDesc(Integer userId);
}
