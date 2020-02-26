package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentPersonalCenterInfo {
    private Integer courseCount;
    private Integer courseEnd;
    private String percentage;
    private List<StudyDuration> studyDurationList;
    private List<CourseInfo> courseInfoList;
    private List<ClassSchedule> classScheduleList;
}
