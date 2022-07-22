package com.luban.eduservice.controller;


import com.luban.commonutils.R;
import com.luban.eduservice.entity.EduChapter;
import com.luban.eduservice.entity.chapter.ChapterVo;
import com.luban.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * Course Chapter Controller
 * </p>
 *
 * @author Po Chen
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId) {
        EduChapter edu = chapterService.getById(chapterId);
        return R.ok().data("chapter", edu);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean res = chapterService.deleteChapter(chapterId);
        return res ? R.ok() : R.error();
    }
}

