package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.categoryCode;
import com.vivedu.ckd.model.categoryThird;
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

    List<categoryThird> findAllCodeThird();

    int addCategoryThird(@Param(value = "courseTypeName")String courseTypeName,
                         @Param(value = "courseTypeCode") String courseTypeCode,
                         @Param(value = "courseSort")String courseSort,
                         @Param(value = "courseHot")String courseHot,
                         @Param(value = "source")String source);

    int upCategoryThird(@Param(value = "courseTypeName")String courseTypeName,
                        @Param(value = "courseTypeCode")String courseTypeCode,
                        @Param(value = "courseSort") String courseSort,
                        @Param(value = "courseHot") String courseHot,
                        @Param(value = "source")String source);

    int delCategroyThird(String courseTypeCode);

    List<categoryCode> findOne();

    List<categoryCode> findMore(String courseTypeCode);

    List<categoryCode> findBen(String courseTypeCode);
}