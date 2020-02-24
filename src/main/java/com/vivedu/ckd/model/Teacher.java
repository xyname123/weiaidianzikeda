package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private Integer Id;
    //课程编号
    private String courseId;
    private String name;
    private String profile;
}

