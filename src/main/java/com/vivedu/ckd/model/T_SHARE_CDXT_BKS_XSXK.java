package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T_SHARE_CDXT_BKS_XSXK {

    @Id
    private String WID;
    //学号
    private String XH;
    //选课课号
    private String XKKH;
    //课程代码
    private String KCDM;
    //课程名称
    private String KCMC;
    //学年
    private String XN;
    //学期
    private String XQ;
    //处理日期
    private Date CLRQ;
    //操作类型
    private String CZLX;
}
