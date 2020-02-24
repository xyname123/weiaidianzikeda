package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dormitory implements Serializable {

    @Id
    //宿舍索引
    private Integer pKey;
    //编号
    private String  no;
    //父节点编号
    private String  parentNo;
    //宿舍楼编号
    private String  dormitoryNo;
    //资源类型
    private String  dormitoryType;
    //
    private String  priority;
    //状态
    private String status;
    //宿舍楼数量
    private String dormitoryNum;
    //校区编号
    private  String school;
    //更新时间
    private  Number updateDate;




}
