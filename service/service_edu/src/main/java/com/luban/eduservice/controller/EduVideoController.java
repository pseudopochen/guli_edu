package com.luban.eduservice.controller;


import com.luban.commonutils.R;
import com.luban.eduservice.entity.EduChapter;
import com.luban.eduservice.entity.EduVideo;
import com.luban.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Course Chapter Videos
 * </p>
 *
 * @author Po Chen
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        boolean res = videoService.save(eduVideo);
        return res ? R.ok() : R.error();
    }

    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        boolean res = videoService.removeById(id);
        return res ? R.ok() : R.error();
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        boolean res = videoService.updateById(eduVideo);
        return res ? R.ok() : R.error();
    }

    @GetMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable String videoId) {
        EduVideo video = videoService.getById(videoId);
        return R.ok().data("video", video);
    }
}

