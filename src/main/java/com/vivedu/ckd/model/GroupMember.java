package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember {

    private int id;
    //课程id
    private int courseId;
    //组团id
    private int groupClassId;
    //,排序码
    private int sort;

    private String courseName;
    private String cover;
}
