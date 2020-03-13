package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class categoryCode {
    private  Integer Id;
    private  String courseTypeName;
    private  String courseTypeCode;
    private Integer courseSort;
    private Integer isDel;
    private Integer courseHot;
}
