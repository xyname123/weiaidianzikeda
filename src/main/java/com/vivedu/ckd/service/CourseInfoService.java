package com.vivedu.ckd.service;

import com.alibaba.fastjson.JSON;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.dao.CourseInfoMapper;
import com.vivedu.ckd.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
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

    public CourseInfoService(CourseInfoMapper mapper) {
        this.mapper = mapper;
    }



  public List<CourseInfo> findCourseByUserIdLearn(String userId) {
        return mapper.findCourseByUserId(userId);
    }

    public List<CourseInfo> findCourseByUserIdPage(String userid, Integer pageNum, Integer pageSize) {
        return mapper.findCourseByUserIdPage(userid,pageNum,pageSize);
    }


    public List<CourseInfo> findCourseByUserId(String userId, String sort, Integer pageNum, Integer pageSize) {
        return mapper.findCourseByUserIdIf(userId,sort,pageNum,pageSize);
    }

    public List<CourseInfo> findCourseByKey(String courseName, Integer pageNum, Integer pageSize) {
        return mapper.findCourseByKey(courseName,pageNum,pageSize);

    }

    public List<CourseInfo> findAllCourse(Integer pageNum, Integer pageSize) {
        return mapper.findAllCourse(pageNum,pageSize);

    }

    public List<CourseInfo> findCourse(Integer id,Integer pageNum, Integer pageSize) {
        return mapper.findCourse(id,pageNum ,pageSize );

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
        int row =0;
        if (timesStatistics != null) {
            timesStatistics.setTotalNumber(timesStatistics.getTotalNumber() + 1);
            if (type == 0) {
                timesStatistics.setDayNumber(timesStatistics.getDayNumber() + 1);
            }
            timesStatistics.setStatisticalDate(date);
            row =mapper.update(timesStatistics);
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
        StatisticsModel statisticsModel =mapper.getCountList();
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
        log.info("dataai数据" + dataai);
        for (int i = 1; i <= count; i++) {
            try {
                Thread.sleep(1000);
                sign = MD5Utils.MD5Encode("page=" + i + "&size=" + 500 + "&sort=" + "date" + "&key=" + keyi, "utf8").toUpperCase();
                s = restTemplate.getForObject("http://222.197.165.58:8080/api/courselist?enc=" + sign + "&page=" + i + "&size=" + 500 + "&sort=" + "date", String.class);
                String s1 = s.replace("members", "teacher");
                String s2 = s1.replace("chapters", "chapterList");
                Map mapai = (Map) JSON.parse(s2);
                String dataA = mapai.get("data").toString().trim();
                List<CourseInfoAiVo> CourseInfoAilist = JSONArray.toList(JSONArray.fromObject(dataA), new CourseInfoAiVo(), new JsonConfig());
                for (CourseInfoAiVo courseInfoAiVo : CourseInfoAilist) {
                    String courseid = courseInfoAiVo.getCourseid();
                    int met = courseInfoService.findAiVo(courseInfoAiVo.getCourseid());
                    if (met <= 0) {
                       // courseInfoService.InsertCourse(CourseInfoAilist);
                        //todo
                       String[] teacher = courseInfoAiVo.getTeacher();
                        String teacherData = Arrays.toString(teacher);
                        String[] chapterList = courseInfoAiVo.getChapterList();
                        String chapterListData = Arrays.toString(chapterList);
                        courseInfoService.InsertCourseOne(courseInfoAiVo);
                        courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData,chapterListData,courseInfoAiVo.getCoursename());
                    } else {
                       // courseInfoService.updateAi(CourseInfoAilist);
                        courseInfoService.updateAiCourse(courseInfoAiVo);
                        String[] teacher = courseInfoAiVo.getTeacher();
                        String teacherData = Arrays.toString(teacher);
                        String[] chapterList = courseInfoAiVo.getChapterList();
                        String chapterListData = Arrays.toString(chapterList);
                        courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData,chapterListData,courseInfoAiVo.getCoursename());

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

    public void updateAiCourseOneTeacherAndChapList(String teacherData,String chapterListData,String coursename) {
        mapper.updateAiCourseOneTeacherAndChapList(teacherData,chapterListData,coursename);
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
            List<CourseInfoMetel> CourseInfoMetelList = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoMetel(), new JsonConfig());
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
                    courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData,chapterListData,courseInfoMetel.getCoursename());
                } else {
                    courseInfoService.updateMeteOne(courseInfoMetel);
                    String[] teacher = courseInfoMetel.getTeacher();
                    String teacherData = Arrays.toString(teacher);
                    String[] chapterList = courseInfoMetel.getChapterlist();
                    String chapterListData = Arrays.toString(chapterList);
                    courseInfoService.updateAiCourseOneTeacherAndChapList(teacherData,chapterListData,courseInfoMetel.getCoursename());
                }

            }

        }
    }

    private void InsertCourseOneMe(CourseInfoMetel courseInfoMetel) {
        mapper.InsertCourseOneMe(courseInfoMetel);
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void updateFim() {
        String signKey = MD5Utils.MD5Encode("page=" + 1 + "size=" + 500 + "sort=" + "date" + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&enc=" + signKey, String.class);
        Map mapF = (Map) JSON.parse(ss);
        String datameF = (String) mapF.get("totalnum");
        int is = Integer.parseInt(datameF);
        int countmetelF = is / 100 + 1;
        for (int j = 1; j <= countmetelF; j++) {
            String ssD = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&enc=" + signKey, String.class);
            Map mapFssD = (Map) JSON.parse(ssD);
            String datametes = mapFssD.get("data").toString().trim();
            List<CourseInfoFilm> CourseInfoFilmlist = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoFilm(), new JsonConfig());
            for (CourseInfoFilm courseInfoFilm : CourseInfoFilmlist) {
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

        }

    }

    private void updateFilmeOne(CourseInfoFilm courseInfoFilm) {
        mapper.updateFilmeOne(courseInfoFilm);
    }

    private void updateCourseFilm(CourseInfoFilm courseInfoFilm) {
        mapper.updateCourseFilm(courseInfoFilm);
    }


    public List<CourseInfo> findCourseByfilmTop(String film, Integer page, Integer size) {

        return  mapper.findCourseByfilmTop(film,page,size);
    }

    public List<CourseInfo> findRec(Integer pageNum, Integer pageSize) {

        return  mapper.findRec(pageNum,pageSize);
    }

    public List<CourseInfo> recIndex(Integer pageNum, Integer pageSize) {
        return  mapper.recIndex(pageNum,pageSize);
    }

    public List<CourseInfo> findrquick(Integer pageNum, Integer pageSize) {
        return  mapper.findrquick(pageNum,pageSize);
    }

    public List<CourseInfo> findquickRec(Integer pageNum, Integer pageSize) {
        return  mapper.findquickRec(pageNum,pageSize);
    }

    public List<CourseInfo> findquickZiyuan(Integer pageNum, Integer pageSize) {
        return  mapper.findquickZiyuan(pageNum,pageSize);
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


    public DemonstrationResponse getRecommendCourseList(Integer isRec, Integer pageNum, Integer pageSize, String source, String courseName) {
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

        List<CourseInfo> courseInfos = mapper.getRecommendCourseList(isRec, firstIndex, pageSize,source,courseName);

        int courseInfosSize = mapper.getRecommendCourseListSize(isRec,source,courseName);
        RecommendCourseData recommendCourseData = new RecommendCourseData(pageNum, courseInfosSize, courseInfos);

       return new DemonstrationResponse(200, "获取成功！", recommendCourseData);
    }


    public DemonstrationResponse updateRecommendCourse(Integer isRec, Integer id) {
        if (isRec == null || isRec < 0) {
            isRec = 0;
        }
        if (id == null || id <= 0) {
            return new DemonstrationResponse(500, "请传入正确的id", null);
        }
        int row = mapper.updateRecommendCourse(isRec, id);
        if (row > 0) {
            return new DemonstrationResponse(200, "修改成功！", null);
        }
        return new DemonstrationResponse(500, "修改失败！", null);
    }

    public DemonstrationResponse getRecommendCourseListL(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        int firstIndex = (pageNum - 1) * pageSize;
        List<CourseInfo> courseInfos = mapper.getRecommendCourseListL(firstIndex,pageSize);
        int k =mapper.getRecommendCourseListK();
        RecommendCourseData recommendCourseData = new RecommendCourseData(pageNum,k,courseInfos);
        return new DemonstrationResponse(200, "获取成功！", recommendCourseData);
    }

    public int findCourseByUserIdNum(String userId) {

        return mapper.findCourseByUserIdNum(userId);
    }

    public int findCourseByKeyNum(String courseName) {
        return mapper.findCourseByKeyNum(courseName);
    }

    public int findAllCourseNum() {
        return mapper.findAllCourseNum();
    }

    public List<CourseInfo> findCoursetopState(Integer state, Integer page, Integer size) {
        /*state  1:正常 3:已完结 */


        return mapper.findCoursetopState(state,page,size);

    }

    public int findCoursetopStateT(Integer state) {
        return mapper.findCoursetopStateT(state);
    }




    public DemonstrationResponse addStudyDuration(String studentID, Integer time) {
        StudyDuration studyDuration = mapper.queryStudyDuration(studentID);
        int row = 0;
        if (studyDuration != null || studyDuration.getId() > 0) {
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
            return new DemonstrationResponse(200, "成功！", null);
        } else {
            return new DemonstrationResponse(500, "异常！", null);
        }
    }

    public DemonstrationResponse addLessonsLearned(BigInteger studentID, Integer courseId) {
        Date date = new Date();
        LessonsLearned lessonsLearned1 = mapper.queryLessonsLearned(studentID,courseId);
        log.info("lessonsLearned1"+lessonsLearned1);
        if (lessonsLearned1 != null || lessonsLearned1.getCourseId() > 0) {
            lessonsLearned1.setCreateDate(date);
            mapper.updateLessonsLearned(lessonsLearned1);
            return new DemonstrationResponse(200, "成功！", null);
        }
        LessonsLearned lessonsLearned = new LessonsLearned();
        lessonsLearned.setStudentID(studentID);
        lessonsLearned.setCourseId(courseId);
        lessonsLearned.setCreateDate(date);
        int row = mapper.addLessonsLearned(lessonsLearned);
        if (row > 0) {
            return new DemonstrationResponse(200, "成功！", null);
        } else {
            return new DemonstrationResponse(500, "异常！", null);
        }
    }


    public DemonstrationResponse getPersonalCenterInfo(String userId) throws Exception {
        if (StringUtils.isEmpty(userId) || userId.length() < 7) {
            return new DemonstrationResponse(500, "请传入正确的用户ID！", null);
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
            //查出老师课程userid塞入实体类
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
            return new DemonstrationResponse(200, "查询成功！", studentPersonalCenterInfo);
        } else if (userId.length() >= 12) {
            int courseCount = mapper.queryCourseCount(userId, startDate, endDate);
            int courseEnd = mapper.queryCourseEnd(userId, startDate, endDate);
            int rank = mapper.queryRank(userId, startDate, endDate);
            int allCount = mapper.queryAllCount(userId, startDate, endDate);
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
            return new DemonstrationResponse(200, "查询成功！", studentPersonalCenterInfo);
        } else {
            return new DemonstrationResponse(500, "没有用户ID数据！", null);
        }
    }

    public int findCourseDatalCoutnt(Integer id) {

       return mapper.findCourseDatalCoutnt(id);
    }

    public void updateStudyCount(Integer id, int i) {
         mapper.updateStudyCount(id,i);
    }

    public void InsertCourseDan(CourseInfoAiVo courseInfoAiVo) {
        mapper.InsertCourseDan(courseInfoAiVo);
    }

    public void InsertCourseDanTeacher(String coursename, String teacher) {
        mapper.InsertCourseDanTeacher(coursename,teacher);
    }

    public void updateMeteOne(CourseInfoMetel courseInfoMetel) {
        mapper.updateMeteOne(courseInfoMetel);
    }
}
