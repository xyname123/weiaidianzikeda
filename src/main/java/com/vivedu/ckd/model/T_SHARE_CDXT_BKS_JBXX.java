package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T_SHARE_CDXT_BKS_JBXX {

    @Id
    private String WID;
    //学号
    private String XH;
    //姓名
    private String XM;
    //性别代码
    private String XBDM;
    //性别名称
    private String XBMC;
    //国际代码
    private String GJDM;
    //国籍名称
    private String GJMC;
    //院系代码
    private String YXDM;
    //院系名称
    private String YXMC;
    //专业代码
    private String ZYDM;
    //专业名称
    private String ZYMC;
    //班级代码
    private String BJDM;
    //班级名称
    private String BJMC;
    //所在年级
    private String SZNJ;
    //入学日期
    private String RXRQ;
    //预计毕业日期
    private String YJBYRQ;
    //学制
    private String XZ;
    //校区代码
    private String XQDM;
    //校区名称
    private String XQMC;
    //学生类别代码
    private String XSLBDM;
    //学生类别名称
    private String XSLBMC;
    //当前状态代码
    private String DQZTDM;
    //当前状态名称
    private String DQZTMC;
    //学籍状态代码
    private String XJZTDM;
    //学籍状态名称
    private String XJZTMC;
    //处理日期
    private Date CLRQ;
    //操作类型
    private String CZLX;

}
