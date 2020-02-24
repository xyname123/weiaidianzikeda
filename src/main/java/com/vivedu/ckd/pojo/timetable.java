package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class timetable implements Serializable {

    //学生索引
    private String pKey;
    //排课编号
    private String no;
    //校区编号
    private String school;
    //学期编号
    private String semester;
    //教学实践名称
    private String name;
    // 更新时间
    private  Number updateDate;
    //
    private  String  msg;


}
