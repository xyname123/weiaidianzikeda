package com.vivedu.ckd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterList {
    private  Integer parentId;
    private  String name;
    private  String url;
    private  String chapterId;
    private  String courseId;
}
