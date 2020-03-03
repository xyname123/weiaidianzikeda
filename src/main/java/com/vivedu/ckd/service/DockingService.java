package com.vivedu.ckd.service;

import com.alibaba.fastjson.JSON;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;

import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j

public class DockingService {

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

    /**
     * allcourse
     */
    // @Scheduled(cron = "0 0/30 * * * ?")
    public void findAllCourse(Integer page, Integer size, String sort) throws InterruptedException {
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



        //todo--------------------------metel                     uestc.connect.metel.cn
      /*  for (int j = 1; j <= 20; j++) {

            String metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + j + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
            log.info("metel" + metel);
            String metel1 = metel.replace("coursename_en", "coursenameEn");
            String s2 = metel1.replace("profile_en", "profileEn");
            Map mapmete2 = (Map) JSON.parse(s2);
            log.info("mapmete2" + mapmete2);
            String datametes = mapmete2.get("data").toString().trim();

            List<CourseInfoMetel> CourseInfoMetelList = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoMetel(), new JsonConfig());

            for (CourseInfoMetel courseInfoMetel : CourseInfoMetelList) {
log.info("courseInfoMetel----------"+courseInfoMetel);
                  //  courseInfoService.updateMeteOne(courseInfoMetel);
                }
            }
        }
*/

   /*         //film  没有进行大写转换
       String signKey = MD5Utils.MD5Encode("page=" + page + "size=" + 500 + "sort=" + sort + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + page + "&size=" + 500 + "&sort=" + sort + "&enc=" + signKey, String.class);
        Map mapF = (Map) JSON.parse(ss);
        String datameF = (String) mapF.get("totalnum");
        int is = Integer.parseInt(datameF);
        int countmetelF = is / 100 + 1;
        //todo--------------------------film
        for (int j = 1; j <= countmetelF; j++) {
            log.info("charucishu"+j);
            String ssD = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?page=" + page + "&size=" + 500 + "&sort=" + sort + "&enc=" + signKey, String.class);
            Map mapFssD = (Map) JSON.parse(ssD);
            String datametes = mapFssD.get("data").toString().trim();
            log.info("film--------------"+datametes);
            List<CourseInfoFilm> CourseInfoFilmlist = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoFilm(), new JsonConfig());
                    //courseInfoService.updateCourse(CourseInfoFilmlist);
        }


        }
        }
    }*/
            }}


