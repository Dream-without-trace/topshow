package com.luwei.services.user;

import com.alibaba.fastjson.JSONObject;
import com.luwei.common.Response;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.enums.type.BillType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.activity.order.ActivityOrder;
import com.luwei.models.activity.order.ActivityOrderDao;
import com.luwei.models.courseEnrolment.CourseEnrolment;
import com.luwei.models.courseEnrolment.CourseEnrolmentDao;
import com.luwei.models.integralbill.IntegralBill;
import com.luwei.models.integralbill.IntegralBillDao;
import com.luwei.models.integralset.IntegralSet;
import com.luwei.models.integralset.IntegralSetDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.user.User;
import com.luwei.models.user.UserDao;
import com.luwei.models.user.receiving.ReceivingAddress;
import com.luwei.module.alisms.AliSmsService;
import com.luwei.module.qiniu.FileVO;
import com.luwei.module.qiniu.QiNiuService;
import com.luwei.services.activity.order.ActivityOrderService;
import com.luwei.services.area.AreaService;
import com.luwei.services.integral.bill.IntegralBillService;
import com.luwei.services.integral.bill.web.IntegralBillDTO;
import com.luwei.services.membershipCard.order.MembershipCardOrderService;
import com.luwei.services.order.OrderService;
import com.luwei.services.user.address.UserReceivingAddressService;
import com.luwei.services.user.address.web.UserAddressVO;
import com.luwei.services.user.address.web.UserReceivingAddDTO;
import com.luwei.services.user.cms.UserEditDTO;
import com.luwei.services.user.cms.UserPageVO;
import com.luwei.services.user.web.UserInfoAddDTO;
import com.luwei.services.user.web.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@Slf4j
@Service
@Transactional
public class UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private OrderService orderService;

    @Resource
    private IntegralBillService integralBillService;

    @Resource
    private AreaService areaService;

    @Resource
    private AliSmsService aliSmsService;

    @Resource
    private UserReceivingAddressService receivingAddressService;

    @Resource
    private ActivityOrderService activityOrderService;

    @Resource
    private UserReceivingAddressService userReceivingAddressService;
    @Resource
    private MembershipCardOrderService membershipCardOrderService;
    @Resource
    private ActivityOrderDao activityOrderDao;
    @Resource
    private CourseEnrolmentDao courseEnrolmentDao;
    @Resource
    private IntegralSetDao integralSetDao;
    @Resource
    private QiNiuService qiNiuService;
    @Resource
    private IntegralBillDao integralBillDao;


    @Value("${app.constant.topshow.first-integral}")
    private Integer firstIntegral;

    // -----------------------------cms-----------------------------------

    /**
     * 分页
     *
     * @param pageable
     * @param nickname
     * @param phone
     * @return
     */
//    @Cacheable(value = "user.page")
    public Page<UserPageVO> page(Pageable pageable, String nickname, String phone) {
        Specification<User> specification = (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));

            if (!StringUtils.isEmpty(nickname)) {
                list.add(criteriaBuilder.like(root.get("nickname").as(String.class), "%" + nickname + "%"));
            }

            if (!StringUtils.isEmpty(phone)) {
                list.add(criteriaBuilder.like(root.get("phone").as(String.class), "%" + phone + "%"));
            }

            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("userId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return userDao.findAll(specification, pageable).map(this::toUserPageVO);
    }


    /**
     * 转换
     *
     * @param user
     * @return
     */
    private UserPageVO toUserPageVO(User user) {
        UserPageVO vo = new UserPageVO();
        BeanUtils.copyProperties(user, vo);
        vo.setArea(user.getProvince() + " " + user.getCity());
        vo.setActivity(activityOrderService.findByUserId(user.getUserId()));
        ReceivingAddress receivingAddress = receivingAddressService.findByUserIdNew(user.getUserId());
        if (!ObjectUtils.isEmpty(receivingAddress)) {
            vo.setAddress(receivingAddress.getProvince() + " " + receivingAddress.getCity() + " " + receivingAddress.getRegion() + " " + receivingAddress.getAddress());
        } else {
            vo.setAddress("");
        }
        return vo;
    }

    /**
     * 添加用户
     *
     * @param dto
     */
//    @CacheEvict(value = "user.page")
    public User save(User dto) {
        return userDao.save(dto);
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    /**
     * 修改用户
     *
     * @param dto
     * @return
     */
    public UserPageVO update(UserEditDTO dto) {
        User user = this.findOne(dto.getUserId());
        BeanUtils.copyProperties(dto, user);
        User entity = userDao.save(user);
        return this.toUserPageVO(entity);
    }

    /**
     * 根据id查找userPageVO
     *
     * @param userId
     * @return
     */
    public UserPageVO one(Integer userId) {
        User user = this.findOne(userId);
        return toUserPageVO(user);
    }

    /**
     * 根据id查找用户
     *
     * @param userId
     * @return
     */
    public User findOne(Integer userId) {
        User user = userDao.findById(userId).orElse(null);
        Assert.notNull(user, "用户不存在");
        return user;
    }

    /**
     * 批量删除用户
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        Integer result = userDao.delByIds(new ArrayList<>(ids));
        if (result < 1) {
            throw new ValidateException(ExceptionMessage.DELETE_FAIL);
        }
    }

    /**
     * 根据openid查找用户
     *
     * @param openid
     * @return
     */
    public User findByOpenid(String openid) {
        User user = userDao.findFirstByOpenid(openid);
        Assert.notNull(user, "用户不存在");
        return user;
    }

    /**
     * 根据openid查找用户
     *
     * @param openid
     * @return
     */
    public User findByOpenidNew(String openid) {
        return userDao.findFirstByOpenid(openid);
    }


    /**
     * 修改用户状态
     *
     * @param userId
     * @return
     */
    public UserPageVO updateStatus(Integer userId) {
        User user = this.findOne(userId);
        if (FlagType.RIGHT.equals(user.getDisable())) {
            user.setDisable(FlagType.DENY);
        } else {
            user.setDisable(FlagType.RIGHT);
        }
        User entity = userDao.save(user);
        return this.toUserPageVO(entity);
    }


    // -----------------------------web-----------------------------------

    /**
     * 小程序绑定手机号
     *
     * @param phone
     * @param userId
     * @param captcha
     * @return
     */
    public Boolean binding(String phone, Integer userId, String captcha,String referrerPhone) {
        User user = this.findOne(userId);
        boolean result = aliSmsService.matchingCaptcha(phone, captcha);
        // 判断用户验证码是否正确
        if (result) {
            user.setPhone(phone);
            int inviteGiftIntegral = 0 ;
            int bindPhoneIntegral = 0 ;
            List<IntegralSet> integralSets = integralSetDao.findAll();
            if (integralSets != null && integralSets.size()>0) {
                IntegralSet integralSet = integralSets.get(0);
                if (integralSet != null) {
                    bindPhoneIntegral = integralSet.getBindPhoneIntegral();
                    inviteGiftIntegral = integralSet.getInviteGiftIntegral();
                }
            }
            if (referrerPhone != null && !"".equals(referrerPhone)) {
                List<User> userList = this.findAllByPhone(referrerPhone);
                if (userList != null && userList.size()>0) {
                    for (User user1:userList) {
                        if (user1 != null) {
                            if (inviteGiftIntegral != 0) {
                                user1.setIntegral(user1.getIntegral() + inviteGiftIntegral);
                                // 保存用户积分流水
                                integralBillService.save(new IntegralBillDTO(user1.getUserId(), user1.getNickname(),
                                        user1.getPhone(), inviteGiftIntegral,
                                        user1.getIntegral(), BillType.INCOME, "邀请用户"));
                                this.save(user1);
                            }
                            user.setRecommenderUserId(user1.getUserId());
                        }
                    }
                }
            }
            user.setIntegral(user.getIntegral() + bindPhoneIntegral);
            user.setFirst(false);
            // 保存用户积分流水
            integralBillService.save(new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(), bindPhoneIntegral,
                    user.getIntegral(), BillType.INCOME, "用户绑定手机号"));
            userDao.save(user);
            return true;
        } else {
            throw new ValidateException(ExceptionMessage.CAPTCHA_FAIL);
        }
    }


    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    public UserInfoVO userInfo(Integer userId) {
        User user = this.findOne(userId);
        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    /**
     * 添加用户详细信息（若果是首次添加个人信息）
     *
     * @param dto
     * @return
     */
    public void addUserInfo(UserInfoAddDTO dto) {
        User user = this.findOne(dto.getUserId());
        if (!user.getFirst()) {
            user.setFirst(true);
            // 如果用户信息不完整或首次完善用户信息这奖励对应的积分
            user.setIntegral(user.getIntegral() + 10);
            // 保存对应的积分
            integralBillService.save(new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(), 10, user.getIntegral(), BillType.INCOME, "用户绑定手机号"));
        }
        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setAge(dto.getAge());
        user.setSex(dto.getSex());
        user.setBabySex(dto.getBabySex());
        user.setBabyName(dto.getBabyName());
        user.setMicroBlog(dto.getMicroBlog());
        userDao.save(user);
    }


    /**
     * 获取用户积分总和
     *
     * @param userId
     * @return
     */
    public JSONObject findIntegralTotal(Integer userId) {
        User user = this.findOne(userId);
        List<MembershipCardOrder> membershipCardOrderList = membershipCardOrderService.findAllByUserIdAndStatus(userId, MembershipCardOrderStatus.PAY);
        Integer isMember = 2;
        if (membershipCardOrderList != null && membershipCardOrderList.size()>0) {
            for (MembershipCardOrder membershipCardOrder:membershipCardOrderList) {
                if (membershipCardOrder != null) {
                    String title = membershipCardOrder.getTitle();
                    if (title == null || title.equals("")) {
                        continue;
                    }
                    if (title.equals("体验会员")) {
                        List<CourseEnrolment> courseEnrolments = courseEnrolmentDao.findAllByUserId(userId);
                        List<ActivityOrder> activityOrderList = activityOrderDao.findAllByUserIdAndAreaIdAndDeletedIsFalse(userId,membershipCardOrder.getAreaId());
                        if ((courseEnrolments == null || courseEnrolments.size()<1)&&(activityOrderList == null || activityOrderList.size()<1)) {
                            isMember = 1;
                        }
                    }else{
                        Integer effective = membershipCardOrder.getEffective();
                        long time = membershipCardOrder.getPayTime().getTime()+effective*24*3600*1000;
                        long l = System.currentTimeMillis();
                        if (time>l) {
                            isMember = 1;
                        }
                    }
                }
            }
        }
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("integral",user.getIntegral());
        jsonObject.put("isMember",isMember);
        return jsonObject;
    }

    /**
     * 用户添加收货地址或修改收货地址
     *
     * @param dto
     */
    public UserAddressVO addReceivingAddress(UserReceivingAddDTO dto) {
        User user = findOne(dto.getUserId());
        ReceivingAddress receivingAddress = receivingAddressService.findReceivingAddressByUserId(dto.getUserId());
        if (!ObjectUtils.isEmpty(receivingAddress)) {
            BeanUtils.copyProperties(dto, receivingAddress);
            receivingAddress.setAddress(dto.getFullAddress());
            receivingAddress.setProvince(dto.getAddress().get(0));
            receivingAddress.setCity(dto.getAddress().get(1));
            receivingAddress.setRegion(dto.getAddress().get(2));
            userReceivingAddressService.save(receivingAddress);
        } else {
            receivingAddress = new ReceivingAddress();
            BeanUtils.copyProperties(dto, receivingAddress);
            receivingAddress.setAddress(dto.getFullAddress());
            receivingAddress.setProvince(dto.getAddress().get(0));
            receivingAddress.setCity(dto.getAddress().get(1));
            receivingAddress.setRegion(dto.getAddress().get(2));
            userReceivingAddressService.save(receivingAddress);
        }
        UserAddressVO vo = new UserAddressVO();
        BeanUtils.copyProperties(receivingAddress, vo);
        vo.setFullAddress(receivingAddress.getAddress());
        vo.setAddress(Arrays.asList(receivingAddress.getProvince(), receivingAddress.getCity(), receivingAddress.getRegion()));
        return vo;
    }


    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    public UserAddressVO userAddress(Integer userId) {
        UserAddressVO vo = new UserAddressVO();
        ReceivingAddress address = userReceivingAddressService.findByUserId(userId);
        BeanUtils.copyProperties(address, vo);
        vo.setFullAddress(address.getAddress());
        vo.setAddress(Arrays.asList(address.getProvince(), address.getCity(), address.getRegion()));
        return vo;
    }

    /**
     * 给用户添加积分
     *
     * @param userId
     * @param count
     */
    public void addIntegral(Integer userId, Integer count) {
        User user = this.findOne(userId);
        user.setIntegral(user.getIntegral() + count);
        userDao.save(user);
    }


    public Response memberCentre(Integer userId) {
        User user = this.findOne(userId);
        List<MembershipCardOrder> membershipCardOrderList = membershipCardOrderService.findAllByUserIdAndStatus(userId,MembershipCardOrderStatus.PAY);
        Assert.isTrue((membershipCardOrderList != null && membershipCardOrderList.size()>0),"您未办理会员卡！");
        MembershipCardOrder membershipCardOrder = membershipCardOrderList.get(0);
        Assert.notNull(membershipCardOrder,"您未办理会员卡！");
        String title = membershipCardOrder.getTitle();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title",title);
        jsonObject.put("picture",membershipCardOrder.getPicture());
        jsonObject.put("detail",membershipCardOrder.getDetail());
        jsonObject.put("memberBenefits",membershipCardOrder.getMemberBenefits());
        jsonObject.put("username",user.getUsername());
        String dateExpire = "使用一次后到期";
        if (title.equals("体验会员")) {
            List<CourseEnrolment> courseEnrolments = courseEnrolmentDao.findAllByUserId(userId);
            Assert.isTrue((courseEnrolments == null || courseEnrolments.size()<1),"您未办理会员卡！");
        }else{
            Integer effective = membershipCardOrder.getEffective();
            long time = membershipCardOrder.getPayTime().getTime()+effective*24*3600*1000;
            long l = System.currentTimeMillis();
            Assert.isTrue((l<time),"您未办理会员卡！");
            Date ta = new Date(time);
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            dateExpire = format.format(ta);
        }
        jsonObject.put("dateExpire",dateExpire);
     return Response.build(20000, "success", jsonObject);
    }



    public List<User> findAllByPhone(String referrerPhone) {
        return userDao.findAllByPhoneAndDisable(referrerPhone,FlagType.DENY);
    }

    public JSONObject shareUserDetails(Integer userId) {
        User user = this.findOne(userId);
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("nickname",user.getNickname());
        String picture = "";
        FileVO fileVO = qiNiuService.uploadStreamByUrl(user.getAvatarUrl());
        if (fileVO != null) {
            List<String> urls = fileVO.getUrls();
            if (urls != null && urls.size()>0) {
                picture = urls.get(0);
            }
        }
        jsonObject.put("avatarUrl",picture);
        String shareDescription = "";
        List<IntegralSet> integralSets = integralSetDao.findAll();
        if (integralSets != null && integralSets.size()>0) {
            IntegralSet integralSet = integralSets.get(0);
            if (integralSet != null) {
                shareDescription = integralSet.getShareDescription();
            }
        }
        jsonObject.put("shareDescription",shareDescription);
        return jsonObject;
    }

    public Response taskFinish(Integer userId) {
        User user = this.findOne(userId);
        List<IntegralSet> IntegralSetAll = integralSetDao.findAll();
        Assert.isTrue(IntegralSetAll != null && IntegralSetAll.size()>0,"积分设置为空！");
        IntegralSet integralSet = IntegralSetAll.get(0);
        Assert.isTrue(integralSet != null,"积分设置为空！");
        Integer taskFinishIntegral = integralSet.getTaskFinishIntegral();
        Assert.isTrue(taskFinishIntegral != null,"积分设置为空！");
        this.addIntegral(user.getUserId(), taskFinishIntegral);
        // 保存积分流水
        IntegralBillDTO integralBillDTO = new IntegralBillDTO(user.getUserId(), user.getNickname(), user.getPhone(),
                taskFinishIntegral, user.getIntegral() + taskFinishIntegral, BillType.INCOME,
                "完成任务");
        IntegralBill bill = new IntegralBill();
        BeanUtils.copyProperties(integralBillDTO, bill);
        integralBillDao.save(bill);
        return Response.success("成功！");
    }
}
