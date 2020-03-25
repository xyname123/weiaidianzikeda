package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.MarkedCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface MarkedCourseMapper {

    int marked(@Param("userid") String userid,@Param("state") Integer state,@Param("courseid")Integer courseid);

    MarkedCourse findMarkedCourse(@Param("userid")String userid,@Param("state")Integer state,@Param("courseid")Integer courseid);

    int addMarked(@Param("userid")String userid, @Param("state")Integer state,@Param("courseid")Integer courseid);

    List<MarkedCourse> courseInfoByMarked(@Param("userid")String userid,@Param("pageNum")Integer pageNum,@Param("pageSize") Integer pageSize);

    int courseInfoByMarkedNum(String userid);

    void catTimecourse(@Param("catTime")Timestamp catTime,@Param("courseid")Integer courseid);


    List<MarkedCourse> findMarkedCourseK(String userid);

    MarkedCourse findMarkedGm(@Param("userid")Integer userid,@Param("id") Integer id);
}
