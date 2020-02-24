package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoMetel {

        //课程名称
        private String coursename;
        //课程级别
        private Integer courseType;
         //link
         private String link;
        //课程封面
        private String cover;
        //课程简介
        private String profile;

        private String soure;
        //link
        private String url;
        private String courseFrom;
        //课程访问量

        //教师团队
        private String[] teacher;

        //章节列表
        private String[] chapterlist;


}


