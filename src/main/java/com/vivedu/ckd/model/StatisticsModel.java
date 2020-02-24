package com.vivedu.ckd.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class StatisticsModel {
    /**
     * 总次数
     */
    private int totalNumber;
    /**
     * 日访问次数
     */
    private int dayNumber;
    /**
     * 学习次数
     */
    private int studyNumber;
    /**
     * 课程访问次数
     */
    private int classNumber;
}
