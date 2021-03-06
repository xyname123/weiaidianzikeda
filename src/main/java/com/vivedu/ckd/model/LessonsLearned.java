package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonsLearned {
    private Integer Id;
    //学号
    private BigInteger studentID;
    private Integer courseId;
    private Date createDate;
}
