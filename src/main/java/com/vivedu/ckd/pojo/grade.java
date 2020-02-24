package com.vivedu.ckd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class grade implements Serializable{

    @Id
    //年级索引
    private String pKey;
    //年级编号
    private String no;
    //年级名字
    private String name;
    //年级学段
    private String period;
    //更新时间
    private Number updateDate;
    //校区编码
    private String identity;
}
