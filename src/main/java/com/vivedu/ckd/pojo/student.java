package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class student implements Serializable {

    @Id
    //学生索引
    private String pKey;
    //学生编号
    private String no;
    //学生名字
    private String name;
    //学生卡号
    private String cardno;
    //学生所在年级
    private String grade;
    //年级名字
    private String gName;
    //学生所在班级
    private  String clazz;
    //班级名字
    private  String cname;
    //性别
    private  String  gender;
    //照片
    private  String  photo;
    //更新时间
    private  Number updateDate;
    //校区编码
    private String identity;
    //年级编码
    private String  gid;
    //班级编码
    private String cid;


}
