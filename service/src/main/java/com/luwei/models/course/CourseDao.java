package com.luwei.models.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @program: topshow
 * @description: 课程dao接口
 * @author: ZhangHongJie
 * @create: 2018-12-03 16:57
 **/
public interface CourseDao extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {


    List<Course> findAllByStartDateAndShopIdAndDeletedFalse(Long startDate, Integer shopId);
    @Modifying
    @Query("update Course set deleted = 1 where courseId in ?1 and deleted <> 1")
    Integer delByIds(List<Integer> ids);
}
