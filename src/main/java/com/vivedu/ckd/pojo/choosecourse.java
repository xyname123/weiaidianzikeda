package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class choosecourse  implements Serializable {

    //选课索引
    private Integer pKey;
    //校区编号
    private String  school;
    //学生编号
    private String student;
    //班级编号
    private  String clazz;
    //课程编号
    private  String course;
    //课程名
    private String  cName;
    //学期编号
    private String  semester;
    //更新时间
    private  Number updateDate;


}
