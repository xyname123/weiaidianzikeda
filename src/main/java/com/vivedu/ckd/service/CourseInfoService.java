package com.vivedu.ckd.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.dao.CourseInfoMapper;
import com.vivedu.ckd.pojo.ZanshiResponse;
import com.vivedu.ckd.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@EnableScheduling
@Slf4j
public class CourseInfoService {
    @Autowired
    private CourseInfoMapper mapper;
    @Autowired
    private RestTemplate restTemplate;
    //爱课堂
    private static final String keyi = "192006250b4c09247ec02edce69f6a2d";
    //film
    private static final String keyo = "Xi$@!%#y*&#i@!5weJ%";
    //爱课堂
    private static String keyiurl;
    //film
    private static String keyourl;
    @Autowired
    private CourseInfoService courseInfoService;
    private List<hotKey> popularKeyWordInfo;

    public CourseInfoService(CourseInfoMapper mapper) {
        this.mapper = mapper;
    }


    public List<CourseInfo> findCourseByUserIdLearn(String userid, Integer pageNum, Integer pageSize) {
        return mapper.findCourseByUserId(userid,pageNum,pageSize);
    }

    public List<CourseInfo> findCourseByUserIdPage(String userid, Integer pageNum, Integer pageSize) {
        return mapper.findCourseByUserIdPage(userid, pageNum, pageSize);
    }


    public List<CourseInfo> findCourseByUserId(String userId, String sort, Integer pageNum, Integer pageSize) {
        return mapper.findCourseByUserIdIf(userId, sort, pageNum, pageSize);
    }

    public List<CourseInfo> findCourseByKey(String courseName, Integer pageNum, Integer pageSize, Integer state, Integer courseTypeCode) {
        return mapper.findCourseByKey(courseName, pageNum, pageSize, state, courseTypeCode);

    }

    public List<CourseInfo> findAllCourse(Integer pageNum, Integer pageSize, Integer state, Integer courseTypeCode) {
        return mapper.findAllCourse(pageNum, pageSize, state, courseTypeCode);

    }

    public List<CourseInfo> findCourse(Integer id, Integer pageNum, Integer pageSize) {
        return mapper.findCourse(id, pageNum, pageSize);

    }

    public List<CourseInfo> courseInfoByMarked(String courseId) {
        return mapper.findMarked(courseId);
    }

    public List<T_SHARE_CDXT_YJS_JBXX> findT() {
        return mapper.findT();
    }

    public void updateCourse(List<CourseInfoFilm> courseInfoFilmlist) {
        mapper.updateCourse(courseInfoFilmlist);

    }

    public void InsertCourse(List<CourseInfoAiVo> courseInfoAilist) {

        mapper.InsertCourse(courseInfoAilist);
    }

    public void InsertCourseMetel(List<CourseInfoMetel> courseInfoMetelList) {
        mapper.InsertCourseMetel(courseInfoMetelList);
    }

    public DemonstrationResponse statisticalCount(int type) {
        TimesStatistics timesStatistics = mapper.query(type);
        Date date = new Date();
        int row = 0;
        if (timesStatistics != null) {
            timesStatistics.setTotalNumber(timesStatistics.getTotalNumber() + 1);
            if (type == 0) {
                timesStatistics.setDayNumber(timesStatistics.getDayNumber() + 1);
            }
            timesStatistics.setStatisticalDate(date);
            row = mapper.update(timesStatistics);
        } else {
            timesStatistics = new TimesStatistics();
            timesStatistics.setTotalNumber(1);
            if (type == 0) {
                timesStatistics.setDayNumber(1);
            }
            timesStatistics.setStatisticalDate(date);
            timesStatistics.setTypeTag(type);
            row = mapper.addTimesStatistics(timesStatistics);
        }
        if (row > 0) {
            return new DemonstrationResponse(200, "更新成功！", null);
        }
        return new DemonstrationResponse(500, "系统异常！", null);
    }


    public DemonstrationResponse getCountList() {
        StatisticsModel statisticsModel = mapper.getCountList();
        return new DemonstrationResponse(200, "更新成功！", statisticsModel);
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void updateStatisticsCount() {
        log.info("开启定时任务");
        mapper.updateStatisticsCount();
    }


    @Scheduled(cron = "0 0 22 * * ?")
    public void updateAi() {
        String sign = MD5Utils.MD5Encode("page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&key=" + keyi, "utf8").toUpperCase();

        String s = restTemplate.getForObject("http://222.197.165.58:8080/api/courselist?enc=" + sign + "&page=" + 1 + "&size=" + 500 + "&sort=" + "date", String.class);

        Map map = (Map) JSON.parse(s);
        Integer data = (Integer) map.get("totalnum");
        int count = data / 500 + 1;
        log.info("count---" + count);
        String dataai = map.get("data").toString().trim();
        for (int i = 1; i <= count; i++) {
            try {
                Thread.sleep(1000);
                sign = MD5Utils.MD5Encode("page=" + i + "&size=" + 500 + "&sort=" + "date" + "&key=" + keyi, "utf8").toUpperCase();
                s = restTemplate.getForObject("http://222.197.165.58:8080/api/courselist?enc=" + sign + "&page=" + i + "&size=" + 500 + "&sort=" + "date", String.class);
                String s1 = s.replace("members", "teacher");
                String s2 = s1.replace("chapters", "chapterList");
                Map mapai = (Map) JSON.parse(s2);
                String dataA = mapai.get("data").toString().trim();
                List<CourseInfoAiVo> CourseInfoAilist = JSONObject.parseArray(dataA, CourseInfoAiVo.class);
                //  List<CourseInfoAiVo> CourseInfoAilist = JSONArray.toList(JSONArray.fromObject(dataA), new CourseInfoAiVo(), new JsonConfig());
                for (CourseInfoAiVo courseInfoAiVo : CourseInfoAilist) {
                    String courseid = courseInfoAiVo.getCourseid();
                    int met = courseInfoService.findAiVo(courseInfoAiVo.getCourseid());
                    if (met <= 0) {
                        // courseInfoService.InsertCourse(CourseInfoAilist);
                        //todo
                        Object[] teacher = courseInfoAiVo.getTeacher();
                        String teacherData = Arrays.toString(teacher);
                        Object[] chapterList = courseInfoAiVo.getChapterList();
                        String chapterListData = Arrays.toString(chapterList);
                        //新增
                        Object[] acts = courseInfoAiVo.getActs();
                        String actsData =Arrays.toString(acts);
                        courseInfoService.InsertCourseOne(courseInfoAiVo);
                        courseInfoService.updateAiCourseOneTeacherAndChapListAndacts(actsData,teacherData, chapterListData, courseInfoAiVo.getCoursename(), courseInfoAiVo.getSource());
                    } else {
                        // courseInfoService.updateAi(CourseInfoAilist);
                        courseInfoService.updateAiCourse(courseInfoAiVo);
                        Object[] teacher = courseInfoAiVo.getTeacher();
                        String teacherData = Arrays.toString(teacher);
                        Object[] chapterList = courseInfoAiVo.getChapterList();
                        String chapterListData = Arrays.toString(chapterList);
                        Object[] acts = courseInfoAiVo.getActs();
                        String actsData =Arrays.toString(acts);
                        courseInfoService.updateAiCourseOneTeacherAndChapListAndacts(actsData,teacherData, chapterListData, courseInfoAiVo.getCoursename(), courseInfoAiVo.getSource());

                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void updateAiCourse(CourseInfoAiVo courseInfoAiV) {
        mapper.updateAiCourse(courseInfoAiV);
    }

    public void updateAiCourseOneTeacherAndChapList(String teacherData, String chapterListData, String coursename, String source) {
        mapper.updateAiCourseOneTeacherAndChapList(teacherData, chapterListData, coursename, source);
    }

    public void InsertCourseOne(CourseInfoAiVo courseInfoAiVo) {
        mapper.InsertCourseOne(courseInfoAiVo);
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void updateMe() {
        String metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + 1 + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
        Map mapmetel = (Map) JSON.parse(metel);
        Integer datametel = (Integer) mapmetel.get("totalnum");
        int countmetel = datametel / 100 + 1;
        String datametelx = mapmetel.get("data").toString().trim();
        log.info("metel--" + datametelx);

        for (int j = 1; j <= countmetel; j++) {

            metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + j + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
            String metel1 = metel.replace("coursename_en", "coursenameEn");
            String s2 = metel1.replace("profile_en", "profileEn");
            Map mapmete2 = (Map) JSON.parse(s2);
            log.info("mapmete2" + mapmete2);
            String datametes = mapmete2.get("data").toString().trim();
            List<CourseInfoMetel> CourseInfoMetelList = JSONObject.parseArray(datametes, CourseInfoMetel.class);
            // List<CourseInfoMetel> CourseInfoMetelList = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoMetel(), new JsonConfig());
            for (CourseInfoMetel courseInfoMetel : CourseInfoMetelList) {
                courseInfoMetel.setSoure("MeTel");
                int met = courseInfoService.findMete(courseInfoMetel.getCoursename());
                if (met <= 0) {
                    //courseInfoService.InsertCourseMetel(CourseInfoMetelList);
                    String[] teacher = courseInfoMetel.getTeacher();
                    String teacherData = Arrays.toString(teacher);
                    String[] chapterList = courseInfoMetel.getChapterlist();
                    String chapterListData = Arrays.toString(chapterList);
                    courseInfoService.InsertCourseOneMe(courseInfoMetel);
                    courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData, chapterListData, courseInfoMetel.getCoursename(), courseInfoMetel.getSoure());
                } else {
                    courseInfoService.updateMeteOne(courseInfoMetel);
                    String[] teacher = courseInfoMetel.getTeacher();
                    String teacherData = Arrays.toString(teacher);
                    String[] chapterList = courseInfoMetel.getChapterlist();
                    String chapterListData = Arrays.toString(chapterList);
                    courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData, chapterListData, courseInfoMetel.getCoursename(), courseInfoMetel.getSoure());
                }

            }

        }
    }

    public void InsertCourseOneMe(CourseInfoMetel courseInfoMetel) {
        mapper.InsertCourseOneMe(courseInfoMetel);
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void updateFim() {
        String signKey = MD5Utils.MD5Encode("page=" + 1 + "size=" + 500 + "sort=" + "date" + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&enc=" + signKey, String.class);
        Map mapF = (Map) JSON.parse(ss);
        String datameF = (String) mapF.get("totalnum");
        int is = Integer.parseInt(datameF);
        int countmetelF = is / 500 + 1;
        for (int j = 1; j <= countmetelF; j++) {
            String ssD = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&enc=" + signKey, String.class);
            Map mapFssD = (Map) JSON.parse(ssD);
            String datametes = mapFssD.get("data").toString().trim();
            List<CourseInfoFilm> CourseInfoFilmlist = JSONObject.parseArray(datametes, CourseInfoFilm.class);
            // List<CourseInfoFilm> CourseInfoFilmlist = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoFilm(), new JsonConfig());
            for (CourseInfoFilm courseInfoFilm : CourseInfoFilmlist) {
                int met = courseInfoService.findFilmT(courseInfoFilm.getCourseid());
                if (met <= 0) {
                    //todo webVideoUrl 添加视频url
                    //courseInfoService.updateCourse(CourseInfoFilmlist);
                    courseInfoService.updateCourseFilm(courseInfoFilm);
                    //courseInfoService.InsertCourseMetel(CourseInfoMetelList);
                    String[] teacher = courseInfoFilm.getTeacher();
                    String teacherData = Arrays.toString(teacher);
                    String[] chapterList = courseInfoFilm.getChapterlist();
                    String chapterListData = Arrays.toString(chapterList);
                    courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData, chapterListData, courseInfoFilm.getCoursename(), courseInfoFilm.getSource());
                } else {
                    // courseInfoService.updateFilm(CourseInfoFilmlist);
                    //todo webVideoUrl 添加视频url
                    courseInfoService.updateFilmeOne(courseInfoFilm);
                    String[] teacher = courseInfoFilm.getTeacher();
                    String teacherData = Arrays.toString(teacher);
                    String[] chapterList = courseInfoFilm.getChapterlist();
                    String chapterListData = Arrays.toString(chapterList);
                    courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData, chapterListData, courseInfoFilm.getCoursename(), courseInfoFilm.getSource());
                }
            }

        }

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateMuKe() {


        //todo 查询另一个数据库,并存入此数据库中

         /*   for (CourseInfoFilm courseInfoFilm : CourseInfoFilmlist) {
                int met = courseInfoService.findFilmT(courseInfoFilm.getCourseid());
                if (met <= 0) {
                    //courseInfoService.updateCourse(CourseInfoFilmlist);
                    courseInfoService.updateCourseFilm(courseInfoFilm);
                    //courseInfoService.InsertCourseMetel(CourseInfoMetelList);
                    String[] teacher = courseInfoFilm.getTeacher();
                    String teacherData = Arrays.toString(teacher);
                    String[] chapterList = courseInfoFilm.getChapterlist();
                    String chapterListData = Arrays.toString(chapterList);
                    courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData,chapterListData,courseInfoFilm.getCoursename());
                } else {
                    // courseInfoService.updateFilm(CourseInfoFilmlist);
                    courseInfoService.updateFilmeOne(courseInfoFilm);
                    String[] teacher = courseInfoFilm.getTeacher();
                    String teacherData = Arrays.toString(teacher);
                    String[] chapterList = courseInfoFilm.getChapterlist();
                    String chapterListData = Arrays.toString(chapterList);
                    courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData,chapterListData,courseInfoFilm.getCoursename());
                }
            }
*/
    }


    public void updateFilmeOne(CourseInfoFilm courseInfoFilm) {
        mapper.updateFilmeOne(courseInfoFilm);
    }

    public void updateCourseFilm(CourseInfoFilm courseInfoFilm) {
        mapper.updateCourseFilm(courseInfoFilm);
    }


    public List<CourseInfo> findCourseByfilmTop(String film, Integer page, Integer size) {

        return mapper.findCourseByfilmTop(film, page, size);
    }

    public List<CourseInfo> findRec(Integer pageNum, Integer pageSize) {

        return mapper.findRec(pageNum, pageSize);
    }

    public List<CourseInfo> recIndex(Integer pageNum, Integer pageSize) {
        return mapper.recIndex(pageNum, pageSize);
    }

    public List<CourseInfo> findrquick(Integer pageNum, Integer pageSize) {
        return mapper.findrquick(pageNum, pageSize);
    }

    public List<CourseInfo> findquickRec(Integer pageNum, Integer pageSize) {
        return mapper.findquickRec(pageNum, pageSize);
    }

    public List<CourseInfo> findquickZiyuan(Integer pageNum, Integer pageSize) {
        return mapper.findquickZiyuan(pageNum, pageSize);
    }

    public CourseInfo findCourseDatal(Integer courseId) {

        return mapper.findCourseDatal(courseId);
    }

    public List<CourseInfoFilm> findFilm(List<CourseInfoFilm> courseInfoFilmlist) {

        return mapper.findFilm(courseInfoFilmlist);
    }

    public void updateFilm(List<CourseInfoFilm> courseInfoFilmlistBen) {
        mapper.updateFilm(courseInfoFilmlistBen);

    }

    public List<CourseInfoAiVo> findAi(List<CourseInfoAiVo> courseInfoAilist) {
        return mapper.findAi(courseInfoAilist);
    }

    public void updateAi(List<CourseInfoAiVo> courseInfoAilistBen) {
        mapper.updateAi(courseInfoAilistBen);
    }

    public int findMete(String name) {

        return mapper.findMete(name);
    }

    public void updateMete(List<CourseInfoMetel> courseInfoMetelListBean) {
        mapper.updateMete(courseInfoMetelListBean);
    }

    public int findAiVo(String id) {
        return mapper.findAiVo(id);
    }

    public int findFilmT(String courseid) {
        return mapper.findFilmT(courseid);
    }


    public DemonstrationResponse getRecommendCourseList(Integer isRec, Integer pageNum, Integer pageSize, String source, String courseName, String categoryID, long startDate, Integer sortId) {
        if (isRec == null || isRec < 0) {
            isRec = 0;
        }
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        int firstIndex = (pageNum - 1) * pageSize;
        String res = "";
        if (startDate != 0) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(startDate);
            res = simpleDateFormat.format(date);
        }
        List<CourseInfo> courseInfos = mapper.getRecommendCourseList(isRec, firstIndex, pageSize, source, courseName, res, sortId, categoryID);
        int courseInfosSize = mapper.getRecommendCourseListSize(isRec, source, courseName, res, categoryID);
        RecommendCourseData recommendCourseData = new RecommendCourseData(pageNum, courseInfosSize, courseInfos);

        return new DemonstrationResponse(0, "获取成功！", recommendCourseData);
    }

    public DemonstrationResponse updateRecommendCourse(Integer isRec, Integer id) {
        if (isRec == null || isRec < 0) {
            isRec = 0;
        }
        if (id == null || id <= 0) {
            return new DemonstrationResponse(1001, "请传入正确的id", null);
        }
        int row = mapper.updateRecommendCourse(isRec, id);
        if (row > 0) {
            return new DemonstrationResponse(0, "修改成功！", null);
        }
        return new DemonstrationResponse(-1, "修改失败！", null);
    }

    public DemonstrationResponse getRecommendCourseListL(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        int firstIndex = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfos = mapper.getRecommendCourseListL(firstIndex, pageSize);
        int k = mapper.getRecommendCourseListK();
        RecommendCourseData recommendCourseData = new RecommendCourseData(pageNum, k, courseInfos);
        return new DemonstrationResponse(0, "获取成功！", recommendCourseData);
    }

    public int findCourseByUserIdNum(String userId) {

        return mapper.findCourseByUserIdNum(userId);
    }

    public int findCourseByKeyNum(String courseName, Integer state, Integer courseTypeCode) {
        return mapper.findCourseByKeyNum(courseName, state, courseTypeCode);
    }

    public int findAllCourseNum(Integer state, Integer courseTypeCode) {
        return mapper.findAllCourseNum(state, courseTypeCode);
    }

    public List<CourseInfo> findCoursetopState(Integer state, Integer page, Integer size) {
        /*state  1:正常 3:已完结 */


        return mapper.findCoursetopState(state, page, size);

    }

    public int findCoursetopStateT(Integer state) {
        return mapper.findCoursetopStateT(state);
    }


    public DemonstrationResponse addStudyDuration(String studentID, Integer time) {
        StudyDuration studyDuration = mapper.queryStudyDuration(studentID);
        int row = 0;
        if (studyDuration != null && studyDuration.getId() > 0) {
            studyDuration.setLearnTime(studyDuration.getLearnTime() + time);
            row = mapper.updateStudyDuration(studyDuration);
        } else {
            StudyDuration studyDuration1 = new StudyDuration();
            studyDuration1.setLearnTime(time);
            studyDuration1.setStudentID(studentID);
            Date date = new Date();
            studyDuration1.setCreateDate(date);
            row = mapper.addStudyDuration(studyDuration1);
        }
        if (row > 0) {
            return new DemonstrationResponse(0, "成功！", null);
        } else {
            return new DemonstrationResponse(-1, "异常！", null);
        }
    }

    public DemonstrationResponse addLessonsLearned(BigInteger studentID, Integer courseId) {
        Date date = new Date();
        LessonsLearned lessonsLearned1 = mapper.queryLessonsLearned(studentID, courseId);
        log.info("lessonsLearned1" + lessonsLearned1);
        if (lessonsLearned1 != null || lessonsLearned1.getCourseId() > 0) {
            lessonsLearned1.setCreateDate(date);
            mapper.updateLessonsLearned(lessonsLearned1);
            return new DemonstrationResponse(0, "成功！", null);
        }
        LessonsLearned lessonsLearned = new LessonsLearned();
        lessonsLearned.setStudentID(studentID);
        lessonsLearned.setCourseId(courseId);
        lessonsLearned.setCreateDate(date);
        int row = mapper.addLessonsLearned(lessonsLearned);
        if (row > 0) {
            return new DemonstrationResponse(0, "成功！", null);
        } else {
            return new DemonstrationResponse(-1, "异常！", null);
        }
    }


    public DemonstrationResponse getPersonalCenterInfo(String userId) throws Exception {
        if (StringUtils.isEmpty(userId) || userId.length() < 7) {
            return new DemonstrationResponse(10001, "请传入正确的用户ID！", null);
        }
        String startDate = "2020-2-24 00:00:00";
        String endDate = "2020-7-15 23:59:59";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(startDate);
        long days = new Date().getTime() / 1000 / 3600 / 24 - date.getTime() / 1000 / 3600 / 24;
        long week = (days % 7 == 0 ? days / 7 : days / 7 + 1);
        List<T_SHARE_CDXT_BKS_KCPK> t_share_cdxt_bks_kcpks = new ArrayList<>();
        StudentPersonalCenterInfo studentPersonalCenterInfo = new StudentPersonalCenterInfo();
        if (userId.length() == 7) {
            //查出老师课程userid 爱课堂
            String sign = MD5Utils.MD5Encode("page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&userid=" + userId + "&key=" + keyi, "utf8").toUpperCase();
            String s = restTemplate.getForObject("http://222.197.165.58:8080/api/courselist?enc=" + sign + "&page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&userid=" + userId, String.class);

            if (s != null && StringUtils.isNotEmpty(s)) {
                String s1 = s.replace("members", "teacher");
                String s2 = s1.replace("chapters", "chapterList");
                Map mapai = (Map) JSON.parse(s2);
                String dataA = mapai.get("totalnum").toString().trim();
                if (dataA.equals("0")) {

                    return new DemonstrationResponse(0, "查询成功！但是此老師無課程", null);

                } else {
                    String datax = mapai.get("data").toString().trim();
                    log.info("datax----"+datax);
                    List<CourseInfoAiVo> CourseInfoAilist = JSONObject.parseArray(datax,CourseInfoAiVo.class);
                    if (CourseInfoAilist.size() > 0) {
                        studentPersonalCenterInfo.setCourseInfoaiList(CourseInfoAilist);
                    }
                }

            }
            else {
                return new DemonstrationResponse(0, "查询成功！但是此老師無課程", null);

            }
            //film
            String signKey = MD5Utils.MD5Encode("page=" + 1 + "size=" + 500 + "sort=" + "date" + "userid=" + userId + keyo, "utf8");
            String ssD = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&userid=" + userId + "&enc=" + signKey, String.class);
            if (ssD != null) {
                Map mapFssD = (Map) JSON.parse(ssD);
                String datametes = mapFssD.get("data").toString().trim();
                List<CourseInfoFilm> CourseInfoFilmlist = JSONObject.parseArray(datametes, CourseInfoFilm.class);
                if (CourseInfoFilmlist.size() > 0) {
                    studentPersonalCenterInfo.setClassfilm(CourseInfoFilmlist);
                }
            }

            t_share_cdxt_bks_kcpks = mapper.queryJSPK(userId, week + 1);
            List<ClassSchedule> classScheduleList = new ArrayList<>();
            for (T_SHARE_CDXT_BKS_KCPK t : t_share_cdxt_bks_kcpks) {
                ClassSchedule classSchedule;
                int difference = (Integer.parseInt(t.getJSJC()) - 1) - (Integer.parseInt(t.getKSJC()) - 1);
                if (difference > 1) {
                    for (int i = 1; i <= difference; i++) {
                        classSchedule = new ClassSchedule((Integer.parseInt(t.getJSJC()) - i), (Integer.parseInt(t.getXQJ())), t.getKCMC());
                        classScheduleList.add(classSchedule);
                    }
                } else {
                    classSchedule = new ClassSchedule((Integer.parseInt(t.getJSJC()) - 1), (Integer.parseInt(t.getXQJ())), t.getKCMC());
                    classScheduleList.add(classSchedule);
                }
            }
            studentPersonalCenterInfo.setClassScheduleList(classScheduleList);
            return new DemonstrationResponse(0, "查询成功！", studentPersonalCenterInfo);
        } else if (userId.length() >= 12) {
            int courseCount = mapper.queryCourseCount(userId, startDate, endDate);
            int courseEnd = mapper.queryCourseEnd(userId, startDate, endDate);
            Integer rank = mapper.queryRank(userId, startDate, endDate);
            if (rank == null) {
                rank = 0;
            }
            Integer allCount = mapper.queryAllCount(userId, startDate, endDate);
            if (allCount == null) {
                allCount = 1;
            }
            int denominator = (allCount == 0 ? 1 : allCount);
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            String result = numberFormat.format((float) (denominator - rank) / (float) denominator * 100) + "%";
            List<StudyDuration> studyDurationList = mapper.queryStudyDurationList(userId);
            List<CourseInfo> courseInfoList = mapper.queryCourseInfoList(userId, startDate, endDate);
            t_share_cdxt_bks_kcpks = mapper.queryKCPK(userId, week + 1);
            List<ClassSchedule> classScheduleList = new ArrayList<>();
            for (T_SHARE_CDXT_BKS_KCPK t : t_share_cdxt_bks_kcpks) {
                ClassSchedule classSchedule;
                int difference = (Integer.parseInt(t.getJSJC()) - 1) - (Integer.parseInt(t.getKSJC()) - 1);
                if (difference > 1) {
                    for (int i = 1; i <= difference; i++) {
                        classSchedule = new ClassSchedule((Integer.parseInt(t.getJSJC()) - i), (Integer.parseInt(t.getXQJ())), t.getKCMC());
                        classScheduleList.add(classSchedule);
                    }
                } else {
                    classSchedule = new ClassSchedule((Integer.parseInt(t.getJSJC()) - 1), (Integer.parseInt(t.getXQJ())), t.getKCMC());
                    classScheduleList.add(classSchedule);
                }
            }

            studentPersonalCenterInfo.setCourseCount(courseCount);
            studentPersonalCenterInfo.setCourseEnd(courseEnd);
            studentPersonalCenterInfo.setPercentage(result);
            studentPersonalCenterInfo.setStudyDurationList(studyDurationList);
            studentPersonalCenterInfo.setCourseInfoList(courseInfoList);
            studentPersonalCenterInfo.setClassScheduleList(classScheduleList);
            return new DemonstrationResponse(0, "查询成功！", studentPersonalCenterInfo);
        } else {
            return new DemonstrationResponse(10001, "没有用户ID数据！", null);
        }
    }

    public int findCourseDatalCoutnt(Integer id) {

        return mapper.findCourseDatalCoutnt(id);
    }

    public void updateStudyCount(Integer id, int i) {
        mapper.updateStudyCount(id, i);
    }

    public void InsertCourseDan(CourseInfoAiVo courseInfoAiVo) {
        mapper.InsertCourseDan(courseInfoAiVo);
    }

    public void InsertCourseDanTeacher(String coursename, String teacher) {
        mapper.InsertCourseDanTeacher(coursename, teacher);
    }

    public void updateMeteOne(CourseInfoMetel courseInfoMetel) {
        mapper.updateMeteOne(courseInfoMetel);
    }


    public void insertBrowse(Integer userid, Integer id) {
        mapper.insertBrowse(userid, id);
    }

    public hotKey findKeyCourseNameAndHotNum(String courseName) {
        return mapper.findKeyCourseNameAndHotNum(courseName);
    }

    public void updateKeyCourseNameAndHotNum(String courseName, int hot) {
        mapper.updateKeyCourseNameAndHotNum(courseName, hot);
    }

    public void addKeyCourseNameAndHotNum(String courseName) {
        mapper.addKeyCourseNameAndHotNum(courseName);
    }

    public List<hotKey> getpopularKeyWordInfo(Integer pageNum, Integer pageSize) {
        return mapper.getpopularKeyWordInfo(pageNum, pageSize);
    }

    public List<categoryCode> getCourseType(Integer pageNum, Integer pageSize) {
        return mapper.getCourseType(pageNum, pageSize);
    }

    public int getpopularKeyWordInfoNum(Integer pageNum, Integer pageSize) {
        return mapper.getpopularKeyWordInfoNum(pageNum, pageSize);
    }

    public int getCourseTypeNum() {
        return mapper.getCourseTypeNum();
    }

    public List<categoryThird> getCourseThird(Integer pageNum, Integer pageSize) {
        return mapper.getCourseThird(pageNum, pageSize);
    }

    public int getCourseThirdeNum() {
        return mapper.getCourseThirdeNum();
    }

    public DemonstrationResponse getGroupClassList() {
        try {
            List<GroupClass> groupClassList = mapper.getGroupClassList();
            return new DemonstrationResponse(0, "查询成功！", groupClassList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse updateGroupClassSort(String sortList) {
        try {
            if (StringUtils.isEmpty(sortList)) {
                return new DemonstrationResponse(-1, "顺序id不能为空", null);
            }
            if (sortList.contains(",")) {
                String[] ids = sortList.split(",");
                List<GroupClass> groupClassList = new ArrayList<>();
                for (int i = 0; i < ids.length; i++) {
                    GroupClass groupClass = new GroupClass(Integer.parseInt(ids[i]), "", i + 1,1);
                    groupClassList.add(groupClass);
                }
                int rows = mapper.updateGroupClassSort(groupClassList);
            }
            return new DemonstrationResponse(0, "修改成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse addGroupClass(GroupClass groupClass) {
        try {
            int o = mapper.find();
            if (o == 0) {
                groupClass.setSort(1);
            } else {
                int max = mapper.queryMaxSortId();
                groupClass.setSort(max + 1);
            }
            int row = mapper.addGroupClass(groupClass);
            if (row > 0) {
                return new DemonstrationResponse(0, "添加成功！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse deleteGroupClass(int id) {
        try {
            if (id <= 0) {
                return new DemonstrationResponse(-1, "请传正确的id", null);
            }
            int row = mapper.deleteGroupClass(id);
            if (row > 0) {
                return new DemonstrationResponse(0, "删除成功！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse getGroupMemberList(int groupId) {
        try {
            if (groupId <= 0) {
                return new DemonstrationResponse(-1, "请传正确的groupId", null);
            }
            List<GroupMember> groupMemberList = mapper.getGroupMemberList(groupId);
            return new DemonstrationResponse(0, "查询成功！", groupMemberList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse updateGroupMemberSort(int groupId, String sortList) {
        try {
            if (groupId <= 0) {
                return new DemonstrationResponse(-1, "请传正确的groupId", null);
            }
            if (StringUtils.isEmpty(sortList)) {
                return new DemonstrationResponse(-1, "顺序id不能为空", null);
            }
            if (sortList.contains(",")) {
                String[] ids = sortList.split(",");
                List<GroupMember> groupMemberList = new ArrayList<>();
                for (int i = 0; i < ids.length; i++) {
                    GroupMember groupMember = new GroupMember(0, Integer.parseInt(ids[i]), groupId, i + 1, "", "");
                    groupMemberList.add(groupMember);
                }
                int rows = mapper.updateGroupMemberSort(groupMemberList);
            }
            return new DemonstrationResponse(0, "修改成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse addGroupMember(Integer courseId, Integer GroupClassId) {
        try {
            GroupMember groupMember1 = mapper.queryGroupMember(courseId, GroupClassId);
            if (groupMember1 != null) {
                return new DemonstrationResponse(-1, "此课程已存在在此组中！", null);
            }
            int max = 1;
            int o = mapper.findp(courseId, GroupClassId);
            if (o > 0) {
                max = mapper.queryGroupMemberMax(courseId, GroupClassId);
            }
            GroupMember groupMember = new GroupMember();
            groupMember.setSort(max);
            groupMember.setGroupClassId(GroupClassId);
            groupMember.setCourseId(courseId);
            int row = mapper.addGroupMember(groupMember);
            if (row > 0) {
                return new DemonstrationResponse(0, "添加成功！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse deleteGroupMember(int id) {
        try {
            if (id <= 0) {
                return new DemonstrationResponse(-1, "请传正确的id", null);
            }
            int row = mapper.deleteGroupMember(id);
            if (row > 0) {
                return new DemonstrationResponse(0, "删除成功！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse getGroupInformationList(Integer pageNum, Integer pageSize) {
        try {
            if (pageNum == null || pageNum <= 0) {
                pageNum = 1;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            int firstIndex = (pageNum - 1) * pageSize;
            RecommendCourseData recommendCourseData = new RecommendCourseData();
            recommendCourseData.setCurrentPage(pageNum);
            List<GroupClass> groupClassList = mapper.getGroupClassListPage(firstIndex, pageSize);
            int count = mapper.getGroupClassList().size();
            recommendCourseData.setCount(count);
            List<TeamModel> teamModelList = new ArrayList<>();
            for (GroupClass groupClass : groupClassList) {
                List<CourseInfo> courseInfoList = mapper.getCourseInfoList(groupClass.getId());
                TeamModel teamModel = new TeamModel(groupClass.getId(), groupClass.getGroupName(), courseInfoList,groupClass.getSize());
                teamModelList.add(teamModel);
            }
            recommendCourseData.setData(teamModelList);
            return new DemonstrationResponse(0, "查询成功！", recommendCourseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public List<BrowseCourse> findCourseByUserIdLearnNum(String userid) {
        return mapper.findCourseByUserIdLearnNum(userid);
    }

    public void catTime(Timestamp time, String couseid) {
         mapper.catTime(time,couseid);
    }

    public List<BrowseCourse> findtime(String userid) {
        return mapper.findtime(userid);
    }

    public DemonstrationResponse updateGroupSort(
            List<GroupSortModel> groupSortModelList) {
        try {
            if(groupSortModelList.size()==0){
                mapper.delete();
            }
            List<GroupClass> groupClassList = new ArrayList<>();
            List<Integer> idList = new ArrayList<>();
            for (int i = 0; i < groupSortModelList.size(); i++) {
                idList.add(groupSortModelList.get(i).getGroupId());
                GroupClass groupClass = new GroupClass(groupSortModelList.get(i).getGroupId(), groupSortModelList.get(i).getGroupName(), i + 1,groupSortModelList.get(i).getSize());
                groupClassList.add(groupClass);

            }
            if(idList.size()>0){
                mapper.deleteGroup(idList);
            }
            if(groupClassList.size()>0){
                mapper.updateGroup(groupClassList);
            }
            for (GroupSortModel groupMember : groupSortModelList) {
                mapper.deleteGroupSort(groupMember.getGroupId());
                if (StringUtils.isNotEmpty(groupMember.getGroupMemberSort())) {
                    if(groupMember.getGroupMemberSort().contains(",")){
                        String[] s = groupMember.getGroupMemberSort().split(",");
                        List<GroupMember> groupMemberList = new ArrayList<>();
                        for (int i = 0; i < s.length; i++) {
                            GroupMember groupMember1 = new GroupMember(1, Integer.parseInt(s[i]), groupMember.getGroupId(), i + 1, "", "");
                            groupMemberList.add(groupMember1);
                        }
                        int row = mapper.updateGroupSort(groupMemberList);
                    }else {
                        GroupMember groupMember1 = new GroupMember(1, Integer.parseInt(groupMember.getGroupMemberSort()), groupMember.getGroupId(), 1, "", "");
                        mapper.addGroupMember(groupMember1);
                    }
                }
            }
            return new DemonstrationResponse(0, "添加成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }


    public void add(Integer id) {
        mapper.add(id);
    }

    public List<CatNumber> thridMonth(Integer id) {
        return   mapper.thridMonth(id);
    }

    public List<CatNumber> sixMonth(Integer id) {
        return   mapper.sixMonth(id);
    }

    public List<CatNumber> oneYear(Integer id) {
        return   mapper.oneYear(id);
    }
    public DemonstrationResponse addCourseReviews(
            CourseReviews courseReviews) {
        try {
            if (courseReviews == null) {
                return new DemonstrationResponse(-1, "请传入正确的数据！", null);
            }
            Date date = new Date();
            courseReviews.setReviewsDate(date);
            courseReviews.setStatus(0);
            int row = mapper.addCourseReviews(courseReviews);
            if (row > 0) {
                return new DemonstrationResponse(0, "添加成功！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public DemonstrationResponse getCourseReviewsList(Integer pageNum, Integer pageSize,String courseId
    ) {
        try {
            if (pageNum == null || pageNum <= 0) {
                pageNum = 1;
            }
            if (pageSize == null || pageSize <= 0) {
                pageSize = 10;
            }
            int firstIndex = (pageNum - 1) * pageSize;
            List<CourseReviews> courseReviews = new ArrayList<>();
            courseReviews = mapper.getCourseReviewsList(pageSize,firstIndex,courseId);
            int count = mapper.getCourseReviewsListCount(courseId);
            RecommendCourseData recommendCourseData = new RecommendCourseData(pageNum,count,courseReviews);
            return new DemonstrationResponse(0, "查询成功！", recommendCourseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }

    public List<CatNumber> week(Integer id) {
      return   mapper.week(id);
    }

    public List<CatNumber> Month(Integer id) {
        return   mapper.Month(id);
    }

    public void updateAiCourseOneTeacherAndChapListAndacts(String actsData, String teacherData, String chapterListData, String coursename, String source) {
        mapper.updateAiCourseOneTeacherAndChapListAndacts(actsData,teacherData,chapterListData,coursename,source);
    }

    public DemonstrationResponse getCourseTypeSourceList(String sortList) {

        try {
            if (StringUtils.isEmpty(sortList)) {
                return new DemonstrationResponse(-1, "顺序id不能为空", null);
            }
            if (sortList.contains(",")) {
                String[] ids = sortList.split(",");
                List<categoryCode> categoryCodeList = new ArrayList<>();
                for (int i = 0; i < ids.length; i++) {
                    categoryCode categoryCode = new categoryCode(Integer.parseInt(ids[i]), "", "",i + 1,1,1);
                    categoryCodeList.add(categoryCode);
                }
                int rows = mapper.updateCourseTypeSourceList(categoryCodeList);
            }
            return new DemonstrationResponse(0, "修改成功！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DemonstrationResponse(-1, "异常！", null);
    }



}
