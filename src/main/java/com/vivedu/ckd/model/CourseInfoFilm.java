package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoFilm {


        //课程编号

        private String courseid;
        //课程名称

        private String coursename;
        //课程级别

        private String courseType;
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
        private String source;

        private String state;
        //课程来源
        private String url;

        private String courseFrom;
        //课程访问量

        private String clicknum;
        //教师团队
        private String[] teacher;
        //章节列表
        private String[] chapterlist;


}


