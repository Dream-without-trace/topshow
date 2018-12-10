package com.luwei.services.course;

import com.aliyuncs.exceptions.ClientException;
import com.luwei.common.enums.status.MembershipCardOrderStatus;
import com.luwei.models.activity.order.ActivityOrder;
import com.luwei.models.activity.order.ActivityOrderDao;
import com.luwei.models.course.Course;
import com.luwei.models.course.CourseDao;
import com.luwei.models.courseEnrolment.CourseEnrolment;
import com.luwei.models.courseEnrolment.CourseEnrolmentDao;
import com.luwei.models.membershipcard.order.MembershipCardOrder;
import com.luwei.models.shop.Shop;
import com.luwei.models.user.User;
import com.luwei.module.alisms.AliSmsService;
import com.luwei.services.course.cms.CourseDTO;
import com.luwei.services.course.cms.CoursePageVo;
import com.luwei.services.membershipCard.order.MembershipCardOrderService;
import com.luwei.services.shop.ShopService;
import com.luwei.services.user.UserService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Resource
    private ActivityOrderDao activityOrderDao;

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
                        List<ActivityOrder> activityOrderList = activityOrderDao.findAllByUserIdAndAreaIdAndDeletedIsFalse(userId,shop.getAreaId());
                        if ((courseEnrolments2 == null || courseEnrolments2.size()<1)&&(activityOrderList == null || activityOrderList.size() < 1)) {
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
        CourseEnrolment courseEnrolment = new CourseEnrolment(shopId,courseId,userId,null);
        courseEnrolmentDao.save(courseEnrolment);
        String text = "恭喜您报名成功参加课程“"+course.getTitle()+"”，开课时间为："+course.getStartTime()+"-"+course.getEndTime()+"，请您合理安排上课时间，以免迟到。";
        System.out.println("1");
        aliSmsService.sendMessage(user.getPhone(),text);
    }



    /**
     * 根据课程ID查询课程
     * @param courseId
     * @return
     */
    public Course findOne(Integer courseId) {
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
        Long startDate = course.getStartDate();
        if (startDate != null && startDate != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            vo.setStartDate(sdf.format(new Date(startDate*1000)));
        }
        return vo;
    }

    public void delete(Set<Integer> ids) {courseDao.delByIds(new ArrayList<>(ids));
    }
}
