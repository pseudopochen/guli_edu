package com.luban.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luban.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luban.eduservice.entity.frontvo.CourseFrontVo;
import com.luban.eduservice.entity.frontvo.CourseWebVo;
import com.luban.eduservice.entity.vo.CourseInfoVo;
import com.luban.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-07-15
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
