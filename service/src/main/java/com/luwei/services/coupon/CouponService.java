package com.luwei.services.coupon;

import com.luwei.common.Response;
import com.luwei.common.enums.status.CouponStatus;
import com.luwei.common.enums.type.BillType;
import com.luwei.common.enums.type.FlagType;
import com.luwei.common.enums.type.SortType;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.models.coupon.Coupon;
import com.luwei.models.coupon.CouponDao;
import com.luwei.models.integralbill.IntegralBill;
import com.luwei.models.user.User;
import com.luwei.models.user.coupon.UserCoupon;
import com.luwei.models.user.coupon.web.UserCouponAddDTO;
import com.luwei.services.coupon.cms.CouponAddDTO;
import com.luwei.services.coupon.cms.CouponEditDTO;
import com.luwei.services.coupon.cms.CouponPageVO;
import com.luwei.services.coupon.web.CouponDetailWebVO;
import com.luwei.services.coupon.web.CouponWebVO;
import com.luwei.services.integral.bill.IntegralBillService;
import com.luwei.services.user.UserService;
import com.luwei.services.user.coupon.UserCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
import java.util.List;
import java.util.Set;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-24
 **/
@Slf4j
@Service
@Transactional
public class CouponService {


    @Resource
    private CouponDao couponDao;

    @Resource
    private UserService userService;

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private IntegralBillService integralBillService;

    /**
     * cms分页
     *
     * @param pageable
     * @param name
     * @return
     */
    public Page<CouponPageVO> page(Pageable pageable, String name) {
        Specification<Coupon> specification = (Root<Coupon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (!StringUtils.isEmpty(name)) {
                list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("couponId").as(Integer.class)));
            return criteriaQuery.getRestriction();
        };
        return couponDao.findAll(specification, pageable).map(this::toCouponPageVO);
    }


    private CouponPageVO toCouponPageVO(Coupon coupon) {
        CouponPageVO vo = new CouponPageVO();
        BeanUtils.copyProperties(coupon, vo);
        return vo;
    }

    /**
     * 添加
     *
     * @param dto
     */
    public void save(CouponAddDTO dto) {
        if (dto.getDiscount() < 1 || dto.getDiscount() > 9) {
            throw new ValidateException(ExceptionMessage.WRONG_VALUE_OF_DISCOUNT_COUPONS);
        }
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(dto, coupon);
        couponDao.save(coupon);
    }

    /**
     * 修改
     *
     * @param dto
     */
    public CouponPageVO update(CouponEditDTO dto) {
        Coupon coupon = this.findOne(dto.getCouponId());
        BeanUtils.copyProperties(dto, coupon);
        Coupon entity = couponDao.save(coupon);
        return this.toCouponPageVO(entity);
    }

    /**
     * 修改状态
     *
     * @param couponId
     * @return
     */
    public CouponPageVO updateStatus(Integer couponId) {
        Coupon coupon = this.findOne(couponId);
        if (coupon.getStatus().equals(CouponStatus.DOWN)) {
            coupon.setStatus(CouponStatus.UP);
        } else {
            coupon.setStatus(CouponStatus.DOWN);
        }
        Coupon entity = couponDao.save(coupon);
        return this.toCouponPageVO(entity);
    }

    /**
     * 查找一个
     *
     * @param couponId
     * @return
     */
    public CouponPageVO one(Integer couponId) {
        return toCouponPageVO(this.findOne(couponId));
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Set<Integer> ids) {
        Integer result = couponDao.delByIds(new ArrayList<>(ids));
        if (result < 1) {
            throw new ValidateException(ExceptionMessage.DELETE_FAIL);
        }
    }

    /**
     * 查找一个
     *
     * @param couponId
     * @return
     */
    public Coupon findOne(Integer couponId) {
        Coupon coupon = couponDao.findById(couponId).orElse(null);
        Assert.notNull(coupon, "优惠券不存在");
        return coupon;
    }


    // --------------------------------------------------------------------


    /**
     * 小程序端分页
     *
     * @param pageable
     * @param price
     * @return
     */
    public Page<CouponWebVO> webPage(SortType price, Pageable pageable) {
        Specification<Coupon> specification = (Root<Coupon> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (!ObjectUtils.isEmpty(price)) {
                if (price.equals(SortType.ASC)) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price").as(Integer.class)));
                } else if (price.equals(SortType.DESC)) {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price").as(Integer.class)));
                }
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            return criteriaQuery.getRestriction();
        };
        return couponDao.findAll(specification, pageable).map(this::toCouponWebVO);
    }

    /**
     * 转换
     *
     * @param coupon
     * @return
     */
    private CouponWebVO toCouponWebVO(Coupon coupon) {
        CouponWebVO vo = new CouponWebVO();
        BeanUtils.copyProperties(coupon, vo);
        return vo;
    }

    /**
     * 优惠券详情
     *
     * @param couponId
     * @return
     */
    public CouponDetailWebVO detail(Integer couponId) {
        Coupon coupon = this.findOne(couponId);
        CouponDetailWebVO vo = new CouponDetailWebVO();
        BeanUtils.copyProperties(coupon, vo);
        return vo;
    }

    /**
     * 购买优惠券
     *
     * @param dto
     * @return
     */
    public Response<String> buyCoupon(UserCouponAddDTO dto) {
        User user = userService.findOne(dto.getUserId());
        Coupon coupon = this.findOne(dto.getCouponId());
        if (coupon.getStatus().equals(CouponStatus.DOWN)) {
            throw new ValidateException(ExceptionMessage.INCORRECT_COUPON_STATUS);
        }
        if (coupon.getPrice() > user.getIntegral()) {
            throw new ValidateException(ExceptionMessage.AVAILABLE_INTEGRAL_DEFICIENCY);
        }
        user.setIntegral(user.getIntegral() - coupon.getPrice());
        User userEntity = userService.save(user);

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setCouponTitle(coupon.getName());
        userCoupon.setFlagType(FlagType.DENY);
        userCoupon.setUserId(user.getUserId());
        userCoupon.setCouponId(coupon.getCouponId());
        userCoupon.setPicture(coupon.getPicture());
        userCoupon.setDiscount(coupon.getDiscount());
        userCouponService.save(userCoupon);

        // 添加积分流水
        IntegralBill bill = new IntegralBill();
        bill.setBillType(BillType.EXPEND);
        bill.setIntegral(coupon.getPrice());
        bill.setNickname(user.getNickname());
        bill.setPhone(user.getPhone());
        bill.setRemark("用户购买优惠券");
        bill.setUserId(user.getUserId());
        bill.setNowIntegral(userEntity.getIntegral());
        integralBillService.save(bill);
        return Response.success("购买成功");
    }


}
