package com.luwei.services.user;

import com.luwei.common.enums.type.BillType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.user.User;
import com.luwei.models.user.UserDao;
import com.luwei.models.user.receiving.ReceivingAddress;
import com.luwei.module.alisms.AliSmsService;
import com.luwei.services.activity.order.ActivityOrderService;
import com.luwei.services.area.AreaService;
import com.luwei.services.integral.bill.IntegralBillService;
import com.luwei.services.integral.bill.web.IntegralBillDTO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    public Boolean binding(String phone, Integer userId, String captcha) {
        User user = this.findOne(userId);
        boolean result = aliSmsService.matchingCaptcha(phone, captcha);
        // 判断用户验证码是否正确
        if (result) {
            user.setPhone(phone);
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
        user.setMicroBlog(dto.getMicroBlog());
        userDao.save(user);
    }


    /**
     * 获取用户积分总和
     *
     * @param userId
     * @return
     */
    public Integer findIntegralTotal(Integer userId) {
        User user = this.findOne(userId);
        return user.getIntegral();
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


}
