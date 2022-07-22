package com.luban.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luban.eduservice.entity.EduSubject;
import com.luban.eduservice.entity.excel.SubjectData;
import com.luban.eduservice.entity.subject.OneSubject;
import com.luban.eduservice.entity.subject.TwoSubject;
import com.luban.eduservice.listener.SubjectExcelListener;
import com.luban.eduservice.mapper.EduSubjectMapper;
import com.luban.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Course Subject Service Implementation
 * </p>
 *
 * @author Po Chen
 * @since 2022-07-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper);

        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapper2);

        List<OneSubject> res = new ArrayList<>();
        for (EduSubject oneSubject : oneSubjectList) {
            OneSubject s1l = new OneSubject();
            BeanUtils.copyProperties(oneSubject, s1l);

            for (EduSubject twoSubject : twoSubjectList) {
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    TwoSubject s2l = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, s2l);
                    s1l.getChildren().add(s2l);
                }
            }

            res.add(s1l);
        }

        return res;
    }
}
