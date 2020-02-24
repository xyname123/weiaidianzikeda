package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.InfoInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface InfoInfoMapper {

    List<InfoInfo> findNewsList(@Param(value = "pageNum") Integer pageNum,
                                @Param(value = "pageSize") Integer pageSize);

    List<InfoInfo> findNewsDetail(Integer id);
}
