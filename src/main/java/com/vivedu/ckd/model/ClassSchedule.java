package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassSchedule {
    private Integer time;
    private Integer day;
    private String className;
}
