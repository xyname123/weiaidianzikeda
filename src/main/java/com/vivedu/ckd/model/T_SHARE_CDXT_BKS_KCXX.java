package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T_SHARE_CDXT_BKS_KCXX {

    @Id
    private  String  WID;
    //课程代码
    private  String  KCDM;
    //课程名称
    private  String  KCMC;
    //学分
    private  String  XF;
    //周学时
    private  String  ZXS;
    //总和学时
    private  String  ZHXS;
    //课程类别代码
    private  String  KCLBDM;
    //课程类别名称
    private  String  KCLBMC;
    //课程开设单位代码
    private  String  KCKSDWDM;
    //课程开设单位名称
    private  String  KCKSDWMC;
    //是否启用
    private String SFQY;
    //处理日期
    private  Date  CLRQ;
    //操作类型
    private  String  CZLX;

}
