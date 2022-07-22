package com.luban.eduservice.mapper;

import com.luban.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luban.eduservice.entity.frontvo.CourseWebVo;
import com.luban.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * Course Mapper Interface
 * </p>
 *
 * @author Po Chen
 * @since 2022-07-15
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
