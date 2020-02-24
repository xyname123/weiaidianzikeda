package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class courseschedule {

    //排课索引
    private Integer pKey;
    //校区编号
    private String  school;
    //年级编号
    private String grade;
    //学期编号
    private  String semester;
    //课程编号
    private  String course;
    //课程名
    private String  cName;
    //授课老师编号
    private String  teacher;
    //授课老师名字
    private String  tName;
    //上课的班级标号
    private String clazz;
    //学年
    private  String year;
    //单双周标识
    private String frequency;
    //周几
    private String week;
    //一天中的第几节
    private String section;
    //课程开始时间
    private String startTime;
    //课程结束时间
    private String endTime;
    //起始周
    private String startWeek;
    //结束周
    private String endWeek;
    //开始日期
    private String startDate;
    //结束日期
    private String  endDate;
    //教师编号
    private  String classroom;
    //教室名字
    private String roomName;
    //更新时间
    private  Number updateDate;

}
