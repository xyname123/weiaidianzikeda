package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T_SHARE_CDXT_BKS_KCPK {

    @Id
    private  String  WID;
    //选课课号
    private  String  XKKH;
    //课程代码
    private  String  KCDM;
    //课程名称
    private  String  KCMC;
    //总和学时
    private  String  ZHXS;
    //周学时
    private  String  ZXS;
    //学分
    private  String  XF;
    //起始周
    private  String  QSZ;
    //终止周
    private  String  ZZZ;
    //星期几
    private  String  XQJ;
    //开始节次
    private String KSJC;
    //结束节次
    private  String  JSJC;
    //上课周次（第二位开始是第一周，0-不上课，1-上课）
    private  String  SKZC;
    //校区代码
    private  String  XQDM;
    //校区名称
    private  String  XQMC;
    //教室代码
    private  String  JSDM;
    //教室名称
    private  String  JSMC;
    //开课院系代码
    private  String  KKYXDM;
    //开课院系名称
    private  String  KKYXMC;
    //课程类别代码
    private  String  KCLBDM;
    //课程类别名称
    private  String  KCLBMC;
    //是否重修
    private  String  SFCX;
    //学年
    private  String  XN;
    //学期
    private  String  XQ;
    //处理日期
    private  Date  CLRQ;
    //操作类型
    private  String  CZLX;

}
