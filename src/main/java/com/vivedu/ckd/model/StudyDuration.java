package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudyDuration {
    private Integer Id;
    //学号
    private String studentID;
    //学习时长
    private Integer learnTime;
    private Date createDate;
    //星期几
    private String dayWeek;
}
