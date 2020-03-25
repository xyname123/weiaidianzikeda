package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MarkedCourse {

    private  Integer Id;
    private  String userid;
    private  Integer courseid;
    //0没收藏  1收藏
    private  Integer state;
    private Timestamp catTime;

}
