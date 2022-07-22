package com.luban.educms.controller;


import com.luban.commonutils.R;
import com.luban.educms.entity.CrmBanner;
import com.luban.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author Po Chen
 * @since 2022-07-18
 */
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> bannerList = bannerService.selectAllBanner();
        return R.ok().data("list", bannerList);
    }

}

