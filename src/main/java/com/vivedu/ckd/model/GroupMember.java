package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember {

    private int id;
    private int courseId;
    private int groupClassId;
    private int sort;

    private String courseName;
    private String cover;
}
