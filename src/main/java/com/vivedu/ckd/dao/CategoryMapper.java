package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.categoryCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int addCategory(@Param(value = "courseTypeName") String courseTypeName,
                    @Param(value = "courseTypeCode") String courseTypeCode,
                    @Param(value = "courseSort")String courseSort,
                    @Param(value = "courseHot")String courseHot);

    int upCategory(@Param(value = "courseTypeName") String courseTypeName,
                   @Param(value = "courseTypeCode") String courseTypeCode,
                   @Param(value = "courseSort")String courseSort,
                   @Param(value = "courseHot")String courseHot);

    List<categoryCode> findAllCode();

    int delCategroy(String courseTypeCode);
}
