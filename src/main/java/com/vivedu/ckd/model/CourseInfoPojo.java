package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoPojo {

    private int id;
    //课程编号

    private String courseId;
    //课程名称

    private String courseName;
    //课程级别

    private Integer courseType;
    //课程关键字

    private String courseKeywords;
    //人数

    private Integer studentNum;
    //开课时间

    private String start;
    //结课时间

    private String end;
    //课时

    private String classHour;
    //课程封面

    private String cover;
    //课程简介

    private String profile;

    private String courseFrom;
    //课程状态

    private String state;
    //课程来源

    private String source;
    //课程访问量

    private String clickNum;

    private String userId;
    //删除状态

    private Integer isDel;
    //添加时间

    private Timestamp insertDate;
    //url

    private String url;

    private String isRec;
    //教师团队
    private String teacher;
    //章节列表
    private String chapterList;

    private Integer StudyCount;

    private String subjectcategory1;

    private String subjectcategory2;

    private String subjectcategory3;

    private String  profileEn;

    private String coursenameEn;

    private String  termType;

    private String  timeData;



}
