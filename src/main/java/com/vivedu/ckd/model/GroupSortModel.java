package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupSortModel {
    //组团id
    private int groupId;

    //组团里的课程排序 以逗号隔开
    private String groupMemberSort;
}
