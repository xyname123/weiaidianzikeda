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


    public int addCategory(String courseTypeName, String courseTypeCode, Integer courseSort, String courseHot) {



        return mapper.addCategory(courseTypeName,courseTypeCode,courseSort,courseHot);


    }

    public int upCategory(String courseTypeName, String courseTypeCode, Integer courseSort, String courseHot) {
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

    public List<categoryCode> findOne(Integer pageNum, Integer pageSize) {
        return mapper.findOne(pageNum,pageSize);
    }

    public List<categoryCode> findMore(String courseTypeCode, Integer pageNum, Integer pageSize) {
        return mapper.findMore(courseTypeCode,pageNum,pageSize);
    }

    public List<categoryCode> findBen(String courseTypeCode) {
        return mapper.findBen(courseTypeCode);
    }

    public List<categoryThird> findOneT(Integer pageNum, Integer pageSize) {
        return mapper.findOneT(pageNum,pageSize);
    }

    public List<categoryThird> findMoreT(String courseTypeCode) {
        return mapper.findMoreT(courseTypeCode);
    }

    public List<categoryThird> findBenT(String courseTypeCode) {
        return mapper.findBenT(courseTypeCode);
    }

    public Integer queryMaxSortId() {
        return mapper.queryMaxSortIdC();
    }

    public List<categoryThird> findAllCodeT() {
        return mapper.findAllCodeT();
    }

    public Integer queryMaxSortIdT() {
        return mapper.queryMaxSortIdT();
    }

    public int addCategoryT(String courseTypeName, String courseTypeCode, Integer courseSort, String courseHot) {

        return mapper.addCategoryT(courseTypeName,courseTypeCode,courseSort,courseHot);

    }

    public int upCategoryT(String courseTypeName, String courseTypeCode, Integer courseSort, String courseHot) {

        return mapper.upCategoryT(courseTypeName,courseTypeCode,courseSort,courseHot);
    }

    public int delCategroyT(String courseTypeCode) {
        return mapper.delCategroyT(courseTypeCode);
    }

    public void insertT(List<categoryThird> category) {
        mapper.insertT(category);
    }

    public int row() {
        return mapper.row();
    }

    public int rowMore(String courseTypeCode) {
        return mapper.rowMore(courseTypeCode);
    }

    public int rowT() {
        return mapper.rowT();
    }

    public int rowMoreT(String courseTypeCode) {
        return mapper.rowMoreT(courseTypeCode);
    }
}
