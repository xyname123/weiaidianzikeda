package com.vivedu.ckd.model;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseStatistics {
    @Id
    private Integer id;
    //删除状态
    private Integer isDle;
    //添加时间
    //private Timestamp insertDate;
    //课程ID
    private Integer courseId;
    //课程名
    private Integer courseName;
    //学生数量
    private String StudentNum;
    //总课时
    private String totalClassHour;
    //已完成课时
    private String completedClassHour;
    //课程完成率
    private String CourseCompletionRate;
    //签到率
    private String attendanceRate;
    //作业数
    private String jobNum;
    //作业完成率
    private String JobCompletionRate;
    //平均互动数
    private String AverageInteractionNum;
    //平均得分
    private String AveragesCore;
}
