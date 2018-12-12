package com.luwei.services.shop;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.luwei.common.Response;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.utils.DateTimeUtis;
import com.luwei.models.area.Area;
import com.luwei.models.area.AreaDao;
import com.luwei.models.course.Course;
import com.luwei.models.course.CourseDao;
import com.luwei.models.courseEnrolment.CourseEnrolment;
import com.luwei.models.courseEnrolment.CourseEnrolmentDao;
import com.luwei.models.membershipcard.MembershipCard;
import com.luwei.models.membershipcard.MembershipCardDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.membershipcard.order.MembershipCardOrderDao;
import com.luwei.models.shop.Shop;
import com.luwei.models.shop.ShopDao;
import com.luwei.models.user.User;
import com.luwei.models.user.UserDao;
import com.luwei.services.course.cms.CourseDTO;
import com.luwei.services.membershipCard.web.MembershipCardDetailVo;
import com.luwei.services.shop.cms.ShopAddDTO;
import com.luwei.services.shop.cms.ShopPageVo;
import com.luwei.services.shop.web.ShopDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: topshow
 * @description: 门店service
 * @author: ZhangHongJie
 * @create: 2018-12-03 15:14
 **/
@Slf4j
@Service
@Transactional
public class ShopService {

    @Resource
    private ShopDao shopDao;
    @Resource
    private MembershipCardDao membershipCardDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private CourseEnrolmentDao courseEnrolmentDao;
    @Resource
    private UserDao userDao;

    @Resource
    private MembershipCardOrderDao membershipCardOrderDao;

    @Resource
    private AreaDao areaDao;

    public void save(@Valid ShopAddDTO dto) {
        Shop shop = new Shop();
        List<Shop> shops = shopDao.findShopsByTitleAndAreaId(dto.getTitle(),dto.getAreaId());
        Assert.isTrue((shops == null || shops.size() < 1), "门店名称重复！");
        BeanUtils.copyProperties(dto, shop);
        String join = Joiner.on(",").join(dto.getPrevious());
        shop.setPrevious(join);
        shopDao.save(shop);
    }


    public ShopDetailVO shopWebDetail(Integer shopId, Integer userId) {
        User user = userDao.findById(userId).orElse(null);
        Assert.notNull(user, "用户不存在");
        List<MembershipCardOrder> membershipCardOrderList = membershipCardOrderDao.findAllByUserIdAndDeletedIsFalse(userId);
        Integer isSignUp = 2;//是否可报名：1：可以报名，2：不可报名
        Integer membershipOrderCardId = 0;
        if (membershipCardOrderList != null && membershipCardOrderList.size()>0) {
            for (MembershipCardOrder membershipCardOrder:membershipCardOrderList) {
                if (membershipCardOrder != null) {
                    MembershipCardOrderStatus status = membershipCardOrder.getStatus();
                    if (status != null && status.equals(MembershipCardOrderStatus.PAY)) {
                        String title = membershipCardOrder.getTitle();
                        if (title != null) {
                            if (title.equals("体验会员")) {
                                List<CourseEnrolment> list = courseEnrolmentDao.findAllByUserId(userId);
                                if (list == null || list.size() <1) {
                                    isSignUp = 1;
                                }
                            }else {
                                Integer effective = membershipCardOrder.getEffective();
                                Date payTime = membershipCardOrder.getPayTime();
                                if (payTime != null) {
                                    long time = payTime.getTime()+effective*3600*24*1000;
                                    long l = System.currentTimeMillis();
                                    if (time > l) {
                                        isSignUp = 1;
                                        membershipOrderCardId = membershipCardOrder.getMembershipCardOrderId();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Shop shop = this.findOne(shopId);
        ShopDetailVO shopDetailVO = new ShopDetailVO();
        shopDetailVO.setTitle(shop.getTitle());
        shopDetailVO.setShopId(shop.getShopId());
        shopDetailVO.setDetail(shop.getDetail());
        shopDetailVO.setPicture(shop.getPicture());
        shopDetailVO.setPrevious(Arrays.asList(shop.getPrevious().split(",")));
        List<JSONObject> coursesList = new ArrayList<>();
        long zeroDate = DateTimeUtis.getZeroDate().getTime();
        for (int i = 0; i < 7; i++) {
            long zeroDateTime = zeroDate;
            zeroDateTime += i*24*3600*1000;
            JSONObject dateJsonObject = new JSONObject();
            Date date = new Date(zeroDateTime);
            String week = DateTimeUtis.dayForWeek(date);
            dateJsonObject.put("week",week);
            SimpleDateFormat f=new SimpleDateFormat("MM月dd日");
            dateJsonObject.put("startDate",f.format(zeroDateTime));
            List<Course> courses = courseDao.findAllByStartDateAndShopIdAndDeletedFalse(zeroDateTime/1000,shopId);
            JSONArray courseList = new JSONArray();
            if (courses != null && courses.size()>0) {
                for (Course course:courses) {
                    if (course != null) {
                        JSONObject courseJsonObject = new JSONObject();
                        Integer courseId = course.getCourseId();
                        courseJsonObject.put("courseId",courseId);
                        courseJsonObject.put("startTime",course.getStartTime());
                        courseJsonObject.put("endTime",course.getEndTime());
                        courseJsonObject.put("title",course.getTitle());
                        courseJsonObject.put("description",course.getDescription());
                        courseJsonObject.put("picture",course.getPicture());
                        courseJsonObject.put("maxNum",course.getMaxNum());
                        int isEnroll = 2;
                        List<CourseEnrolment> list = courseEnrolmentDao.findAllByCourseId(courseId);
                        if (list == null || list.size()<course.getMaxNum()) {
                            List<CourseEnrolment> list1 = courseEnrolmentDao.findAllByCourseIdAndUserIdAndShopId(courseId,userId,shopId);
                            if (list1 != null && list1.size()>0) {
                                isEnroll = 1;
                            }else{
                                isSignUp = 2;
                            }
                        }
                        courseJsonObject.put("isEnroll",isEnroll);
                        courseJsonObject.put("isSignUp",isSignUp);
                        courseList.add(courseJsonObject);
                    }
                }
            }
            dateJsonObject.put("courseList",courseList);
            coursesList.add(dateJsonObject);
        }
        shopDetailVO.setCourses(coursesList);
        List<MembershipCardDetailVo> MembershipCardDetailVos = new ArrayList<>();
        List<MembershipCard> membershipCards = membershipCardDao.findAll();
        if (membershipCards != null && membershipCards.size() > 0) {
            for (MembershipCard membershipCard:membershipCards) {
                if (membershipCard != null) {
                    MembershipCardDetailVo membershipCardDetailVo = new MembershipCardDetailVo();
                    membershipCardDetailVo.setTitle(membershipCard.getTitle());
                    membershipCardDetailVo.setDetail(membershipCard.getDetail());
                    membershipCardDetailVo.setMembershipCardId(membershipCard.getMembershipCardId());
                    membershipCardDetailVo.setPicture(membershipCard.getPicture());
                    membershipCardDetailVo.setMemberBenefits(membershipCard.getMemberBenefits());
                    MembershipCardDetailVos.add(membershipCardDetailVo);
                }
            }
        }
        shopDetailVO.setMembershipOrderCardId(membershipOrderCardId);
        shopDetailVO.setMembershipCards(MembershipCardDetailVos);
        return shopDetailVO;
    }



    /**
     * 查找一个
     *
     * @param shopId
     * @return
     */
    public Shop findOne(Integer shopId) {
        Assert.isTrue(shopId != null && shopId != 0,"门店ID参数为空！");
        Shop shop = shopDao.findById(shopId).orElse(null);
        Assert.notNull(shop, "门店不存在");
        return shop;
    }

    public List<Shop> findAllByAreaId(Integer areaId) {
        return shopDao.findAllByAreaIdAndDeletedFalse(areaId);
    }

    public Page<ShopPageVo> page(Pageable pageable, String title) {
        Specification<Shop> specification = (Root<Shop> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("deleted").as(Integer.class), 0));
            if (!StringUtils.isEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("shopId")));
            return criteriaQuery.getRestriction();
        };
        return shopDao.findAll(specification, pageable).map(this::toShopPageVo);
    }


    /**
     * 转换
     *
     * @param shop
     * @return
     */
    private ShopPageVo toShopPageVo(Shop shop) {
        ShopPageVo vo = new ShopPageVo();
        BeanUtils.copyProperties(shop, vo);
        Integer areaId = shop.getAreaId();
        if (areaId != null && areaId != 0) {
            Optional<Area> parea = areaDao.findById(areaId);
            if (parea.isPresent()) {
                vo.setAreaId(areaId);
                vo.setAreaName(parea.get().getName());
            }
        }
        String previous = shop.getPrevious();
        if (previous != null && previous.length()>0) {
            String[] split = previous.split(",");
            vo.setPrevious(Arrays.asList(split));
        }
        return vo;
    }


    public void delete(Set<Integer> ids) {shopDao.delByIds(new ArrayList<>(ids));
    }


    public Response update(ShopAddDTO dto) {
        Shop shop = this.findOne(dto.getShopId());
        BeanUtils.copyProperties(dto, shop);
        shopDao.save(shop);
        return Response.build(2000,"成功！",dto);
    }
}
