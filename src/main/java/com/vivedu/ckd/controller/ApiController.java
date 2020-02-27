package com.vivedu.ckd.controller;

import com.alibaba.fastjson.JSON;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.service.ClassroomService;
import com.vivedu.ckd.service.CourseInfoService;
import com.vivedu.ckd.service.CourseStatisticsService;
import com.vivedu.ckd.service.InfoInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/ckd")
public class ApiController {
    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private CourseStatisticsService courseStatisticsService;
    @Autowired
    private InfoInfoService infoInfoService;
    @Autowired
    private ClassroomService classroomService;

    @GetMapping(path = "/courseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课程信息")
    public String courseInfo(
            @RequestParam(required = false, value = "id") @ApiParam(value = "课程编号") Integer id,
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
            @RequestParam(required = false, value = "courseName") @ApiParam(value = "课程关键字") String courseName,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize,
            @RequestParam(required = false, value = "sort", defaultValue = "date") @ApiParam(value = "排序") String sort,
            @RequestParam(required = false, value = "userId") @ApiParam(value = "学工编号") String userId) {
        log.info("进入/courseList方法");
        if (StringUtils.isNotEmpty(userId)) {
            //根据学工号查询拥有的课程列表；
            pageNum = (pageNum - 1) * pageSize;
            List<CourseInfo> courseInfo = courseInfoService.findCourseByUserId(userId, sort, pageNum, pageSize);
            int num =courseInfoService.findCourseByUserIdNum(userId);
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total",num);
            hashMapMap.put("data", courseInfo);
            return JSON.toJSONString(hashMapMap);

        } else if (StringUtils.isEmpty(userId) && StringUtils.isNotEmpty(courseName)) {
            //根据课程关键字查询课程列表；
            log.info("courseName-----------"+courseName);
            pageNum = (pageNum - 1) * pageSize;
            List<CourseInfo> courseInfo = courseInfoService.findCourseByKey(courseName, pageNum, pageSize);
            int numkey =courseInfoService.findCourseByKeyNum(courseName);
            log.info("numkey----"+numkey);
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total", numkey);
            hashMapMap.put("data", courseInfo);
            return JSON.toJSONString(hashMapMap);

        } /*else if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(keywords)) {
            //查询所有课程列表；
            List<CourseInfo> courseInfo = courseInfoService.findAllCourse();
            HashMap<String,Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total", courseInfo.size());
            hashMapMap.put("data",courseInfo);
            return JSON.toJSONString(hashMapMap);
        } */ else if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(courseName) && id !=null) {
            //只查询单门课程信息；
            pageNum = (pageNum - 1) * pageSize;
            List<CourseInfo> courseInfo = courseInfoService.findCourse(id, pageNum, pageSize);
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total", courseInfo.size());
            hashMapMap.put("data", courseInfo);
            return JSON.toJSONString(hashMapMap);
        } else {
            //查询所有课程列表；
            pageNum = (pageNum - 1) * pageSize;
            List<CourseInfo> courseInfo = courseInfoService.findAllCourse(pageNum, pageSize);
            int allnum = courseInfoService.findAllCourseNum();
            HashMap<String, Object> hashMapMap = new HashMap<>();
            log.info("allnum----"+allnum);
            hashMapMap.put("total", allnum);
            hashMapMap.put("data", courseInfo);
            return JSON.toJSONString(hashMapMap);
        }
    }

    //按照用户id查询最新学习的课程（数组，返回最近学习的10门课程）。
    @GetMapping(path = "/learningList", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "最近学习的课程")
    public String courseInfoByUserId(
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
           /* @RequestParam(required = false, value = "page",defaultValue = "1") @ApiParam(value = "页数")Integer page,
            @RequestParam(required = false, value = "size",defaultValue = "10") @ApiParam(value = "页大小") Integer size,*/
            @RequestParam(required = true, value = "userId") @ApiParam(value = "用户编号") String id) {
        log.info("进入/learningList方法");
        List<CourseInfo> courseInfo = courseInfoService.findCourseByUserIdLearn(id);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }

    @GetMapping(path = "/markedList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "收藏的课程")
    public String courseInfoByMarked(
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
            @RequestParam(required = true, value = "userId") @ApiParam(value = "用户编号") String courseId) {
        log.info("进入/markedList方法");
        List<CourseInfo> courseInfo = courseInfoService.courseInfoByMarked(courseId);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }

    @GetMapping(path = "/openCourseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "我开的课程（教师）")
    public String courseInfoByTeacher(
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = true, value = "userId") @ApiParam(value = "用户编号") String userId) {
        log.info("进入/openCourseList方法");
        List<CourseInfo> courseInfo = courseInfoService.findCourseByUserIdPage(userId, page, size);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }

    @GetMapping(path = "/teachingData", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "教学情况统计")
    public String TeacherData(
            @RequestParam(required = false, value = "courseId") @ApiParam(value = "课程编号") String courseId,
            @RequestParam(required = false, value = "departmentId") @ApiParam(value = "院系") String departmentId,
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = false, value = "sort", defaultValue = "date") @ApiParam(value = "排序") String sort,
            @RequestParam(required = false, value = "subjecteId") @ApiParam(value = "学科") Integer subjecteId,
            @RequestParam(required = false, value = "term") @ApiParam(value = "学期") Integer term,
            @RequestParam(required = false, value = "userId") @ApiParam(value = "教师Id") String userId) {
        log.info("进入/teachingData方法");
        List<CourseStatistics> CourseStatistics = courseStatisticsService.teachingData(
                courseId, departmentId, page, size, sort, subjecteId, term, userId);
        JSONArray CourseStatisticsJson = JSONArray.fromObject(CourseStatistics);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", CourseStatistics.size());
        hashMapMap.put("data", CourseStatistics);
        return JSON.toJSONString(hashMapMap);
    }

    @GetMapping(path = "/news/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新闻信息列表）")
    public String newsList(
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/news/list方法");
        List<InfoInfo> infoInfo = infoInfoService.findNewsList(pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", infoInfo.size());
        hashMapMap.put("data", infoInfo);
        return JSON.toJSONString(infoInfo);
    }

    @GetMapping(path = "/news/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "新闻信息详情）")
    public String newsDetail(
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
            @RequestParam(required = true, value = "Id") @ApiParam(value = "新闻Id") Integer Id

    ) {
        log.info("进入/news/detail方法");
        List<InfoInfo> infoInfo = infoInfoService.findNewsDetail(Id);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", infoInfo.size());
        hashMapMap.put("data", infoInfo);
        return JSON.toJSONString(infoInfo);
    }

    @GetMapping(path = "/course/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课件详情）")
    public String newsDetail(
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
            @RequestParam(required = true, value = "id") @ApiParam(value = "课程Id") Integer courseId,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize

    ) {
        log.info("进入/course/detail方法");
        List<CourseInfo> courseInfo = courseInfoService.findCourseDatal(courseId, pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }

    /**
     * 首页
     * 推荐课程
     */
    @GetMapping(path = "/course/rec", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课件详情）")
    public String newsRec(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/rec");
        List<CourseInfo> courseInfo = courseInfoService.findRec(pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }


    /***
     * 热门推荐课程
     */
    @GetMapping(path = "/course/recIndex", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "recIndex）")
    public String recIndex(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/recIndex");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.recIndex(pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }


    /***
     * 最新课程
     */
    @GetMapping(path = "/course/quick", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "最新课程）")
    public String recquick(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/quick");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.findrquick(pageNum, pageSize);
        log.info("courseInfo---"+courseInfo);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }

    /***
     * 最新热门课程
     */
    @GetMapping(path = "/course/quickRec", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "最新热门课程）")
    public String quickRec(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/quickRec");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.findquickRec(pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }

    //最新资源
    @GetMapping(path = "/course/quickZiyuan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "quickZiyuan）")
    public String quickZiyuan(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/quickZiyuan");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.findquickZiyuan(pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }


    //测试用
    @PostMapping(path = "/ceshi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课件详情")
    public response newsDetail() {

        List<T_SHARE_CDXT_YJS_JBXX> courseInfo = courseInfoService.findT();
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);

        return response.success(CodeMsg.CODE_EXCEPTION.getCode(), CodeMsg.CODE_EXCEPTION.getMessage(), JSON.toJSON(courseInfo));
    }

    /**
     * 增加次数
     *
     * @param type
     * @return
     */
    @GetMapping(path = "/statisticalCount")
    public DemonstrationResponse statisticalCount(
            @RequestParam(value = "type") int type) {
        return courseInfoService.statisticalCount(type);
    }

    /**
     * 获取次数
     *
     * @param
     * @return
     */
    @GetMapping(path = "/getCountList")
    public DemonstrationResponse getCountList() {
        return courseInfoService.getCountList();
    }


    @GetMapping(path = "/filmTop", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "filmTop")
    public String courseInfoByfilm(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer size
    ) {
        log.info("进入/filmTop");
        String film = "film";
        List<CourseInfo> courseInfo = courseInfoService.findCourseByfilmTop(film, page, size);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }

    /***
     *  智慧教室
     */
    @GetMapping(path = "/classroom/detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "classroom详情）")
    public String classroom(
            @RequestParam(required = false, value = "shooolArea") @ApiParam(value = "校区") String shooolArea
            ,@RequestParam(required = false, value = "maxXin") @ApiParam(value = "屏号") Integer maxXin
            , @RequestParam(required = false, value = "dianNaoXin") @ApiParam(value = "电脑型号") Integer dianNaoXin
            , @RequestParam(required = false, value = "keTangHuXin") @ApiParam(value = "课堂互动型号") Integer keTangHuXin
            , @RequestParam(required = false, value = "luBoXin") @ApiParam(value = "录播互动型号") Integer luBoXin
            , @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/classroom/detail方法");
        pageNum = (pageNum - 1) * pageSize;
        if (StringUtils.isEmpty(shooolArea) && maxXin == null && dianNaoXin == null && dianNaoXin == null && keTangHuXin == null && luBoXin == null) {
            List<Classroom> classroom = classroomService.findClassroomE(pageNum,pageSize);
            int kk = classroomService.findClassroomJ();
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total",kk);
            hashMapMap.put("data", classroom);
            return JSON.toJSONString(hashMapMap);

        } else {
            List<Classroom> classroom = classroomService.findClassroom(shooolArea,maxXin,dianNaoXin, keTangHuXin,luBoXin,pageNum,pageSize);
           int kg =classroomService.findClassroomK(shooolArea,maxXin,dianNaoXin,keTangHuXin,luBoXin);
            log.info("classroom" + classroom.toString());
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total",kg);
            hashMapMap.put("data", classroom);
            return JSON.toJSONString(hashMapMap);
        }
        // List<Classroom> classroomtol = classroomService.findClassroomTotal(shooolArea,dianNaoXin, keTangHuXin,luBoXin,maxXin);
    }


    @GetMapping(path = "/getRecommendCourseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取推荐课程信息列表")
    public DemonstrationResponse getRecommendCourseList(
            @RequestParam(required = false, value = "isRec") @ApiParam(value = "是否推荐（0不推荐，1推荐）") Integer isRec,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "source") @ApiParam(value = "source") String source,
            @RequestParam(required = false, value = "courseName") @ApiParam(value = "courseName") String courseName,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize) {
        if (StringUtils.isEmpty(source) && isRec == null && StringUtils.isEmpty(courseName)) {


            return  courseInfoService.getRecommendCourseListL(pageNum,pageSize);

        }

        return courseInfoService.getRecommendCourseList(isRec,pageNum,pageSize,source,courseName);
    }


    @GetMapping(path = "/updateRecommendCourse", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改推荐课程")
    public DemonstrationResponse updateRecommendCourse(
            @RequestParam(required = false, value = "isRec") @ApiParam(value = "是否推荐（0不推荐，1推荐）") Integer isRec,
            @RequestParam(value = "id") @ApiParam(value = "id") Integer id) {
        return courseInfoService.updateRecommendCourse(isRec, id);
    }
/***
 *  首页推荐选择
 * */
    @GetMapping(path = "/topState", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "topState")
    public String courseInfotopState(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = false, value = "state") @ApiParam(value = "页大小") Integer state
    ) {
        log.info("进入/topState");
        List<CourseInfo> courseInfo = courseInfoService.findCoursetopState(state, page, size);
        int courseInfoT = courseInfoService.findCoursetopStateT(state);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfoT);
        hashMapMap.put("data", courseInfo);
        return JSON.toJSONString(hashMapMap);
    }
/**
 * 2020/2/26
 * */
    @GetMapping(path = "/addStudyDuration", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加学习时长")
    public DemonstrationResponse addStudyDuration(
            @RequestParam(required = true, value = "studentID") String studentID,
            @RequestParam(required = true, value = "time") @ApiParam(value = "时长") Integer time) {
        return courseInfoService.addStudyDuration(studentID,time);
    }

    @GetMapping(path = "/addLessonsLearned", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加学习课程")
    public DemonstrationResponse addLessonsLearned(
            @RequestParam(required = true, value = "studentID") BigInteger studentID,
            @RequestParam(required = true, value = "id") @ApiParam(value = "课程号") Integer id) {
        return courseInfoService.addLessonsLearned(studentID,id);
    }

    @GetMapping(path = "/getPersonalCenterInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取个人中心信息")
    public DemonstrationResponse getPersonalCenterInfo (
            @RequestParam(required = true, value = "userId") String userId) throws Exception {
        return courseInfoService.getPersonalCenterInfo(userId);
    }

}