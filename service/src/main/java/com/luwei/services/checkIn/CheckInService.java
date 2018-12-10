package com.luwei.services.checkIn;

import com.alibaba.fastjson.JSONObject;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.enums.type.BillType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.utils.DateTimeUtis;
import com.luwei.models.checkIn.CheckIn;
import com.luwei.models.checkIn.CheckInDao;
import com.luwei.models.integralbill.IntegralBill;
import com.luwei.models.integralbill.IntegralBillDao;
import com.luwei.models.integralset.IntegralSet;
import com.luwei.models.integralset.IntegralSetDao;
import com.luwei.models.user.User;
import com.luwei.models.user.UserDao;
import com.luwei.services.checkIn.web.CheckInVo;
import com.luwei.services.integral.bill.web.IntegralBillDTO;
import com.luwei.services.user.UserService;
import com.luwei.services.user.cms.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Resource
    private IntegralBillDao integralBillDao;
    @Resource
    private IntegralSetDao integralSetDao;
    @Resource
    private UserService userService;

    /**
     *
     * @param userId
     * @return
     */
    public JSONObject saveCheckIn(Integer userId,Integer type) {
        User user = userDao.findById(userId).orElse(null);
        Assert.notNull(user, "用户不存在");
        int zeroTimestamp = DateTimeUtis.getZeroTimestamp();
        List<CheckIn> checkInList = checkInDao.findAllByUserIdAndCheckInTime(userId,zeroTimestamp);
        Assert.isTrue((checkInList == null || checkInList.size() < 1), "您今日已签到！");
        List<CheckIn> checkIns = checkInDao.findAllByUserIdOrderByCheckInTimeDesc(userId);
        JSONObject jsonObject = new JSONObject();
        int checkInDate = 1;
        int checkInIntegral = 0;
        int continuousSevenCheckInIntegral = 0;
        int continuousFifteenCheckInIntegral = 0;
        int continuousThirtyCheckInIntegral = 0;
        List<IntegralSet> IntegralSetAll = integralSetDao.findAll();
        if (IntegralSetAll != null && IntegralSetAll.size()>0) {
            IntegralSet integralSet = IntegralSetAll.get(0);
            if (integralSet != null) {
                if (type == 2) {
                    checkInIntegral = integralSet.getShopCheckInIntegral();
                }else if(type == 1){
                    checkInIntegral = integralSet.getDailyCheckInIntegral();
                }
                continuousSevenCheckInIntegral = integralSet.getContinuousSevenCheckInIntegral();
                continuousFifteenCheckInIntegral = integralSet.getContinuousFifteenCheckInIntegral();
                continuousThirtyCheckInIntegral = integralSet.getContinuousThirtyCheckInIntegral();
            }
        }
        if (checkIns != null && checkIns.size() > 0) {
            CheckIn checkIn = checkIns.get(0);
            if (checkIn != null) {
                checkInDate = checkInDate + checkIn.getCheckInDate();
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        long aa = zeroTimestamp * 1000;
        String dada = sdf.format(new Date(aa));
        if (dada.equals("01") ){
            checkInDate = 1;
        }

        jsonObject.put("checkInDate",checkInDate);
        jsonObject.put("checkInIntegral",checkInIntegral);
        if (checkInDate == 7) {
            checkInIntegral = checkInIntegral + continuousSevenCheckInIntegral;
            jsonObject.put("additional",continuousSevenCheckInIntegral);
        }else if (checkInDate == 15){
            checkInIntegral = checkInIntegral + continuousFifteenCheckInIntegral;
            jsonObject.put("additional",continuousFifteenCheckInIntegral);
        }else if (checkInDate == 30) {
            checkInIntegral = checkInIntegral + continuousThirtyCheckInIntegral;
            jsonObject.put("additional",continuousThirtyCheckInIntegral);
        }
        CheckIn checkIn = new CheckIn(userId,checkInIntegral,checkInDate,zeroTimestamp);
        checkInDao.save(checkIn);
        userService.addIntegral(user.getUserId(), checkInIntegral);
        // 保存积分流水
        IntegralBillDTO integralBillDTO = new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(),
                checkInIntegral, user.getIntegral() + checkInIntegral, BillType.INCOME,
                "到店签到");
        IntegralBill bill = new IntegralBill();
        BeanUtils.copyProperties(integralBillDTO, bill);
        integralBillDao.save(bill);
        return jsonObject;
    }


    /**
     *
     * @param userId
     * @return
     */
    public CheckInVo getCheckInVo(Integer userId) {
        User user = userDao.findById(userId).orElse(null);
        Assert.notNull(user, "用户不存在");
        int zeroTimestamp = DateTimeUtis.getZeroTimestamp();
        int isCheckIn = 2;//是否签到 ：1：已签到，2：未签到
        List<CheckIn> checkInList = checkInDao.findAllByUserIdAndCheckInTime(userId,zeroTimestamp);
        if (checkInList != null && checkInList.size()>0) {
            isCheckIn = 1;
        }
        int timesMonthmorning = DateTimeUtis.getTimesMonthmorning();
        int timesMonthnight = DateTimeUtis.getTimesMonthnight();
        List<CheckIn> checkIns = checkInDao.findAllByUserIdAndCheckInTimeAfterAndCheckInTimeBeforeOrderByCheckInTimeDesc(userId,
                timesMonthmorning,timesMonthnight);
        int checkInDate = 0;

        List<String> checkInTimes = new ArrayList<>();
        if (checkIns != null && checkIns.size() > 0) {
            for (CheckIn checkIn:checkIns) {
                if (checkIn == null) {
                    continue;
                }
                Integer checkInTime = checkIn.getCheckInTime();
                if (checkInTime == null || checkInTime == 0) {
                    continue;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                String format = sdf.format(new Date(Long.valueOf(checkInTime + "000")));
                checkInTimes.add(format);
            }
            CheckIn checkIn = checkIns.get(0);
            if (checkIn != null) {
                checkInDate = checkIn.getCheckInDate();
            }
        }
        Integer checkInTotalIntegral = checkInDao.findTotalIntegralByUserId(userId);
        return new CheckInVo(checkInDate,checkInTotalIntegral,checkInTimes,isCheckIn);
    }

    /**
     * 根据手机查询用户信息
     * @param phone
     * @return
     */
    public UserInfoVo getUserInfoVoByPhone(String phone) {
        List<User> userList = userDao.findAllByPhoneAndDisable(phone, FlagType.DENY);
        Assert.isTrue((userList != null && userList.size() < 1), "用户不存在");
        User user = userList.get(0);
        Assert.notNull(user, "用户不存在");
        Integer userId = user.getUserId();
        Assert.isTrue((userId != null && userId != 0), "用户不存在");
        int zeroTimestamp = DateTimeUtis.getZeroTimestamp();
        int isCheckIn = 2;//是否签到 ：1：已签到，2：未签到
        List<CheckIn> checkInList = checkInDao.findAllByUserIdAndCheckInTime(userId,zeroTimestamp);
        if (checkInList != null && checkInList.size()>0) {
            isCheckIn = 1;
        }
        int timesMonthmorning = DateTimeUtis.getTimesMonthmorning();
        int timesMonthnight = DateTimeUtis.getTimesMonthnight();
        List<CheckIn> checkIns = checkInDao.findAllByUserIdAndCheckInTimeAfterAndCheckInTimeBeforeOrderByCheckInTimeDesc(userId,
                timesMonthmorning,timesMonthnight);
        int checkInDate = 0;

        List<String> checkInTimes = new ArrayList<>();
        if (checkIns != null && checkIns.size() > 0) {
            for (CheckIn checkIn:checkIns) {
                if (checkIn == null) {
                    continue;
                }
                Integer checkInTime = checkIn.getCheckInTime();
                if (checkInTime == null || checkInTime == 0) {
                    continue;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                String format = sdf.format(new Date(Long.valueOf(checkInTime + "000")));
                checkInTimes.add(format);
            }
            CheckIn checkIn = checkIns.get(0);
            if (checkIn != null) {
                checkInDate = checkIn.getCheckInDate();
            }
        }
        int checkInTotalIntegral = checkInDao.findTotalIntegralByUserId(userId);
        return new UserInfoVo(user.getUserId(),user.getUsername(),user.getNickname(),user.getAvatarUrl(),
                user.getPhone(),user.getIntegral(),user.getSex(),user.getAge(),user.getCity(),user.getProvince(),
                isCheckIn,checkInDate,checkInTotalIntegral,checkInTimes);
    }


}
