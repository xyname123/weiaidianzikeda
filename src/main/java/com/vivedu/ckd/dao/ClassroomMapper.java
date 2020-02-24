package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassroomMapper {



    List<Classroom> findClassroomTotal(@Param(value = "shooolArea") String shooolArea,
                                       @Param(value = "dianNaoXin") Integer dianNaoXin,
                                       @Param(value = "max") String max,
                                       @Param(value = "keTangHuXin") Integer keTangHuXin,
                                       @Param(value = "luBoXin") Integer luBoXin);

    List<Classroom> findClassroom(@Param(value = "shooolArea")String shooolArea,
                                  @Param(value = "max") String max,
                                  @Param(value = "dianNaoXin")Integer dianNaoXin,
                                  @Param(value = "keTangHuXin")Integer keTangHuXin,
                                  @Param(value = "luBoXin")Integer luBoXin,
                                  @Param(value = "pageNum") Integer pageNum,
                                  @Param(value = "pageSize") Integer pageSize);

    List<Classroom> findClassroomE(@Param(value = "pageNum") Integer pageNum,
                                   @Param(value = "pageSize") Integer pageSize);

    int findClassroomK(@Param(value = "shooolArea")String shooolArea,
                       @Param(value = "max") String max,
                       @Param(value = "dianNaoXin")Integer dianNaoXin,
                       @Param(value = "keTangHuXin")Integer keTangHuXin,
                       @Param(value = "luBoXin")Integer luBoXin);

    int findClassroomJ();

}