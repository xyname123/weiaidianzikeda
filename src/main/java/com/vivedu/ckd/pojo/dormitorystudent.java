package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dormitorystudent implements Serializable {

    @Id
    //宿舍学生关系
    private Integer pKey;
    //学生编号
    private String  no;
    //宿舍楼id
    private String  buildingId;
    //宿舍楼编号
    private String  buildingNo;
    //宿舍楼楼层id
    private String  floorId;
    //宿舍楼楼层编号
    private String  floorNo;
    // 宿舍楼房间id
    private String roomId;
    //宿舍楼房间编号
    private String roomNo;
    //更新时间
    private Number updateDate;

}
