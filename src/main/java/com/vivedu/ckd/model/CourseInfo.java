package com.vivedu.ckd.model;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

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
    private String courseType;
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
    @Column
    private Integer StudyCount;
    @Column
    private String subjectcategory1;
    @Column
    private String subjectcategory2;
    @Column
    private String subjectcategory3;
    @Column
    private String  profileEn;
    @Column
    private String coursenameEn;
    //中国大学慕课的分类
    @Column
    private String  termType;
    @Column
    private String  summary;
    @Column
    private String courseTypeName;
    @Column
    private  Timestamp catTime;
    @Column
    private  Timestamp markedTime;
    //本地资源地址  默认空(localVideoUrl)
    private String localVideoUrl;
    //远程资源地址 替换(webVideoUrl)
    private String webVideoUrl;
    //资源类型(0视频,1网页); 默认0
    private Integer resourceType;
    //平台课程总数
/*    @Column
    private String  course;*/

}
