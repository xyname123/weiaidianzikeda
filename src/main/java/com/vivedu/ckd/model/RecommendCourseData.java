package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendCourseData {
    //当前页码
    int currentPage;
    //总条数
    int Count;
    Object data;
}
