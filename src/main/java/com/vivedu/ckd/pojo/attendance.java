package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class attendance implements Serializable {

    //卡号
    private String cardno;
    // 打卡时间
    private Date swipingDate;
    //打卡状态
    private String status;
    //角色
    private String role;
    //打卡类型
    private Integer type;
    //排课编号
    private  String csid;
    //课程编号
    private  String course;
    //周几
    private String week;
    //课程节次
    private  String section;

}
