package com.vivedu.ckd.service;

import com.vivedu.ckd.dao.ClassroomMapper;
import com.vivedu.ckd.model.Classroom;
import com.vivedu.ckd.model.areMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomMapper mapper;

    public ClassroomService(ClassroomMapper mapper) {
        this.mapper = mapper;
    }



    public List<Classroom> findClassroomTotal(String shooolArea, String dianNaoXin, String keTangHuXin, String luBoXin, Integer maxXin) {
       /* if (maxXin == areMessage.BIND.getCode()) {
            String max = areMessage.BIND.getMessage();
            return mapper.findClassroomTotal(shooolArea, dianNaoXin, keTangHuXin, luBoXin, max);
        }
        if (maxXin == areMessage.PARARM.getCode()) {
            String max = areMessage.PARARM.getMessage();
            return mapper.findClassroomTotal(shooolArea, dianNaoXin, keTangHuXin, luBoXin, max);
        }
        if (maxXin == areMessage.CODE.getCode()) {
            String max = areMessage.PARARM.getMessage();
            return mapper.findClassroomTotal(shooolArea, dianNaoXin, keTangHuXin, luBoXin, max);
        }
        if (maxXin == areMessage.SHUJU.getCode()) {
            String max = areMessage.PARARM.getMessage();
            return mapper.findClassroomTotal(shooolArea, dianNaoXin, keTangHuXin, luBoXin, max);
        }
        if (maxXin == areMessage.TIME.getCode()) {
            String max = areMessage.PARARM.getMessage();
            return mapper.findClassroomTotal(shooolArea, dianNaoXin, keTangHuXin, luBoXin, max);
        }
        if (maxXin == areMessage.REQUEST.getCode()) {
            String max = areMessage.PARARM.getMessage();
            return mapper.findClassroomTotal(shooolArea,dianNaoXin,max,keTangHuXin,luBoXin);
        }
        if (maxXin== areMessage.SERVER.getCode()){
            String max ="0";
            return mapper.findClassroomTotal(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin);
        }
        return null;*/
        return null;
    }
    public List<Classroom> findClassroom(String shooolArea, Integer maxXin, Integer dianNaoXin, Integer keTangHuXin, Integer luBoXin, Integer pageNum, Integer pageSize) {
        if (maxXin==null){
            String max="1";
            return mapper.findClassroom(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin,pageNum,pageSize);
        }
        if (maxXin== areMessage.BIND.getCode() && maxXin!=null){
            String max=areMessage.BIND.getMessage();
            return mapper.findClassroom(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin,pageNum,pageSize);
        }
        if (maxXin== areMessage.PARARM.getCode()&& maxXin!=null){
            String max=areMessage.PARARM.getMessage();
            return mapper.findClassroom(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin,pageNum,pageSize);
        }
        if (maxXin== areMessage.CODE.getCode()&& maxXin!=null){
            String max=areMessage.CODE.getMessage();
            return mapper.findClassroom(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin,pageNum,pageSize);
        }
        if (maxXin== areMessage.SHUJU.getCode()&& maxXin!=null){
            String max=areMessage.SHUJU.getMessage();
            return mapper.findClassroom(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin,pageNum,pageSize);
        }
        if (maxXin== areMessage.TIME.getCode()&& maxXin!=null){
            String max=areMessage.TIME.getMessage();
            return mapper.findClassroom(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin,pageNum,pageSize);
        }
        if (maxXin== areMessage.REQUEST.getCode()&& maxXin!=null){
            String max=areMessage.REQUEST.getMessage();
            return mapper.findClassroom(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin,pageNum,pageSize);
        }

        return null;
    }

    public List<Classroom> findClassroomE(Integer pageNum, Integer pageSize) {
        return mapper.findClassroomE(pageNum,pageSize);
    }

    public int findClassroomK(String shooolArea, Integer maxXin, Integer dianNaoXin, Integer keTangHuXin, Integer luBoXin) {
        if (maxXin==null){
            String max="1";
            return mapper.findClassroomK(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin);
        }
        if (maxXin== areMessage.BIND.getCode() && maxXin!=null){
            String max=areMessage.BIND.getMessage();
            return mapper.findClassroomK(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin);
        }
        if (maxXin== areMessage.PARARM.getCode()&& maxXin!=null){
            String max=areMessage.PARARM.getMessage();
            return mapper.findClassroomK(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin);
        }
        if (maxXin== areMessage.CODE.getCode()&& maxXin!=null){
            String max=areMessage.CODE.getMessage();
            return mapper.findClassroomK(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin);
        }
        if (maxXin== areMessage.SHUJU.getCode()&& maxXin!=null){
            String max=areMessage.SHUJU.getMessage();
            return mapper.findClassroomK(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin);
        }
        if (maxXin== areMessage.TIME.getCode()&& maxXin!=null){
            String max=areMessage.TIME.getMessage();
            return mapper.findClassroomK(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin);
        }
        if (maxXin== areMessage.REQUEST.getCode()&& maxXin!=null){
            String max=areMessage.REQUEST.getMessage();
            return mapper.findClassroomK(shooolArea,max,dianNaoXin,keTangHuXin,luBoXin);
        }

        return 1;
    }

    public int findClassroomJ() {

        return mapper.findClassroomJ();

    }
}

