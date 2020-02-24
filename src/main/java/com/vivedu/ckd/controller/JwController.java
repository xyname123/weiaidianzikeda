package com.vivedu.ckd.controller;

import com.alibaba.fastjson.JSON;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.pojo.*;
import com.vivedu.ckd.service.JxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/api")
public class JwController {

    @Autowired
    private JxService jxService;

    //基础数据同步
    @PostMapping(path = "/pub/sync/school/data", params = "params",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    private response DataTb(@PathVariable(value = "params") String params, @RequestBody Map<String, Object> map) {
    log.info("============"+"/pub/sync/school/data/params");
        String type = (String) map.get("type");
        if (type.equals("grade")) {
            grade grade = (grade) map.get("datda");
            List<T_SHARE_CDXT_BKS_JBXX> JxInfo = jxService.findJxByGrade(grade.getPKey());
            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        } else if (type.equals("clazz")) {
            clazz clazz = (com.vivedu.ckd.pojo.clazz) map.get("data");
            List<T_SHARE_CDXT_BKS_JBXX> JxInfo = jxService.findJxByClazz(clazz.getPKey());
            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        } else if (type.equals("student")) {
            student student = (com.vivedu.ckd.pojo.student) map.get("data");
            List<T_SHARE_CDXT_BKS_JBXX> JxInfo = jxService.findJxByName(student.getName());
            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));

        } else if (type.equals("teacher")) {

            teacher teacher = (com.vivedu.ckd.pojo.teacher) map.get("data");

            List<T_SHARE_CDXT_BKS_JSPK> JxInfo = jxService.findJxByNo(teacher.getNo());
            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));

        } else if (type.equals("attendance")) {

            attendance attendance = (com.vivedu.ckd.pojo.attendance) map.get("data");

            return response.success(CodeMsg.SHUJU_EXCEPTION.getCode(), CodeMsg.SHUJU_EXCEPTION.getMessage(), null);
        } else {

            return response.success(CodeMsg.REQUEST_EXCEPTION.getCode(), CodeMsg.REQUEST_EXCEPTION.getMessage(), null);
        }

    }

    //应用数据删除
    @PostMapping(path = "/pub/del/school/data", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    private response dleTb(@RequestBody Map<String, Object> map) {
        log.info("============"+"/pub/sync/school/data");
        String type = (String) map.get("type");
        if (type.equals("dormitory")) {

            dormitory dormitory = (com.vivedu.ckd.pojo.dormitory) map.get("data");

            Integer JxInfo = jxService.findJxByDor(dormitory.getSchool());
            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        }
        if (type.equals("dormitorystudent")) {

            dormitorystudent dormitorystudent = (com.vivedu.ckd.pojo.dormitorystudent) map.get("data");

            Integer JxInfo = jxService.findJxByDNo(dormitorystudent.getNo());
            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        } else {
            return response.success(CodeMsg.REQUEST_EXCEPTION.getCode(), CodeMsg.REQUEST_EXCEPTION.getMessage(), null);
        }

    }


    //基础数据导出
    @PostMapping(path = "/pub/exp/school/data", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    private response expTb(@RequestBody Map<String, Object> map) {
        log.info("============"+"/pub/exp/school/data");
        String type = (String) map.get("type");
        if (type.equals("school")) {

            school school = (com.vivedu.ckd.pojo.school) map.get("data");
            if (school == null) {
                List<T_SHARE_CDXT_YJS_JBXX> JxInfo = jxService.findJxBySch();
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
            }
            List<T_SHARE_CDXT_YJS_JBXX> JxInfo = jxService.findJxBySchool(school.getIdentity());

            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));

        } else if (type.equals("grade")) {

            grade grade = (com.vivedu.ckd.pojo.grade) map.get("data");
            if (grade == null) {
                List<T_SHARE_CDXT_YJS_JBXX> JxInfo = jxService.findByGrdAll();
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
            }
            List<T_SHARE_CDXT_YJS_JBXX> JxInfo = jxService.findByGrd(grade.getNo(), grade.getIdentity());
            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        } else if (type.equals("clazz")) {

            clazz clazz = (com.vivedu.ckd.pojo.clazz) map.get("data");
            if (clazz == null) {
                List<T_SHARE_CDXT_BKS_JBXX> JxInfo = jxService.findByCrdAll();
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
            }
            if (clazz.getNo() == null) {
                return response.success(CodeMsg.REQUEST_EXCEPTION.getCode(), CodeMsg.REQUEST_EXCEPTION.getMessage(), null);
            }
            List<T_SHARE_CDXT_BKS_JBXX> JxInfo = jxService.findByCrd(clazz.getNo());
            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        }


        if (type.equals("classroom")) {

            classroom classroom = (com.vivedu.ckd.pojo.classroom) map.get("data");

            if (classroom == null) {
                List<T_SHARE_CDXT_BKS_KCPK> JxInfo = jxService.findByJsAll();
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
            }
            if (classroom.getCrid() != null && classroom.getIdentity() == null) {

                List<T_SHARE_CDXT_BKS_KCPK> JxInfo = jxService.findByJsCrid(classroom.getCrid());
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
            }
            if (classroom.getIdentity() != null && classroom.getCrid() == null) {

                List<T_SHARE_CDXT_BKS_KCPK> JxInfo = jxService.findByJsIdentity(classroom.getIdentity());
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
            }
            if (classroom.getCrid() != null && classroom.getIdentity() != null) {
                List<T_SHARE_CDXT_BKS_KCPK> JxInfo = jxService.findByCC(classroom.getIdentity(), classroom.getCrid());
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));

            }

        }

        if (type.equals("teacher")) {

            teacher teacher = (com.vivedu.ckd.pojo.teacher) map.get("data");

            if (teacher == null) {
                List<T_SHARE_CDXT_BKS_JSPK> JxInfo = jxService.findByTeacher();
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
            }

            List<T_SHARE_CDXT_BKS_JSPK> JxInfo = jxService.findByTc(teacher.getNo());
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        }


        if (type.equals("student")) {

            student student = (com.vivedu.ckd.pojo.student) map.get("data");
            if (student == null) {
                List<T_SHARE_CDXT_BKS_XSXK> JxInfo = jxService.findByStudent();
                return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
            }

            List<T_SHARE_CDXT_BKS_XSXK> JxInfo = jxService.findByIf(student.getIdentity(), student.getCid(), student.getGid(), student.getNo());
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        }
        return response.success(CodeMsg.SERVER_EXCEPTION.getCode(), CodeMsg.SERVER_EXCEPTION.getMessage(), "重试");
    }

    //通知第三方更新数据接口定义
    @PostMapping(path = "/pub/receive", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    private response SFTb(@RequestBody Map<String, Object> map) {
        log.info("============"+"/pub/receive");
        String type = (String) map.get("type");
        if (type.equals("school")) {

            Object identity = map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), "");

        }
        if (type.equals("grade")) {

            Object gid = map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), "");

        }
        if (type.equals("clazz")) {

            Object cid = map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), "");
        }
        if (type.equals("student")) {

            Object sid = map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), "");
        }
        if (type.equals("teacher")) {

            Object account = map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), "");
        }


        return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), "");

    }

    //应用数据
    @PostMapping(path = "/pub/sync/school/data", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    private response DataYb(@RequestBody Map<String, Object> map) {
        log.info("============"+"/pub/sync/school/data");
        String type = (String) map.get("type");
        if (type.equals("dormitory")) {

            dormitory dormitory = (com.vivedu.ckd.pojo.dormitory) map.get("data");

            List<T_SHARE_CDXT_YJS_JBXX> JxInfo = jxService.findJxByDorX(dormitory.getSchool());

            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        }
        if (type.equals("dormitorystudent")) {

            dormitorystudent dormitorystudent = (com.vivedu.ckd.pojo.dormitorystudent) map.get("data");

            List<T_SHARE_CDXT_YJS_JBXX> JxInfo = jxService.findJxByDDNo(dormitorystudent.getNo());

            if (JxInfo == null) {
                return response.success(CodeMsg.PARARM_EXCEPTION.getCode(), CodeMsg.PARARM_EXCEPTION.getMessage(), null);
            }
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(), JSON.toJSON(JxInfo));
        }
        if (type.equals("semester")) {

            semester semester = (com.vivedu.ckd.pojo.semester) map.get("data");

            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(),"暂无数据");
        }

        if (type.equals("classroom")) {

            classroom classroom = (com.vivedu.ckd.pojo.classroom) map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(),"暂无数据");
        }

        if (type.equals("course")) {

            course course = (com.vivedu.ckd.pojo.course) map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(),"暂无数据");
        }

        if (type.equals("courseschedule")) {

            courseschedule  courseschedule = (com.vivedu.ckd.pojo. courseschedule) map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(),"暂无数据");
        }
        if (type.equals("timetable")) {

            timetable  timetable = (com.vivedu.ckd.pojo. timetable) map.get("data");
            return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(),"暂无数据");
        }
        return response.success(CodeMsg.BIND_SUCESS.getCode(), CodeMsg.BIND_SUCESS.getMessage(),"暂无数据");

    }


}



