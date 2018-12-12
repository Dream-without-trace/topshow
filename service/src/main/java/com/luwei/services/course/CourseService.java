package com.luwei.services.course;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.luwei.common.Response;
import com.luwei.common.enums.status.ActivityStatus;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.common.utils.PageQuery;
import com.luwei.models.activity.Activity;
import com.luwei.models.activity.order.ActivityOrder;
import com.luwei.models.activity.order.ActivityOrderDao;
import com.luwei.models.course.Course;
import com.luwei.models.course.CourseDao;
import com.luwei.models.courseEnrolment.CourseEnrolment;
import com.luwei.models.courseEnrolment.CourseEnrolmentDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.shop.Shop;
import com.luwei.models.user.User;
import com.luwei.module.alisms.AliSmsProperties;
import com.luwei.module.alisms.AliSmsService;
import com.luwei.services.activity.web.ActivityWebListVO;
import com.luwei.services.course.cms.CourseDTO;
import com.luwei.services.course.cms.CourseOrderCMSPageVo;
import com.luwei.services.course.cms.CoursePageVo;
import com.luwei.services.course.web.CourseEnrolmentWebListVO;
import com.luwei.services.membershipCard.order.MembershipCardOrderService;
import com.luwei.services.shop.ShopService;
import com.luwei.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: topshow
 * @description: 课程service
 * @author: ZhangHongJie
 * @create: 2018-12-03 16:14
 **/
@Slf4j
@Service
@Transactional
public class CourseService {

    @Resource
    private CourseDao courseDao;
    @Resource
    private CourseEnrolmentDao courseEnrolmentDao;
    @Resource
    private UserService userService;
    @Resource
    private ShopService shopService;
    @Resource
    private MembershipCardOrderService membershipCardOrderService;
    @Resource
    private AliSmsService aliSmsService;
    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private ActivityOrderDao activityOrderDao;

    @Resource
    private AliSmsProperties aliSmsProperties;

    public void save(CourseDTO dto) {
        Course course = new Course();
        BeanUtils.copyProperties(dto, course);
        String startDate = dto.getStartDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(startDate);
            long ts = date.getTime();
            course.setStartDate(ts);
            courseDao.save(course);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 报名课程
     * @param shopId
     * @param courseId
     * @param userId
     * @return
     */
    public void enrolment(Integer shopId, Integer courseId, Integer userId) throws ClientException {
        User user = userService.findOne(userId);
        Shop shop = shopService.findOne(shopId);
        Course course = this.findOne(courseId);
        Integer maxNum = course.getMaxNum();
        Assert.isTrue((maxNum != null && maxNum > 0), "课程最大报名数为空！");
        List<CourseEnrolment> courseEnrolments = courseEnrolmentDao.findAllByCourseIdAndUserIdAndShopId(courseId, userId, shopId);
        Assert.isTrue((courseEnrolments == null || courseEnrolments.size()<1), "您已报名了该课程！");
        List<CourseEnrolment> courseEnrolments1 = courseEnrolmentDao.findAllByCourseIdAndShopId(courseId,shopId);
        Assert.isTrue((courseEnrolments1 == null || courseEnrolments1.size() < maxNum), "课程已报满！");
        List<MembershipCardOrder> membershipCardOrders = membershipCardOrderService.
                findAllByUserIdAndStatus(userId, MembershipCardOrderStatus.PAY);
        Assert.isTrue((membershipCardOrders != null && membershipCardOrders.size() > 0), "您未办理会员卡！");
        int state = 1;
        for (MembershipCardOrder membershipCardOrder:membershipCardOrders) {
            if (membershipCardOrder != null) {
                Integer effective = membershipCardOrder.getEffective();
                long time = membershipCardOrder.getPayTime().getTime()+effective*24*3600*1000;
                long l = System.currentTimeMillis();
                String title = membershipCardOrder.getTitle();
                if (!StringUtils.isEmpty(title)) {
                    if (title.equals("体验会员")){
                        List<CourseEnrolment> courseEnrolments2 = courseEnrolmentDao.findAllByUserId(userId);
                        //List<ActivityOrder> activityOrderList = activityOrderDao.findAllByUserIdAndAreaIdAndDeletedIsFalse(userId,shop.getAreaId());
                        if (courseEnrolments2 == null || courseEnrolments2.size()<1) {
                            state = 2;
                        }
                    }else{
                        if (time > l) {
                            state = 2;
                        }
                    }
                }
            }
        }
        Assert.isTrue((state == 2), "您办理的会员卡已过期！");
        CourseEnrolment courseEnrolment = new CourseEnrolment(shopId,courseId,userId,null,1);
        courseEnrolmentDao.save(courseEnrolment);
        Long startDate = course.getStartDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String format = sdf.format(startDate * 1000);
        String courseTime =format+ course.getStartTime()+"-"+course.getEndTime();
        String text = "{\"date\":\""+courseTime+"\", \"name\":\""+course.getTitle()+"\"}";
        aliSmsService.sendMessage(user.getPhone(),aliSmsProperties.getEnrollCoursesCode(),text);
    }


    /**
     * 根据课程ID查询课程
     * @param courseId
     * @return
     */
    public Course findOne(Integer courseId) {
        Assert.isTrue(courseId != null && courseId != 0,"课程Id参数不能为空！");
        Course course = courseDao.findById(courseId).orElse(null);
        Assert.notNull(course, "课程不存在");
        return course;
    }

    public Page<CoursePageVo> page(Pageable pageable, String title) {
        Specification<Course> specification = (Root<Course> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("courseId")));
            return criteriaQuery.getRestriction();
        };
        return courseDao.findAll(specification, pageable).map(this::toCoursePageVo);
    }


    /**
     * 转换
     *
     * @param course
     * @return
     */
    private CoursePageVo toCoursePageVo(Course course) {
        CoursePageVo vo = new CoursePageVo();
        BeanUtils.copyProperties(course, vo);
        Integer shopId = course.getShopId();
        Assert.isTrue(shopId != null && shopId != 0,"关联的门店ID为空！");
        Long startDate = course.getStartDate();
        if (startDate != null && startDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            vo.setStartDate(sdf.format(new Date(startDate*1000)));
        }
        Shop shop = shopService.findOne(shopId);
        vo.setShopId(shopId);
        vo.setShopName(shop.getTitle());
        return vo;
    }

    public void delete(Set<Integer> ids) {courseDao.delByIds(new ArrayList<>(ids));
    }

    public Page<CourseEnrolmentWebListVO> enrolmentCourseList(Integer userId, Integer isInspectTicket, Pageable pageable) {
        Assert.isTrue(userId != null && userId != 0,"参数用户ID为空！");
        User user = userService.findOne(userId);
        Specification<CourseEnrolment> specification = (Root<CourseEnrolment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("userId"), userId));
            if (isInspectTicket != null && (isInspectTicket == 1 || isInspectTicket == 2)) {
                list.add(criteriaBuilder.equal(root.get("isInspectTicket"), isInspectTicket));
            }
            Predicate[] predicates = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(predicates));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("courseEnrolmentId")));
            return criteriaQuery.getRestriction();
        };
        return courseEnrolmentDao.findAll(specification, pageable).map(this::toCourseEnrolmentWebListVO);
    }

    private CourseEnrolmentWebListVO toCourseEnrolmentWebListVO(CourseEnrolment courseEnrolment) {
        Assert.notNull(courseEnrolment,"参数不能为空！");
        Integer shopId = courseEnrolment.getShopId();
        Shop shop = shopService.findOne(shopId);
        Integer courseId = courseEnrolment.getCourseId();
        Course course = this.findOne(courseId);
        Long startDate = course.getStartDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date(startDate * 1000));
        Date createTime = courseEnrolment.getCreateTime();
        String createTimeStr = "";
        if (createTime != null) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            createTimeStr = sdf1.format(createTime);
        }
        CourseEnrolmentWebListVO vo = new CourseEnrolmentWebListVO(courseEnrolment.getCourseEnrolmentId(),
                shop.getTitle(),course.getTitle(),
                format,course.getStartTime(),course.getEndTime(),createTimeStr,course.getPicture(),courseEnrolment.getIsInspectTicket());
        return vo;
    }


    public PageImpl courseOrderPage(Pageable pageable, String phone) {
        String contentSql = "select new com.luwei.services.course.cms.CourseOrderCMSPageVo " +
                "(ce.courseEnrolmentId,u.avatarUrl,u.nickname,u.phone,c.title,c.startDate,c.startTime,c.endTime," +
                "c.picture,ce.isInspectTicket,ce.createTime)" +
                " from CourseEnrolment ce, User u,Course c where ce.userId = u.userId and ce.courseId = c.courseId";

        String countSql = "select count(ce.courseEnrolmentId) from CourseEnrolment ce,  User u,Course c where ce.userId = u.userId and ce.courseId = c.courseId";

        if (phone != null && !"".equals(phone.trim())) {
            countSql += " and u.phone like ?0";
            contentSql += " and u.phone like ?0 order by ce.courseEnrolmentId desc";
        } else {
            contentSql += " order by ce.courseEnrolmentId desc";
        }

        Query countQuery = entityManager.createQuery(countSql);
        Query contentQuery = entityManager.createQuery(contentSql);

        if (phone != null && !"".equals(phone.trim())) {
            countQuery.setParameter(0, phone.replace(" ",""));
            contentQuery.setParameter(0, phone.replace(" ",""));
        }

        contentQuery.setMaxResults(pageable.getPageSize());
        contentQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        Long total = (Long) countQuery.getSingleResult();
        List content = contentQuery.getResultList();
        return new PageImpl<>(content, pageable, total);
    }


    public Response checkCourseOrder(Integer courseEnrolmentId) {
        Optional<CourseEnrolment> courseEnrolment = courseEnrolmentDao.findById(courseEnrolmentId);
        Assert.isTrue(courseEnrolment.isPresent(),"订单ID不可用！");
        CourseEnrolment courseEnrolment1 = courseEnrolment.get();
        Assert.notNull(courseEnrolment1,"订单ID不可用！");
        courseEnrolment1.setIsInspectTicket(2);
        courseEnrolmentDao.save(courseEnrolment1);
        return Response.success("成功！");
    }

    public Response update(CourseDTO dto) {
        Course course = this.findOne(dto.getCourseId());
        BeanUtils.copyProperties(dto, course);
        courseDao.save(course);
        return Response.build(2000,"成功！",course);

    }
}
