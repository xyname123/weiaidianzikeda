package com.vivedu.ckd.service;

import com.vivedu.ckd.dao.MarkedCourseMapper;
import com.vivedu.ckd.model.MarkedCourse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class MarkedCourseService {

    @Autowired
    private MarkedCourseMapper mapper;

    public int marked(String userid, Integer state, Integer courseid) {
        return  mapper.marked(userid,state,courseid);
    }

    public MarkedCourse findMarkedCourse(String userid, Integer state, Integer courseid) {
        return  mapper.findMarkedCourse(userid,state,courseid);
    }

    public int addMarked(String userid, Integer state, Integer courseid) {
        return  mapper.addMarked(userid,state,courseid);
    }

    public List<MarkedCourse> courseInfoByMarked(String userid, Integer pageNum, Integer pageSize) {
        return  mapper.courseInfoByMarked(userid,pageNum,pageSize);
    }

    public int courseInfoByMarkedNum(String userid) {
        return  mapper.courseInfoByMarkedNum(userid);
    }


    public void catTimecourse(Timestamp catTime, Integer courseid) {
        mapper.catTimecourse(catTime,courseid);
    }

    public List<MarkedCourse> findMarkedCourseK(String userid) {
        return  mapper.findMarkedCourseK(userid);
    }

    public MarkedCourse findMarkedGm(Integer userid, Integer id) {
        return  mapper.findMarkedGm(userid,id);
    }
}
