package com.vivedu.ckd.model;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoInfo {
    @Id
    private Integer id;
    //类型/栏目
    private String  type;
    //标题
    private String  title;
    //图片
    private String  img;
    //简介
    private String  content;
    //内容
    private String  body;
    //排序
    private Integer sort;
    //删除状态
    private Integer isDel;
    //添加时间
    private Timestamp insertDate;
    //跳转URL
    private String url;
    //来源
    private String source;
    //点击量
    private String clickNum;
    //是否添加到首页(轮播用)
    private Integer isRec;
}
