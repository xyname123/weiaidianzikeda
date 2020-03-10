package com.vivedu.ckd.service;

import com.vivedu.ckd.dao.InfoInfoMapper;
import com.vivedu.ckd.model.InfoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoInfoService {
    @Autowired
    private InfoInfoMapper mapper;

    public InfoInfoService(InfoInfoMapper mapper) {
        this.mapper = mapper;
    }

    public List<InfoInfo> findNewsList(Integer pageNum, Integer pageSize,Integer isRec) {

        return  mapper.findNewsList(pageNum,pageSize,isRec);
    }

    public List<InfoInfo> findNewsDetail(Integer Id) {
        return mapper.findNewsDetail(Id);
    }

    public int findCount(Integer isRec) {
        return  mapper.findCount(isRec);
    }
}
