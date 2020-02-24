package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoAiVo {
        //课程编号

        private String courseid;
        //课程名称

        private String coursename;
        //课程级别

        private Integer courseType;
        //课程关键字

        private String courseKeywords;
        //人数

        private Integer studentnum;
        //开课时间

        private String start;
        //结课时间

        private String end;
        //课时

        private String classhour;
        //课程封面

        private String cover;
        //课程简介

        private String profile;
        //课程状态
        private String courseUrl;
        private String state;
        //source
        private String source;

        private String courseFrom;
        //课程访问量

        private String clicknum;
        //教师团队
        private String[] teacher;
       // "members":[{"profile":"","name":"李颉","userId":"5162106"}]
        //章节列表
        private String[] chapterList;
//chapters":[{"name":"未命名","id":48962,"parentid":0,"url":""

}


