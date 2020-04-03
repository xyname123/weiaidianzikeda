package com.vivedu.ckd.service;

import com.alibaba.fastjson.JSON;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.utils.MD5Utils;

import com.vivedu.ckd.utils.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;

import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j

public class DockingService {


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
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private RestTemplate restTemplate;

    public static ThreadPoolExecutor cancelPool = new ThreadPoolExecutor(
            10, 10,
            1, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(80, false),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    /**
     * allcourse
     */
    // @Scheduled(cron = "0 0/30 * * * ?")
    public void findAllCourse(Integer page, Integer size, String sort) throws InterruptedException {
    /*    String sign = MD5Utils.MD5Encode("page=" + 1 + "&size=" + 500 + "&sort=" + "date" + "&key=" + keyi, "utf8").toUpperCase();

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

        }*/
      String metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + 1 + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
        Map mapmetel = (Map) JSON.parse(metel);
        Integer datametel = (Integer) mapmetel.get("totalnum");
        int countmetel = datametel / 100 + 1;
        String datametelx = mapmetel.get("data").toString().trim();
        log.info("metel--" + datametelx);

        for (int j = 1; j <= countmetel; j++) {
            int index = j;

            cancelPool.execute(new Runnable() {
                @Override
                public void run() {
                    String metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + index + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
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
            });
        }
    }
    }



