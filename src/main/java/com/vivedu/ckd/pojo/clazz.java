package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class clazz implements Serializable {

    @Id
    //班级索引
    private String pKey;
    //班级编号
    private String no;
    //班级名字
    private String name;
    //班级所在的年级
    private String grade;
    //更新时间
    private Number updateDate;
    //校区编码
    private String identity;

}
