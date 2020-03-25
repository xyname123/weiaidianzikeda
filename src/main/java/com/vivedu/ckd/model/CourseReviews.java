package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseReviews {
    private int id;
    //课程id
    private String courseId;
    //评论用户id
    private String userId;
    //评论内容
    private String comments;
    //状态
    private int status;
    //评论日期
    private Date reviewsDate;
    
    //映射字段，不添加数据库
    private String userName;
    private String courseName;
    
}
