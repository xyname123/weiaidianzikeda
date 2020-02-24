package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.lang.ref.PhantomReference;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T_SHARE_CDXT_YJS_JBXX {
     @Id
    private  String  WID;
    //学号
    private  String  XH;
    //姓名
    private  String  XM;
    //院系代码
    private  String  YXDM;
    ////院系代码
    private  String  YXMC;
    //学生类别代码
    private  String  XSLBDM;
    //学生类别名称
    private  String  XSLBMC;
    //校区代码
    private  String  XQDM;
    //校区名称
    private  String  XQMC;
    //当前状态代码
    private  String  XSDQZTDM;
    //当前状态名称
    private  String  XSDQZTMC;
    //电子邮箱
    private  String  DZYX;
    //处理日期
    private Date CLRQ;
    //操作类型
    private  String  CZLX;


}
