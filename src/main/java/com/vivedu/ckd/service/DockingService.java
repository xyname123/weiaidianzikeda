package com.vivedu.ckd.service;

import com.alibaba.fastjson.JSON;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivedu.ckd.model.*;
import com.vivedu.ckd.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    public void findAllCourse(Integer page, Integer size, String sort) {
        //爱课堂
        if (page == null || size == null) page = 1;
        size = 500;
        if (StringUtils.isEmpty(sort)) sort = "date";


        String sign = MD5Utils.MD5Encode("page=" + page + "&size=" + 500 + "&sort=" + sort + "&key=" + keyi, "utf8").toUpperCase();

        String s = restTemplate.getForObject("http://222.197.165.58:8080/api/courselist?enc=" + sign + "&page=" + page + "&size=" + 500 + "&sort=" + sort, String.class);

        Map map = (Map) JSON.parse(s);
        Integer data = (Integer) map.get("totalnum");
        int count = data / 500 + 1;
        log.info("count---" + count);
        String dataai = map.get("data").toString().trim();
        //  log.info("dataai数据" + dataai);
        for (int i = 1; i <= 1; i++) {
            try {
                Thread.sleep(1000);
                sign = MD5Utils.MD5Encode("page=" + i + "&size=" + size + "&sort=" + sort + "&key=" + keyi, "utf8").toUpperCase();
                s = restTemplate.getForObject("http://222.197.165.58:8080/api/courselist?enc=" + sign + "&page=" + i + "&size=" + size + "&sort=" + sort, String.class);
                String s1 = s.replace("members", "teacher");
                String s2 = s1.replace("chapters", "chapterList");
                Map mapai = (Map) JSON.parse(s2);
                String dataA = mapai.get("data").toString().trim();
                log.info("dataA----------------" + dataA);
                List<CourseInfoAiVo> CourseInfoAilist = JSONArray.toList(JSONArray.fromObject(dataA), new CourseInfoAiVo(), new JsonConfig());
              //log.info("CourseInfoAilist-"+CourseInfoAilist);
            //    courseInfoService.InsertCourse(CourseInfoAilist);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        //todo--------------------------metel                     uestc.connect.metel.cn
        String metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + 1 + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
        Map mapmetel = (Map) JSON.parse(metel);
        Integer datametel = (Integer) mapmetel.get("totalnum");
        int countmetel = datametel / 100 + 1;
        String datametelx = mapmetel.get("data").toString().trim();
        log.info("metel--" + datametelx);

        for (int j = 1; j <= 1; j++) {

            metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + j + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
            Map mapmete2 = (Map) JSON.parse(metel);
            log.info("mapmete2" + mapmete2);
            String datametes = mapmete2.get("data").toString().trim();
            log.info("metel-------------"+datametes);
            List<CourseInfoMetel> CourseInfoMetelList = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoMetel(), new JsonConfig());

                 //   courseInfoService.InsertCourseMetel(CourseInfoMetelList);


        }
    }


            //film  没有进行大写转换
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

