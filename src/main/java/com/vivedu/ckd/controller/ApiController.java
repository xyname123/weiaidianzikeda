package com.vivedu.ckd.controller;

import com.alibaba.fastjson.JSON;
import com.vivedu.ckd.dao.CategoryMapper;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.pojo.ZanshiResponse;
import com.vivedu.ckd.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MarkedCourseService markedCourseService;

    @GetMapping(path = "/courseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课程信息")
    public ZanshiResponse courseInfo(
            @RequestParam(required = false, value = "id") @ApiParam(value = "课程编号") Integer id,
            @RequestParam(required = false, value = "courseName") @ApiParam(value = "课程关键字") String courseName,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize,
            @RequestParam(required = false, value = "sort", defaultValue = "date") @ApiParam(value = "排序") String sort,
            @RequestParam(required = false, value = "userId") @ApiParam(value = "学工编号") String userId,
            @RequestParam(required = false, value = "state") @ApiParam(value = "课程状态") Integer state,
            @RequestParam(required = false, value = "courseTypeCode") @ApiParam(value = "课程分类编码") Integer courseTypeCode) {
        log.info("进入/courseList方法");
        if (StringUtils.isNotEmpty(userId)) {
            //根据学工号查询拥有的课程列表；
            pageNum = (pageNum - 1) * pageSize;
            List<CourseInfo> courseInfo = courseInfoService.findCourseByUserId(userId, sort, pageNum, pageSize);
            //对学科分类进行处理
            for (CourseInfo info : courseInfo) {
                if (info.getSubjectcategory1() != null) {
                    info.setCourseTypeName(info.getSubjectcategory1());
                }
            }
            int num = courseInfoService.findCourseByUserIdNum(userId);
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total", num);
            hashMapMap.put("data", courseInfo);
            return new ZanshiResponse(0, "请求成功", num, courseInfo);

        } else if (StringUtils.isEmpty(userId) && StringUtils.isNotEmpty(courseName)) {
            //根据课程关键字查询课程列表；
            pageNum = (pageNum - 1) * pageSize;
            List<CourseInfo> courseInfo = courseInfoService.findCourseByKey(courseName, pageNum, pageSize, state, courseTypeCode);
            for (CourseInfo info : courseInfo) {
                if (info.getSubjectcategory1() != null) {
                    info.setCourseTypeName(info.getSubjectcategory1());
                }
            }
            int numkey = courseInfoService.findCourseByKeyNum(courseName, state, courseTypeCode);
            //热门关键词 hotkey相关

            hotKey hotNum = courseInfoService.findKeyCourseNameAndHotNum(courseName);

            if (hotNum != null && hotNum.getCourseKeywordsNum() > 0) {
                courseInfoService.updateKeyCourseNameAndHotNum(courseName, hotNum.getCourseKeywordsNum() + 1);
            } else {
                courseInfoService.addKeyCourseNameAndHotNum(courseName);
            }

            return new ZanshiResponse(0, "请求成功", numkey, courseInfo);

        } else if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(courseName) && id != null) {
            //只查询单门课程信息；
            pageNum = (pageNum - 1) * pageSize;
            List<CourseInfo> courseInfo = courseInfoService.findCourse(id, pageNum, pageSize);
            for (CourseInfo info : courseInfo) {
                if (info.getSubjectcategory1() != null) {
                    info.setCourseTypeName(info.getSubjectcategory1());
                }
            }
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total", courseInfo.size());
            hashMapMap.put("data", courseInfo);
            return new ZanshiResponse(0, "请求成功", 1, courseInfo);
        } else {
            //查询所有课程列表；
            pageNum = (pageNum - 1) * pageSize;
            List<CourseInfo> courseInfo = courseInfoService.findAllCourse(pageNum, pageSize, state, courseTypeCode);
            for (CourseInfo info : courseInfo) {
                if (info.getSubjectcategory1() != null) {
                    info.setCourseTypeName(info.getSubjectcategory1());
                }
            }
            int allnum = courseInfoService.findAllCourseNum(state, courseTypeCode);
            return new ZanshiResponse(0, "请求成功", allnum, courseInfo);
        }
    }

    /**
     * 新加 2020/3/10
     */
    @GetMapping(path = "/myCourseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "我浏览的课程信息")
    public ZanshiResponse courseInfoByUserId(
            @RequestParam(required = true, value = "userid") @ApiParam(value = "用户编号") String userid,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        log.info("进入/myCourseList");
        List<BrowseCourse> brow = courseInfoService.findtime(userid);
        for (BrowseCourse browseCourse : brow) {
            courseInfoService.catTime(browseCourse.getCatTime(),browseCourse.getCourseid());
        }
        List<CourseInfo> courseInfo = courseInfoService.findCourseByUserIdLearn(userid,pageNum,pageSize);
        List<BrowseCourse> num = courseInfoService.findCourseByUserIdLearnNum(userid);
        return new ZanshiResponse(0, "请求成功", num.size(), courseInfo);
    }
    /**
     * 3/25
     * */
    @GetMapping(path = "/markedList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "我收藏的课程")
    public ZanshiResponse ByMarked(
            @RequestParam(required = true, value = "userid") @ApiParam(value = "用户编号") String userid,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize) {
        pageNum = (pageNum - 1) * pageSize;
        List<MarkedCourse> MarkedCourses = markedCourseService.findMarkedCourseK(userid);
        for (MarkedCourse course : MarkedCourses) {
            markedCourseService.catTimecourse(course.getCatTime(),course.getCourseid());
        }
        List<MarkedCourse> courseInfo = markedCourseService.courseInfoByMarked(userid,pageNum,pageSize);
        int num = markedCourseService.courseInfoByMarkedNum(userid);
        return new ZanshiResponse(0, "请求成功", num, courseInfo);
    }

   /**
    * 3/24
    * */
    @GetMapping(path = "/marked", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "是否收藏")
    public ZanshiResponse Marked(
            @RequestParam(required = true,value = "userid") @ApiParam(value = "用户编号") String userid,
            @RequestParam(required = true,value = "courseid") @ApiParam(value = "课程编号") Integer courseid,
            @RequestParam(required = true,value = "state") @ApiParam(value = "状态") Integer state) {
        log.info("进入/marked方法");
       MarkedCourse markedcourse = markedCourseService.findMarkedCourse(userid,state,courseid);
        if (markedcourse != null) {
            int marked = markedCourseService.marked(userid,state,courseid);
            return new ZanshiResponse(0, "请求成功", 1, null);
        } else {
            int marked = markedCourseService.addMarked(userid,state,courseid);
            return new ZanshiResponse(0, "请求成功", 1, null);
        }

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
    public  List<InfoInfo> newsList(
            @RequestParam(required = false, value = "isRec") @ApiParam(value = "isRec") Integer isRec,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/news/list方法");
        pageNum = (pageNum - 1) * pageSize;
        List<InfoInfo> infoInfo = infoInfoService.findNewsList(pageNum,pageSize,isRec);
        int num = infoInfoService.findCount(isRec);
        return  infoInfo;
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
    @ApiOperation(value = "课件详情")
    public String newsDetail(
            // @RequestParam(required = true, value = "enc") @ApiParam(value = "签名") String enc,
            @RequestParam(required = true, value = "id") @ApiParam(value = "课程Id") Integer id,
            @RequestParam(required = false, value = "userid") @ApiParam(value = "课程Id") Integer userid,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/detail方法");
        HashMap<String, Object> hashMapMap = new HashMap<>();
        CourseInfo courseInfo = courseInfoService.findCourseDatal(id);
        CourseInfoPojo courseInfoPojo = new CourseInfoPojo();
        BeanUtils.copyProperties(courseInfo, courseInfoPojo);

        //对时间的处理
        String format = "yyyy-MM-dd";
        String format1;
        String format2;
        if (!courseInfo.getSource().equals("中国大学MOOC") && courseInfo.getStart() != null && StringUtils.isNotEmpty(courseInfo.getStart())) {
            if (courseInfo.getStart() != null && courseInfo.getStart() != "") {
                Long start = Long.valueOf(Long.parseLong(courseInfo.getStart()));
                Date date = new Date(start);
                if (start != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    format1 = sdf.format(date);
                    courseInfo.setStart(format1);

                }
            }
            if (courseInfo.getEnd() != null && courseInfo.getEnd() != "" && !courseInfo.getEnd().equals("0")) {
                Long end = Long.valueOf(Long.parseLong(courseInfo.getEnd()));
                Date date2 = new Date(end);
                if (end != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat(format);
                    format2 = sdf.format(date2);
                    courseInfo.setEnd(format2);
                }
            }
            if (courseInfo.getEnd().equals(0)) {
                String f = "";
                courseInfo.setEnd(f);
            }


        }
        if (!courseInfo.getSource().equals("中国大学MOOC") && courseInfo.getStart() == null || StringUtils.isEmpty(courseInfo.getStart())) {
            String timeData = "N/A";
            courseInfoPojo.setTimeData(timeData);
        } else {
            String timeData = courseInfo.getStart() + " 至 " + courseInfo.getEnd();
            if (courseInfo.getEnd().equals("0")) {
                timeData = "起于" + courseInfo.getStart();
            }
            courseInfoPojo.setTimeData(timeData);
        }

        //添加学习次数
        if (courseInfo.getStudyCount() == null) {
            courseInfoService.updateStudyCount(id, 1);

        } else {
            Integer studyCount = courseInfo.getStudyCount();
            courseInfoService.updateStudyCount(id, studyCount + 1);
        }
        //访问人数(学习次数的调整)
        courseInfoService.add(id);
        //新增访问记录的查询
        //1.查询本周
        List<CatNumber> week =courseInfoService.week(id);

        courseInfoPojo.setWeek(week.size());
        //2.查询本月
        List<CatNumber> Month =courseInfoService.Month(id);
        courseInfoPojo.setMonth(Month.size());
        //3.查询最近三个月
        List<CatNumber> threeMonth =courseInfoService.thridMonth(id);
        courseInfoPojo.setThreeMonth(threeMonth.size());
        //4.查询最近6个月
        List<CatNumber> sixMonth =courseInfoService.sixMonth(id);
        courseInfoPojo.setSixMonth(sixMonth.size());
        //5.查询最近一年
        List<CatNumber> oneyear =courseInfoService.oneYear(id);
        courseInfoPojo.setOneYear(oneyear.size());

        //中国大学慕课
        if (courseInfoPojo.getTermType() != null) {
            courseInfoPojo.setSubjectcategory1(courseInfoPojo.getTermType());
        }
        //metel的详情展示处理
        if (courseInfoPojo.getSource().equals("MeTel")) {
            courseInfoPojo.setSummary(courseInfoPojo.getProfileEn());
        }

        //对userid的处理与browsecourse关联   id和courseid的关联
        if (userid!=null) {
            courseInfoService.insertBrowse(userid,id);
        }
        //对收藏的管理 1为收藏
        if (userid!=null){
            MarkedCourse markedCourse = markedCourseService.findMarkedGm(userid,id);
            if (markedCourse==null||markedCourse.getState()==null){
                courseInfoPojo.setOk(0);

            }else {
                courseInfoPojo.setOk(markedCourse.getState());
            }
        }

        hashMapMap.put("data", courseInfoPojo);
        return JSON.toJSONString(hashMapMap);
    }

    /**
     * 首页
     * 推荐课程
     */
    @GetMapping(path = "/course/rec", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "推荐课程）")
    public ZanshiResponse newsRec(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/rec");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.findRec(pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return new ZanshiResponse(0, "请求成功", courseInfo.size(), courseInfo);
    }


    /***
     * 热门推荐课程
     */
    @GetMapping(path = "/course/recIndex", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "recIndex")
    public ZanshiResponse recIndex(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/recIndex");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.recIndex(pageNum, pageSize);

        ArrayList<CourseInfoPojo> CourseInfoPojoList = new ArrayList<>();
        for (CourseInfo info : courseInfo) {
            CourseInfoPojo courseInfoPojo = new CourseInfoPojo();
            BeanUtils.copyProperties(info, courseInfoPojo);

            //对时间的处理
            String format = "yyyy-MM-dd";
            String format1;
            String format2;
            if (!info.getSource().equals("中国大学MOOC") && info.getStart() != null && StringUtils.isNotEmpty(info.getStart())) {
                if (info.getStart() != null && info.getStart() != "") {
                    Long start = Long.valueOf(Long.parseLong(info.getStart()));
                    Date date = new Date(start);
                    if (start != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        format1 = sdf.format(date);
                        info.setStart(format1);

                    }
                }
                if (info.getEnd() != null && info.getEnd() != "" && !info.getEnd().equals("0")) {
                    Long end = Long.valueOf(Long.parseLong(info.getEnd()));
                    Date date2 = new Date(end);
                    if (end != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        format2 = sdf.format(date2);
                        info.setEnd(format2);
                    }
                }
                if (info.getEnd().equals(0)) {
                    String f = "";
                    info.setEnd(f);
                }

            }
            if (!info.getSource().equals("中国大学MOOC") && info.getStart() == null || StringUtils.isEmpty(info.getStart())) {
                String timeData = "N/A";
                courseInfoPojo.setTimeData(timeData);
                CourseInfoPojoList.add(courseInfoPojo);
            } else {
                String timeData = info.getStart() + " 至 " + info.getEnd();
                if (info.getEnd().equals("0")) {
                    timeData = "起于" + info.getStart();
                }
                courseInfoPojo.setTimeData(timeData);
                CourseInfoPojoList.add(courseInfoPojo);
            }

        }

        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", CourseInfoPojoList);
        return new ZanshiResponse(0, "请求成功", courseInfo.size(), CourseInfoPojoList);
    }


    /***
     * 最新课程
     */
    @GetMapping(path = "/course/quick", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "最新课程）")
    public ZanshiResponse recquick(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/quick");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.findrquick(pageNum, pageSize);
        log.info("courseInfo---" + courseInfo);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return new ZanshiResponse(0, "请求成功", courseInfo.size(), courseInfo);
    }

    /***
     * 最新热门课程
     */
    @GetMapping(path = "/course/quickRec", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "最新热门课程）")
    public ZanshiResponse quickRec(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/quickRec");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.findquickRec(pageNum, pageSize);
        //log.info("course"+courseInfo);
        ArrayList<CourseInfoPojo> CourseInfoPojoList = new ArrayList<>();
        for (CourseInfo info : courseInfo) {
            CourseInfoPojo courseInfoPojo = new CourseInfoPojo();
            BeanUtils.copyProperties(info, courseInfoPojo);


            //对时间的处理
            String format = "yyyy-MM-dd";
            String format1;
            String format2;
            if (!info.getSource().equals("中国大学MOOC") && info.getStart() != null && StringUtils.isNotEmpty(info.getStart())) {
                if (info.getStart() != null && info.getStart() != "") {
                    Long start = Long.valueOf(Long.parseLong(info.getStart()));
                    Date date = new Date(start);
                    if (start != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        format1 = sdf.format(date);
                        info.setStart(format1);

                    }
                }
                if (info.getEnd() != null && info.getEnd() != "" && !info.getEnd().equals("0")) {
                    Long end = Long.valueOf(Long.parseLong(info.getEnd()));
                    Date date2 = new Date(end);
                    if (end != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        format2 = sdf.format(date2);
                        info.setEnd(format2);
                    }
                }
                if (info.getEnd().equals(0)) {
                    String f = "";
                    info.setEnd(f);
                }

            }
            if (!info.getSource().equals("中国大学MOOC") && info.getStart() == null || StringUtils.isEmpty(info.getStart())) {
                String timeData = "N/A";
                courseInfoPojo.setTimeData(timeData);
                CourseInfoPojoList.add(courseInfoPojo);

            } else {
                String timeData = info.getStart() + " 至 " + info.getEnd();
                if (info.getEnd().equals("0")) {
                    timeData = "起于" + info.getStart();
                }
                courseInfoPojo.setTimeData(timeData);
                CourseInfoPojoList.add(courseInfoPojo);

            }

        }

        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", CourseInfoPojoList);
        return new ZanshiResponse(0, "请求成功", courseInfo.size(), CourseInfoPojoList);
    }

    //最新资源
    @GetMapping(path = "/course/quickZiyuan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "quickZiyuan）")
    public ZanshiResponse quickZiyuan(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/course/quickZiyuan");
        pageNum = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfo = courseInfoService.findquickZiyuan(pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        hashMapMap.put("total", courseInfo.size());
        hashMapMap.put("data", courseInfo);
        return new ZanshiResponse(0, "请求成功", courseInfo.size(), courseInfo);
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
    public ZanshiResponse classroom(
            @RequestParam(required = false, value = "shooolArea") @ApiParam(value = "校区") String shooolArea
            , @RequestParam(required = false, value = "maxXin") @ApiParam(value = "屏号") Integer maxXin
            , @RequestParam(required = false, value = "dianNaoXin") @ApiParam(value = "电脑型号") Integer dianNaoXin
            , @RequestParam(required = false, value = "keTangHuXin") @ApiParam(value = "课堂互动型号") Integer keTangHuXin
            , @RequestParam(required = false, value = "luBoXin") @ApiParam(value = "录播互动型号") Integer luBoXin
            , @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) {
        log.info("进入/classroom/detail方法");
        pageNum = (pageNum - 1) * pageSize;
        if (StringUtils.isEmpty(shooolArea) && maxXin == null && dianNaoXin == null && dianNaoXin == null && keTangHuXin == null && luBoXin == null) {
            List<Classroom> classroom = classroomService.findClassroomE(pageNum, pageSize);
            int kk = classroomService.findClassroomJ();
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total", kk);
            hashMapMap.put("data", classroom);
            return new ZanshiResponse(0, "请求成功", kk, classroom);

        } else {
            List<Classroom> classroom = classroomService.findClassroom(shooolArea, maxXin, dianNaoXin, keTangHuXin, luBoXin, pageNum, pageSize);
            int kg = classroomService.findClassroomK(shooolArea, maxXin, dianNaoXin, keTangHuXin, luBoXin);
            log.info("classroom" + classroom.toString());
            HashMap<String, Object> hashMapMap = new HashMap<>();
            hashMapMap.put("total", kg);
            hashMapMap.put("data", classroom);
            return new ZanshiResponse(0, "请求成功", kg, classroom);
        }

    }


    @GetMapping(path = "/getRecommendCourseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取推荐课程信息列表")
    public DemonstrationResponse getRecommendCourseList(
            @RequestParam(required = false, value = "isRec") @ApiParam(value = "是否推荐（0不推荐，1推荐）") Integer isRec,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "source") @ApiParam(value = "source") String source,
            @RequestParam(required = false, value = "courseName") @ApiParam(value = "courseName") String courseName,
            @RequestParam(required = false, value = "categoryID") @ApiParam(value = "categoryID") String categoryID,
            @RequestParam(required = false, value = "startDate") @ApiParam(value = "startDate") long startDate,
            @RequestParam(required = false, value = "sortId") @ApiParam(value = "sortId") Integer sortId,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize) {

        return courseInfoService.getRecommendCourseList(isRec, pageNum, pageSize, source, courseName, categoryID, startDate, sortId);
    }


    @GetMapping(path = "/updateRecommendCourse", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改推荐课程")
    public DemonstrationResponse updateRecommendCourse(
            @RequestParam(required = false, value = "isRec") @ApiParam(value = "是否推荐（0不推荐，1推荐）") Integer isRec,
            @RequestParam(value = "id") @ApiParam(value = "id") Integer id) {
        return courseInfoService.updateRecommendCourse(isRec, id);
    }

    /***
     *  首页搜索选择(1和2)
     * */
    @GetMapping(path = "/topState", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "topState")
    public String courseInfotopState(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = false, value = "state") @ApiParam(value = "状态") Integer state
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
     */
    @GetMapping(path = "/addStudyDuration", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加学习时长")
    public DemonstrationResponse addStudyDuration(
            @RequestParam(required = true, value = "studentID") String studentID,
            @RequestParam(required = true, value = "time") @ApiParam(value = "时长") Integer time) {
        return courseInfoService.addStudyDuration(studentID, time);
    }

    @GetMapping(path = "/addLessonsLearned", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加学习课程")
    public DemonstrationResponse addLessonsLearned(
            @RequestParam(required = true, value = "studentID") BigInteger studentID,
            @RequestParam(required = true, value = "id") @ApiParam(value = "课程号") Integer id) {
        return courseInfoService.addLessonsLearned(studentID, id);
    }

    @GetMapping(path = "/getPersonalCenterInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取个人中心信息")
    public DemonstrationResponse getPersonalCenterInfo(
            @RequestParam(required = true, value = "userId") String userId) throws Exception {
        return courseInfoService.getPersonalCenterInfo(userId);
    }

    /**
     * 热门关键词2020/3/10
     */
    @GetMapping(path = "/popularKeyWord", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "热门关键词")
    public ZanshiResponse getpopularKeyWordInfo(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) throws Exception {
        pageNum = (pageNum - 1) * pageSize;
        List<hotKey> hotkeyList = courseInfoService.getpopularKeyWordInfo(pageNum, pageSize);
        int num = courseInfoService.getpopularKeyWordInfoNum(pageNum, pageSize);
        HashMap<String, Object> hashMapMap = new HashMap<>();
        return new ZanshiResponse(0, "请求成功", num, hotkeyList);
    }


    /**
     * 课程分类列表3/12
     */
    @GetMapping(path = "/getCourseTypeList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课程分类")
    public ZanshiResponse getCourseTypeList(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) throws Exception {
        pageNum = (pageNum - 1) * pageSize;
        List<categoryCode> categoryCodeList = courseInfoService.getCourseType(pageNum, pageSize);
        int num = courseInfoService.getCourseTypeNum();
        return new ZanshiResponse(0, "请求成功", num, categoryCodeList);
    }

    /**
     * 课程分类更新3/12
     */
    @GetMapping(path = "/updateCourseTypeList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课程分类更新")
    public ZanshiResponse updateCourseTypeList(
            @RequestParam(required = true, value = "type") int type,
            @RequestParam(required = false, value = "courseTypeName") String courseTypeName,
            @RequestParam(required = false, value = "courseTypeCode") String courseTypeCode,
            @RequestParam(required = false, value = "courseSort") String courseSort,
            @RequestParam(required = false, value = "courseHot") String courseHot
    ) throws Exception {
        //1.增加数据 2.更新数据 3.删除数据
        if (type ==1) {
            List<categoryCode> code =categoryService.findAllCode();
            for (categoryCode categoryCode : code) {
                if (categoryCode.getCourseTypeName().equals(courseTypeName)) {
                    return new ZanshiResponse(CodeMsg.REQUEST_EXCEPTION.getCode(),"已存在",0,null);
                }
            }
           int num= categoryService.addCategory(courseTypeName,courseTypeCode,courseSort,courseHot);

            if (num >0) {
                return new ZanshiResponse(0,"添加成功",num,null);
            }
            else{
                return new ZanshiResponse(-1,"未知错误",0,null);
            }
        }
        if (type ==2) {

            int num= categoryService.upCategory(courseTypeName,courseTypeCode,courseSort,courseHot);

            if (num >0) {
                return new ZanshiResponse(0,"添加成功",num,null);
            }
            else{
                return new ZanshiResponse(-1,"未知错误",0,null);
            }

        }
       if (type ==3) {
            //分类是否存在下级分类，假设存在则不能删除，只有在不存在下级分类的情况下才可删除
            List<categoryCode> code =categoryService.findAllCode();
            for (categoryCode categoryCode : code) {

               String codeParam = categoryCode.getCourseTypeCode();

                if (codeParam.length() == courseTypeCode.length() + 2) {

                    if (courseTypeCode.equals(codeParam.substring(0, courseTypeCode.length()))) {

                        return new ZanshiResponse(10005, CodeMsg.REQUEST_EXCEPTION.getMessage(), 0, "有下级分类");
                    } else {
                        //删除状态  0:正常  1:已删除
                        int row = categoryService.delCategroy(courseTypeCode);
                        if (row > 0) {
                            return new ZanshiResponse(0, CodeMsg.BIND_SUCESS.getMessage(), 0, null);
                        }

                    }

                } else {
                    return new ZanshiResponse(10001, CodeMsg.PARARM_EXCEPTION.getMessage(), 0, null);
                }

            }

        }


        return new ZanshiResponse(CodeMsg.REQUEST_EXCEPTION.getCode(), CodeMsg.REQUEST_EXCEPTION.getMessage(), 0, null);
    }


    /**
     * 三方课程分类列表3/16
     */
    @GetMapping(path = "/getCourseTypeSourceList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "三方课程分类列表3")
    public ZanshiResponse getCourseTypeSourceList(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) throws Exception {
        pageNum = (pageNum - 1) * pageSize;
        List<categoryThird> categoryCodeList = courseInfoService.getCourseThird(pageNum, pageSize);
        int num = courseInfoService.getCourseThirdeNum();
        return new ZanshiResponse(0, "请求成功", num, categoryCodeList);
    }

    /**
     * 三方课程分类更新3/16
     */
    @GetMapping(path = "/updateCourseTypeSourceList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课程分类更新(三方)")
    public ZanshiResponse updateCourseTypeList(
            @RequestParam(required = true, value = "type") int type,
            @RequestParam(required = false, value = "source") String source,
            @RequestParam(required = false, value = "courseTypeName") String courseTypeName,
            @RequestParam(required = false, value = "courseTypeCode") String courseTypeCode,
            @RequestParam(required = false, value = "courseSort") String courseSort,
            @RequestParam(required = false, value = "courseHot") String courseHot
    ) throws Exception {
        //1.增加数据 2.更新数据 3.删除数据
        if (type ==1) {
            List<categoryThird> code =categoryService.findAllCodeThird();
            for (categoryThird categoryCode : code) {
                if (categoryCode.getCourseTypeName().equals(courseTypeName)) {
                    return new ZanshiResponse(CodeMsg.REQUEST_EXCEPTION.getCode(),"已存在",0,null);
                }
            }
            int num= categoryService.addCategoryThird(courseTypeName,courseTypeCode,courseSort,courseHot,source);

            if (num >0) {
                return new ZanshiResponse(0,"添加成功",num,null);
            }
            else{
                return new ZanshiResponse(-1,"未知错误",0,null);
            }
        }
        if (type ==2) {

            int num= categoryService.upCategoryThird(courseTypeName,courseTypeCode,courseSort,courseHot,source);

            if (num >0) {
                return new ZanshiResponse(0,"更新成功",num,null);
            }
            else{
                return new ZanshiResponse(-1,"未知错误",0,null);
            }

        }
        if (type ==3) {
            //分类是否存在下级分类，假设存在则不能删除，只有在不存在下级分类的情况下才可删除
            List<categoryThird> code =categoryService.findAllCodeThird();
            for (categoryThird categoryCode : code) {

                String codeParam = categoryCode.getCourseTypeCode();

                if (codeParam.length()==courseTypeCode.length()+2) {

                    if (courseTypeCode.equals(codeParam.substring(0, courseTypeCode.length()))) {

                        return new ZanshiResponse(10005, CodeMsg.REQUEST_EXCEPTION.getMessage(), 0, "有下级分类");
                    } else {
                        //删除状态  0:正常  1:已删除
                        int row = categoryService.delCategroyThird(courseTypeCode);
                        if (row>0) {
                            return new ZanshiResponse(0, CodeMsg.BIND_SUCESS.getMessage(), 0, null);
                        }

                    }

                }
                else {
                    return new ZanshiResponse(10001, CodeMsg.PARARM_EXCEPTION.getMessage(), 0, null);
                }

            }

        }
        if (type==4) {
            //映射测试
            List<categoryCode> code =categoryService.findAllCode();
            for (categoryCode categoryCode : code) {

                String codeParam = categoryCode.getCourseTypeCode();

                //一级分类
                if (courseTypeCode==null) {
                    List<categoryCode> categoryName= categoryService.findOne();
                    return new ZanshiResponse(0, CodeMsg.BIND_SUCESS.getMessage(), 0, categoryName);
                }

                if (courseTypeCode!=null&&codeParam.length() == courseTypeCode.length() + 2) {
                    if (courseTypeCode.equals(codeParam.substring(0, courseTypeCode.length()))) {
                        List<categoryCode> categoryName = categoryService.findMore(courseTypeCode);
                        return new ZanshiResponse(0, CodeMsg.BIND_SUCESS.getMessage(), 0, categoryName);
                    } else {
                        List<categoryCode> categoryName = categoryService.findBen(courseTypeCode);
                        return new ZanshiResponse(0, CodeMsg.BIND_SUCESS.getMessage(), 0, categoryName);
                    }

                }
            }

        }
        return new ZanshiResponse(CodeMsg.REQUEST_EXCEPTION.getCode(), CodeMsg.REQUEST_EXCEPTION.getMessage(), 0, null);
    }


/***
 * 首页修改版
 *
 */
    @GetMapping(path = "/getGroupClassList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取组团信息")
    public DemonstrationResponse getGroupClassList() throws Exception {
        return courseInfoService.getGroupClassList();
    }

    @GetMapping(path = "/updateGroupClassSort", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改组团信息顺序")
    public DemonstrationResponse updateGroupClassSort(
            @RequestParam(required = true, value = "sortList") String sortList) throws Exception {
        return courseInfoService.updateGroupClassSort(sortList);
    }

    @PostMapping(path = "/addGroupClass", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加组团信息")
    public DemonstrationResponse addGroupClass(
           @RequestBody GroupClass groupClass) throws Exception {
        return courseInfoService.addGroupClass(groupClass);
    }

    @DeleteMapping(path = "/deleteGroupClass", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "删除组团信息")
    public DemonstrationResponse deleteGroupClass(
            @RequestParam(required = true, value = "id") int id) throws Exception {
        return courseInfoService.deleteGroupClass(id);
    }

    @GetMapping(path = "/getGroupMemberList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取组团信息")
    public DemonstrationResponse getGroupMemberList(
            @RequestParam(required = true, value = "groupId") int groupId
    ) throws Exception {
        return courseInfoService.getGroupMemberList(groupId);
    }

    @GetMapping(path = "/updateGroupMemberSort", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "更新组团信息")
    public DemonstrationResponse updateGroupMemberSort(
            @RequestParam(required = true, value = "groupId") int groupId,
            @RequestParam(required = true, value = "sortList") String sortList
    ) throws Exception {
        return courseInfoService.updateGroupMemberSort(groupId,sortList);
    }

    @PostMapping(path = "/addGroupMember", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加组团信息")
    public DemonstrationResponse addGroupMember(
            @RequestParam(required = true,value = "courseId") int courseId,
            @RequestParam(required = true,value = "groupClassId") int groupClassId
            ) throws Exception {
        return courseInfoService.addGroupMember(courseId,groupClassId);
    }

    @DeleteMapping(path = "/deleteGroupMember", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "删除组团信息")
    public DemonstrationResponse deleteGroupMember(
            @RequestParam(required = true, value = "id") int id) throws Exception {
        return courseInfoService.deleteGroupMember(id);
    }

    @GetMapping(path = "/getGroupInformationList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取组团信息")
    public DemonstrationResponse getGroupInformationList(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize
    ) throws Exception {
        return courseInfoService.getGroupInformationList(pageNum,pageSize);
    }

    @PostMapping(path = "/updateGroupSort", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "修改组团顺序")
    public DemonstrationResponse updateGroupSort(
            @RequestBody List<GroupSortModel> groupSortModelList) throws Exception {
        return courseInfoService.updateGroupSort(groupSortModelList);
    }

    @PostMapping(path = "/addCourseReviews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "添加课程评论")
    public DemonstrationResponse addCourseReviews(
            @RequestBody CourseReviews courseReviews) throws Exception {
        return courseInfoService.addCourseReviews(courseReviews);
    }

    @GetMapping(path = "/getCourseReviewsList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "获取课程评列表")
    public DemonstrationResponse getCourseReviewsList(
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") @ApiParam(value = "页数") Integer pageNum,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") @ApiParam(value = "页大小") Integer pageSize,
            @RequestParam(required = false, value = "courseId") @ApiParam(value = "课程id") String courseId
    ) throws Exception {
        return courseInfoService.getCourseReviewsList(pageNum,pageSize,courseId);
    }
}
