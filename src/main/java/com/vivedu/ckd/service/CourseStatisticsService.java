package com.vivedu.ckd.service;

import com.vivedu.ckd.dao.CourseStatisticsMapper;
import com.vivedu.ckd.model.CourseStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseStatisticsService {
    @Autowired
    private CourseStatisticsMapper mapper;

    public CourseStatisticsService(CourseStatisticsMapper mapper) {
        this.mapper = mapper;
    }

    public List<CourseStatistics> teachingData(String courseId, String departmentId, Integer page, Integer size, String sort, Integer subjecteId, Integer term, String userId) {
        return mapper.teachingData(courseId,departmentId,page,size,sort,subjecteId,term,userId);
    }
}
