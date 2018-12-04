package com.luwei.services.shop;

import com.google.common.base.Joiner;
import com.luwei.models.course.Course;
import com.luwei.models.course.CourseDao;
import com.luwei.models.courseEnrolment.CourseEnrolment;
import com.luwei.models.courseEnrolment.CourseEnrolmentDao;
import com.luwei.models.membershipcard.MembershipCard;
import com.luwei.models.membershipcard.MembershipCardDao;
import com.luwei.models.shop.Shop;
import com.luwei.models.shop.ShopDao;
import com.luwei.models.user.User;
import com.luwei.models.user.UserDao;
import com.luwei.services.course.web.CourseDetailVo;
import com.luwei.services.membershipCard.web.MembershipCardDetailVo;
import com.luwei.services.shop.cms.ShopAddDTO;
import com.luwei.services.shop.web.ShopDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        Shop shop = this.findOne(shopId);
        ShopDetailVO shopDetailVO = new ShopDetailVO();
        shopDetailVO.setTitle(shop.getTitle());
        shopDetailVO.setShopId(shop.getShopId());
        shopDetailVO.setDetail(shop.getDetail());
        shopDetailVO.setPicture(shop.getPicture());
        shopDetailVO.setPrevious(Arrays.asList(shop.getPrevious().split(",")));
        List<Course> courses = courseDao.findCoursesByShopId(shopId);
        List<CourseDetailVo>CourseDetailVos = new ArrayList<>();
        if (courses != null && courses.size() > 0) {
            for (Course course:courses) {
                if (course != null) {
                    Integer courseId = course.getCourseId();
                    Date startTime = course.getStartTime();
                    SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd");
                    String dateString = formatter1.format(startTime);
                    SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
                    String startTimeStr = formatter2.format(startTime);
                    Date endTime = course.getEndTime();
                    String endTimeStr = formatter2.format(endTime);
                    Integer isSignUp = 1;//是否可报名：1：可以报名，2：不可报名
                    List<CourseEnrolment> list = courseEnrolmentDao.findAllByCourseIdAndUserIdAndShopId(courseId,userId,shopId);
                    if (list != null && list.size() > 0) {
                        isSignUp = 2;
                    }
                    CourseDetailVo courseDetailVo = new CourseDetailVo(courseId,course.getTitle(),startTimeStr,
                            endTimeStr,dateString,isSignUp);
                    CourseDetailVos.add(courseDetailVo);
                }
            }
        }
        shopDetailVO.setCourses(CourseDetailVos);
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
                    MembershipCardDetailVos.add(membershipCardDetailVo);
                }
            }
        }
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
        Shop shop = shopDao.findById(shopId).orElse(null);
        Assert.notNull(shop, "门店不存在");
        return shop;
    }
}
