package com.luwei.models.courseEnrolment;

import com.luwei.common.utils.PageQuery;
import com.luwei.services.course.cms.CourseOrderCMSPageVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: topshow
 * @description: 报名课程Dao
 * @author: ZhangHongJie
 * @create: 2018-12-04 23:45
 **/
public interface CourseEnrolmentDao extends JpaRepository<CourseEnrolment, Integer>, JpaSpecificationExecutor<CourseEnrolment> {
    List<CourseEnrolment> findAllByCourseIdAndUserIdAndShopId(Integer courseId, Integer userId, Integer shopId);
    List<CourseEnrolment> findAllByCourseIdAndShopId(Integer courseId, Integer shopId);

    List<CourseEnrolment> findAllByUserId(Integer userId);

    List<CourseEnrolment> findAllByCourseId(Integer courseId);

}
