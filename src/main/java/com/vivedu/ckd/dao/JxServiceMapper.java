package com.vivedu.ckd.dao;

import com.vivedu.ckd.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JxServiceMapper {

    List<T_SHARE_CDXT_BKS_JBXX> findJxByName(String name);

    List<T_SHARE_CDXT_BKS_JBXX> findJxByGrade(String pKey);

    List<T_SHARE_CDXT_BKS_JBXX> findJxByClazz(String pKey);

    List<T_SHARE_CDXT_BKS_JSPK> findJxByNo(String no);

    Integer findJxByDor(String school);

    Integer findJxByDNo(String no);

    List<T_SHARE_CDXT_YJS_JBXX> findJxBySchool(String identity);

    List<T_SHARE_CDXT_YJS_JBXX> findJxBySch();

    List<T_SHARE_CDXT_YJS_JBXX> findByGrdAll();

    List<T_SHARE_CDXT_YJS_JBXX> findByGrd(@Param(value = "no")String no, @Param(value = "identity")String identity);

    List<T_SHARE_CDXT_BKS_JBXX> findByCrdAll();

    List<T_SHARE_CDXT_BKS_JBXX> findByCrd(String no);

    List<T_SHARE_CDXT_BKS_KCPK> findByJsAll();

    List<T_SHARE_CDXT_BKS_KCPK> findByJsCrid(String crid);

    List<T_SHARE_CDXT_BKS_KCPK> findByJsIdentity(String identity);

    List<T_SHARE_CDXT_BKS_KCPK> findByCC(@Param(value = "identity")String identity, @Param(value = "crid")String crid);

    List<T_SHARE_CDXT_BKS_JSPK> findByTeacher();

    List<T_SHARE_CDXT_BKS_JSPK> findByTc(String no);

    List<T_SHARE_CDXT_BKS_XSXK> findByStudent();

    List<T_SHARE_CDXT_BKS_XSXK> findByIf(@Param(value = "identity")String identity,
                                         @Param(value = "cid")String cid,
                                         @Param(value = "gid")String gid,
                                         @Param(value = "no")String no);

    List<T_SHARE_CDXT_YJS_JBXX> findJxByDorX(String school);

    List<T_SHARE_CDXT_YJS_JBXX> findJxByDDNo(String no);

}
