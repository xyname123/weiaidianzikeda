package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class categoryThird {
    private  Integer Id;
    private  String courseTypeName;
    private  String courseTypeCode;
    private Integer courseSort;
    private Integer isDel;
    private Integer courseHot;
    private Integer source;
}
