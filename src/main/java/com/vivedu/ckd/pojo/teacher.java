package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class teacher implements Serializable {

    @Id
    //老师唯一索引
    private String pKey;
    //老师编号
    private String no;
    //老师姓名
    private String name;
    //老师卡号
    private String cardno;
    //老师照片
    private String photo;
    //性别
    private String gender;
    //更新时间
    private Number updateDate;
}
