package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class semester {

    private  String pKey;

    private   String no;

    private  String school;

    private  String period;

    private String grade;

    private  String semester;

    private  String year;

    private  String startDate;

    private String endStart;

    //更新时间
    private Number updateDate;
}
