package com.vivedu.ckd.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfo {
    @Id
    private int id;
    //课程编号
    @Column
    private String courseId;
    //课程名称
    @Column
    private String courseName;
    //课程级别
    @Column
    private Integer courseType;
    //课程关键字
    @Column
    private String courseKeywords;
    //人数
    @Column
    private Integer studentNum;
    //开课时间
    @Column
    private String start;
    //结课时间
    @Column
    private String end;
    //课时
    @Column
    private String classHour;
    //课程封面
    @Column
    private String cover;
    //课程简介
    @Column
    private String profile;
    @Column
    private String courseFrom;
    //课程状态
    @Column
    private String state;
    //课程来源
    @Column
    private String source;
    //课程访问量
    @Column
    private String clickNum;
    @Column
    private String userId;
    //删除状态
    @Column
    private Integer isDel;
    //添加时间
    @Column
    private Timestamp insertDate;
    //url
    @Column
    private String url;
    @Column
    private String isRec;
    //教师团队
    private String teacher;
    //章节列表
    private String chapterList;


}