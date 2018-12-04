package com.luwei.services.checkIn;

import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.models.checkIn.CheckIn;
import com.luwei.models.checkIn.CheckInDao;
import com.luwei.models.user.User;
import com.luwei.models.user.UserDao;
import com.luwei.services.checkIn.web.CheckInVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: topshow
 * @description: 签到service
 * @author: ZhangHongJie
 * @create: 2018-12-04 14:40
 **/
@Slf4j
@Service
@Transactional
public class CheckInService {

    @Resource
    private CheckInDao checkInDao;
    @Resource
    private UserDao userDao;


    /**
     *
     * @param userId
     * @return
     */
    public CheckInVo saveCheckIn(Integer userId) {
        User user = userDao.findById(userId).orElse(null);
        Assert.notNull(user, "用户不存在");
        List<CheckIn> checkIns = checkInDao.findAllByUserIdOrderByCheckInTimeDesc(userId);
        Integer checkInDate = 1;
        Integer checkInTotalIntegral = null;
        int isCheckIn = 1;//是否签到 ：1：已签到，2：未签到
        List<Date> checkInTimes = new ArrayList<>();
        if (checkIns != null && checkIns.size() > 0) {
            CheckIn checkIn = checkIns.get(0);
            if (checkIn != null) {
                Date checkInTime = checkIn.getCheckInTime();
                if (checkInTime != null) {
                    long time = checkInTime.getTime();
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    long endTimestamps= cal.getTimeInMillis();
                    long startTimestamps= endTimestamps-1000*60*60*24;
                    if(endTimestamps < time){
                        throw new IllegalArgumentException("您今日已签到！");
                    }
                    if (startTimestamps < time && time < endTimestamps) {
                        checkInDate += checkIn.getCheckInDate();
                    }
                }
            }
            for (CheckIn checkIn1:checkIns) {
                if (checkIn1 != null) {
                    checkInTotalIntegral += checkIn1.getGiveIntegral();
                    checkInTimes.add(checkIn1.getCheckInTime());
                }
            }
        }
        CheckIn checkIn = new CheckIn(userId,100,checkInDate,new Date());
        checkInDao.save(checkIn);
        return new CheckInVo(checkInDate,checkInTotalIntegral,checkInTimes,isCheckIn);
    }


    /**
     *
     * @param userId
     * @return
     */
    public CheckInVo getCheckInVo(Integer userId) {
        User user = userDao.findById(userId).orElse(null);
        Assert.notNull(user, "用户不存在");
        List<CheckIn> checkIns = checkInDao.findAllByUserIdOrderByCheckInTimeDesc(userId);
        int checkInDate = 0;
        int checkInTotalIntegral = 0;
        int isCheckIn = 1;//是否签到 ：1：已签到，2：未签到
        List<Date> checkInTimes = new ArrayList<>();
        if (checkIns != null && checkIns.size() > 0) {
            CheckIn checkIn = checkIns.get(0);
            if (checkIn != null) {
                Date checkInTime = checkIn.getCheckInTime();
                if (checkInTime != null) {
                    long time = checkInTime.getTime();
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    long endTimestamps= cal.getTimeInMillis();
                    long startTimestamps= endTimestamps-1000*60*60*24;
                    if(endTimestamps > time){
                        isCheckIn = 2;
                    }
                    if (startTimestamps < time && time < endTimestamps) {
                        checkInDate += checkIn.getCheckInDate();
                    }
                }
            }
            for (CheckIn checkIn1:checkIns) {
                if (checkIn1 != null) {
                    checkInTotalIntegral += checkIn1.getGiveIntegral();
                    checkInTimes.add(checkIn1.getCheckInTime());
                }
            }
        }
        return new CheckInVo(checkInDate,checkInTotalIntegral,checkInTimes,isCheckIn);
    }
}
