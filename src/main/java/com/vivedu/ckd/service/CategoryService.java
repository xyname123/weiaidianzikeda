package com.vivedu.ckd.service;

import com.vivedu.ckd.dao.CategoryMapper;
import com.vivedu.ckd.model.categoryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper mapper;


    public int addCategory(String courseTypeName, String courseTypeCode, String courseSort, String courseHot) {


        return mapper.addCategory(courseTypeName,courseTypeCode,courseSort,courseHot);


    }

    public int upCategory(String courseTypeName, String courseTypeCode, String courseSort, String courseHot) {
        return mapper.upCategory(courseTypeName,courseTypeCode,courseSort,courseHot);
    }

    public List<categoryCode> findAllCode() {

        return mapper.findAllCode();
    }

    public int delCategroy(String courseTypeCode) {
        return mapper.delCategroy(courseTypeCode);
    }
}
