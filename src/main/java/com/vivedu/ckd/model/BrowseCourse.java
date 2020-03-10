package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseCourse {

    private  Integer Id;
    private  String userid;
    private  String courseid;
    private  Timestamp catTime;

}
