package com.vivedu.ckd.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class TimesStatistics {
    private int id;
    /**
     * 总次数
     */
    private int totalNumber;
    /**
     * 日访问次数
     */
    private int dayNumber;
    /**
     * 类型标记（0-首页访问次数，1-学习人次）
     */
    private int typeTag;
    /**
     * 最新记录日期
     */
    private Date statisticalDate;
}
