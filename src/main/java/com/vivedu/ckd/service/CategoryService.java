package com.vivedu.ckd.service;

import com.vivedu.ckd.dao.CategoryMapper;
import com.vivedu.ckd.model.categoryCode;
import com.vivedu.ckd.model.categoryThird;
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

    public List<categoryThird> findAllCodeThird() {
        return mapper.findAllCodeThird();
    }

    public int addCategoryThird(String courseTypeName, String courseTypeCode, String courseSort, String courseHot, String source) {
        return mapper.addCategoryThird(courseTypeName,courseTypeCode,courseSort,courseHot,source);
    }

    public int upCategoryThird(String courseTypeName, String courseTypeCode, String courseSort, String courseHot, String source) {
        return mapper.upCategoryThird(courseTypeName,courseTypeCode,courseSort,courseHot,source);
    }

    public int delCategroyThird(String courseTypeCode) {
        return mapper.delCategroyThird(courseTypeCode);
    }

    public List<categoryCode> findOne() {
        return mapper.findOne();
    }

    public List<categoryCode> findMore(String courseTypeCode) {
        return mapper.findMore(courseTypeCode);
    }

    public List<categoryCode> findBen(String courseTypeCode) {
        return mapper.findBen(courseTypeCode);
    }

    public List<categoryThird> findOneT() {
        return mapper.findOneT();
    }

    public List<categoryThird> findMoreT(String courseTypeCode) {
        return mapper.findMoreT(courseTypeCode);
    }

    public List<categoryThird> findBenT(String courseTypeCode) {
        return mapper.findBenT(courseTypeCode);
    }
}
