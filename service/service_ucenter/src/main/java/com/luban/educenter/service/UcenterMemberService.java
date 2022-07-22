package com.luban.educenter.service;

import com.luban.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luban.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Po Chen
 * @since 2022-07-18
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    Integer countRegisterDay(String day);
}
