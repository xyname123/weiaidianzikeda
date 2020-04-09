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
                    @Param(value = "courseSort") int courseSort,
                    @Param(value = "courseHot")String courseHot);

    int upCategory(@Param(value = "courseTypeName") String courseTypeName,
                   @Param(value = "courseTypeCode") String courseTypeCode,
                   @Param(value = "courseSort")int courseSort,
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

    List<categoryThird> findOneT();

    List<categoryThird> findMoreT(String courseTypeCode);

    List<categoryThird> findBenT(String courseTypeCode);

    Integer queryMaxSortIdC();

    List<categoryThird> findAllCodeT();

    Integer queryMaxSortIdT();

    int addCategoryT(String courseTypeName, String courseTypeCode, Integer courseSort, String courseHot);

    int upCategoryT(String courseTypeName, String courseTypeCode, Integer courseSort, String courseHot);

    int delCategroyT(String courseTypeCode);

    void insertT(@Param("category") List<categoryThird> category);
}
