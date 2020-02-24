package com.vivedu.ckd.service;

import com.vivedu.ckd.dao.CourseInfoMapper;
import com.vivedu.ckd.dao.JxServiceMapper;
import com.vivedu.ckd.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JxService {
    @Autowired
    private JxServiceMapper mapper;

    public JxService(JxServiceMapper mapper) {
        this.mapper = mapper;
    }


    public List<T_SHARE_CDXT_BKS_JBXX> findJxByName(String name) {
        return mapper.findJxByName(name);
    }

    public List<T_SHARE_CDXT_BKS_JBXX> findJxByGrade(String pKey) {

        return  mapper.findJxByGrade(pKey);
    }

    public List<T_SHARE_CDXT_BKS_JBXX> findJxByClazz(String pKey) {
        return  mapper.findJxByClazz(pKey);
    }

    public List<T_SHARE_CDXT_BKS_JSPK> findJxByNo(String no) {
        return  mapper.findJxByNo(no);
    }

    public Integer findJxByDor(String school) {
        return mapper.findJxByDor(school);
    }

    public Integer findJxByDNo(String no) {
        return mapper.findJxByDNo(no);
    }

    public List<T_SHARE_CDXT_YJS_JBXX> findJxBySchool(String identity) {

        return  mapper.findJxBySchool(identity);

    }

    public List<T_SHARE_CDXT_YJS_JBXX> findJxBySch() {

        return  mapper.findJxBySch();
    }

    public List<T_SHARE_CDXT_YJS_JBXX> findByGrdAll() {

        return  mapper.findByGrdAll();
    }

    public List<T_SHARE_CDXT_YJS_JBXX> findByGrd(String no, String identity) {
        return  mapper.findByGrd(no,identity);
    }

    public List<T_SHARE_CDXT_BKS_JBXX> findByCrdAll() {
        return mapper.findByCrdAll();
    }

    public List<T_SHARE_CDXT_BKS_JBXX> findByCrd(String no) {
        return mapper.findByCrd(no);
    }

    public List<T_SHARE_CDXT_BKS_KCPK> findByJsAll() {

        return mapper.findByJsAll();
    }

    public List<T_SHARE_CDXT_BKS_KCPK> findByJsCrid(String crid) {
        return mapper.findByJsCrid(crid);
    }

    public List<T_SHARE_CDXT_BKS_KCPK> findByJsIdentity(String identity) {
        return mapper.findByJsIdentity(identity);
    }

    public List<T_SHARE_CDXT_BKS_KCPK> findByCC(String identity, String crid) {
        return mapper.findByCC(identity,crid);
    }

    public List<T_SHARE_CDXT_BKS_JSPK> findByTeacher() {
        return mapper.findByTeacher();

    }

    public List<T_SHARE_CDXT_BKS_JSPK> findByTc(String no) {
        return mapper.findByTc(no);
    }

    public List<T_SHARE_CDXT_BKS_XSXK> findByStudent() {
        return mapper.findByStudent();

    }

    public List<T_SHARE_CDXT_BKS_XSXK> findByIf(String identity, String cid, String gid, String no) {
        return mapper.findByIf(identity,cid,gid,no);
    }

    public List<T_SHARE_CDXT_YJS_JBXX> findJxByDorX(String school) {
        return mapper.findJxByDorX(school);
    }

    public List<T_SHARE_CDXT_YJS_JBXX> findJxByDDNo(String no) {
        return mapper.findJxByDDNo(no);
    }
}
