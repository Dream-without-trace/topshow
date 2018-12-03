package com.luwei.services.course;

import com.luwei.models.course.Course;
import com.luwei.models.course.CourseDao;
import com.luwei.services.course.cms.CourseAddDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    public void save(CourseAddDTO dto) {
        Course course = new Course();
        BeanUtils.copyProperties(dto, course);
        courseDao.save(course);
    }
}
