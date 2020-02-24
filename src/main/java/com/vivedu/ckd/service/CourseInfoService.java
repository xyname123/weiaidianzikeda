package com.vivedu.ckd.service;

import com.alibaba.fastjson.JSON;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.dao.CourseInfoMapper;
import com.vivedu.ckd.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
                //todo

                for (CourseInfoAiVo courseInfoAiVo : CourseInfoAilist) {

                    int met = courseInfoService.findAiVo(courseInfoAiVo.getCourseid());
                    if (met <= 0) {
                        courseInfoService.InsertCourse(CourseInfoAilist);
                    } else {
                        courseInfoService.updateAi(CourseInfoAilist);
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
            Map mapmete2 = (Map) JSON.parse(metel);
            log.info("mapmete2" + mapmete2);
            String datametes = mapmete2.get("data").toString().trim();
            List<CourseInfoMetel> CourseInfoMetelList = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoMetel(), new JsonConfig());
            for (CourseInfoMetel courseInfoMetel : CourseInfoMetelList) {
                courseInfoMetel.setSoure("MeTel");
                int met = courseInfoService.findMete(courseInfoMetel.getCoursename());
                if (met <= 0) {
                    courseInfoService.InsertCourseMetel(CourseInfoMetelList);
                } else {
                    courseInfoService.updateMete(CourseInfoMetelList);
                }

            }

        }
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void updateFim() {
        String signKey = MD5Utils.MD5Encode("page=" + 1 + "size=" + 500 + "sort=" + "date" + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&enc=" + signKey, String.class);
        Map mapF = (Map) JSON.parse(ss);
        String datameF = (String) mapF.get("totalnum");
        int is = Integer.parseInt(datameF);
        int countmetelF = is / 100 + 1;
        //todo--------------------------film
        for (int j = 1; j <= countmetelF; j++) {
            String ssD = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&enc=" + signKey, String.class);
            Map mapFssD = (Map) JSON.parse(ssD);
            String datametes = mapFssD.get("data").toString().trim();
            List<CourseInfoFilm> CourseInfoFilmlist = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoFilm(), new JsonConfig());
            for (CourseInfoFilm courseInfoFilm : CourseInfoFilmlist) {
                int met = courseInfoService.findFilmT(courseInfoFilm.getCourseid());
                if (met <= 0) {
                    courseInfoService.updateCourse(CourseInfoFilmlist);
                } else {
                    courseInfoService.updateFilm(CourseInfoFilmlist);
                }
            }


        }

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

    public List<CourseInfo> findCourseDatal(Integer id, Integer pageNum, Integer pageSize) {

        return mapper.findCourseDatal(id,pageNum ,pageSize );
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

    public int findMete(String courseInfoMetel) {

        return mapper.findMete(courseInfoMetel);
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
}
