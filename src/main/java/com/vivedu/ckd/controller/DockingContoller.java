package com.vivedu.ckd.controller;

import com.alibaba.fastjson.JSON;
import com.vivedu.ckd.service.DockingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/api")
public class DockingContoller {

    @Autowired
    private DockingService dockingService;

    @GetMapping(path = "/courseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课程信息")
    public String courseInfo(
            @RequestParam(required = false, value = "courseid") @ApiParam(value = "课程编号") String courseId,
            @RequestParam(required = false, value = "keywords") @ApiParam(value = "课程关键字") String keywords,
            @RequestParam(required = false, value = "page", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = false, value = "sort", defaultValue = "date") @ApiParam(value = "排序") String sort,
            @RequestParam(required = false, value = "userId") @ApiParam(value = "学工编号") String userId) {
        log.info("进入/courseList方法");
     /*   if (StringUtils.isNotEmpty(userId)) {
            //根据学工号查询拥有的课程列表；
            log.info("根据学工号查询拥有的课程列表");
            HashMap<String, Object> TotalMap = dockingService.findCourseByUserId(userId, sort, page, size);
            return JSON.toJSONString(TotalMap);
        } else if (StringUtils.isEmpty(userId) && StringUtils.isNotEmpty(keywords)) {
            //根据课程关键字查询课程列表；
            log.info("根据课程关键字查询课程列表");
            HashMap<String, Object> TotalMap = dockingService.findCourseByKey(keywords, page, size, sort);
            return JSON.toJSONString(TotalMap);

        } else if (StringUtils.isNotEmpty(courseId)) {
            //只查询单门课程信息；
            log.info("查询单门课程");
            HashMap<String, Object> TotalMap = dockingService.findCourse(courseId, page, size, sort);
            return JSON.toJSONString(TotalMap);
        } else {*/
            //查询所有课程列表；
            log.info("查询所有课程列表");
            //todo
          dockingService.findAllCourse(page, size, sort);
            return JSON.toJSONString(null);

        }

/*

    //按照用户id查询最新学习的课程（数组，返回最近学习的10门课程）。
    @GetMapping(path = "/learningList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "最近学习的课程")
    public String courseInfoByUserId(
            @RequestParam(required = false, value = "page", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = true, value = "userId") @ApiParam(value = "用户编号") String userId) {
        log.info("进入/learningList方法");


        //HashMap<String, Object> TotalMap = dockingService.findCourseByUserIdLearn(userId, page, size);

        return JSON.toJSONString(TotalMap);
    }

    @GetMapping(path = "/markedList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "收藏的课程")
    public String courseInfoByMarked(
            @RequestParam(required = false, value = "page", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = true, value = "userId") @ApiParam(value = "用户编号") String userId) {
        log.info("进入/markedList方法");
        HashMap<String, Object> TotalMap = dockingService.courseInfoByMarked(userId, page, size);

        return JSON.toJSONString(TotalMap);
    }

    @GetMapping(path = "/openCourseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "我开的课程（教师）")
    public String courseInfoByTeacher(
            @RequestParam(required = false, value = "page", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = true, value = "userId") @ApiParam(value = "教师编号") String userId) {
        log.info("进入/openCourseList方法");
        HashMap<String, Object> TotalMap = dockingService.findCourseByUserIdPage(userId, page, size);
        //film
        return JSON.toJSONString(TotalMap);
    }

    @GetMapping(path = "/teachingData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "教学情况统计")
    public String TeacherData(
            @RequestParam(required = false, value = "courseId") @ApiParam(value = "课程编号") String courseId,
            @RequestParam(required = false, value = "departmentId") @ApiParam(value = "院系") String departmentId,
            @RequestParam(required = false, value = "page", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = false, value = "sort", defaultValue = "date") @ApiParam(value = "排序") String sort,
            @RequestParam(required = false, value = "subjecteId") @ApiParam(value = "学科") String subjecteId,
            @RequestParam(required = false, value = "term") @ApiParam(value = "学期") String term,
            @RequestParam(required = false, value = "userId") @ApiParam(value = "教师Id") String userId) {
        log.info("进入/teachingData方法");
        HashMap<String, Object> TotalMap = dockingService.teachingData(courseId, departmentId, page, size, sort, subjecteId, term, userId);

        return JSON.toJSONString(TotalMap);
    }
*/


}

