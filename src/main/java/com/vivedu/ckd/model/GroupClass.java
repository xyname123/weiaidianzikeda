package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupClass {
    private int id;
    //组团名
    private String groupName;
    //排序字段
    private int sort;

    private int size;
}
