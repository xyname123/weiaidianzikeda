package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class course  implements Serializable {

    //课程索引
    private Integer pKey;
    //校区编号
    private String  school;
    //课程名
    private String  name;
    //课程标签
    private String  note;
    //授课老师
    private String  teacher;
    //授课老师名字
    private String  tName;
    //年级编号
    private String grade;
    //学期编号
    private String semester;
    //更新时间
    private  Number updateDate;
}
