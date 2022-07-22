package com.luban.eduservice.controller;


import com.luban.commonutils.R;
import com.luban.eduservice.entity.EduCourse;
import com.luban.eduservice.entity.vo.CourseInfoVo;
import com.luban.eduservice.entity.vo.CoursePublishVo;
import com.luban.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Course Controller
 * </p>
 *
 * @author Po Chen
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        //System.out.println("courseId: "+ courseId);
        CourseInfoVo courseInfo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfo);
    }

    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfo) {
        courseService.updateCourseInfo(courseInfo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @GetMapping
    public R getCourseList() {
        return R.ok().data("list", courseService.list(null));
    }

    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

