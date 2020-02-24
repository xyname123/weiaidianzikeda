package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.CourseStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CourseStatisticsMapper {


    List<CourseStatistics> teachingData(@Param(value = "courseId")String courseId,
                                        @Param(value = "departmentId")String departmentId,
                                        @Param(value = "page")Integer pageNum,
                                        @Param(value = "size")Integer pagesize,
                                        @Param(value = "sort")String sort,
                                        @Param(value = "subjecteId")Integer subjecteId,
                                        @Param(value = "term")Integer term,
                                        @Param(value = "userId")String userId);
}
