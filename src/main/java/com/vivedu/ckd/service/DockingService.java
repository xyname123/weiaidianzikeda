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
        log.info("dataai数据" + dataai);
        for (int i = 1; i <= count; i++) {
            try {
                Thread.sleep(1000);
                sign = MD5Utils.MD5Encode("page=" + i + "&size=" + size + "&sort=" + sort + "&key=" + keyi, "utf8").toUpperCase();
                s = restTemplate.getForObject("http://222.197.165.58:8080/api/courselist?enc=" + sign + "&page=" + i + "&size=" + size + "&sort=" + sort, String.class);
                String s1 = s.replace("members", "teacher");
                String s2 = s1.replace("chapters", "chapterList");
                Map mapai = (Map) JSON.parse(s2);
                String dataA = mapai.get("data").toString().trim();
                log.info("dataA----------------"+dataA);
                List<CourseInfoAiVo> CourseInfoAilist = JSONArray.toList(JSONArray.fromObject(dataA), new CourseInfoAiVo(), new JsonConfig());
                //todo

                // courseInfoService.InsertCourse(CourseInfoAilist);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


/*        //todo--------------------------metel                     uestc.connect.metel.cn
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

                    courseInfoService.InsertCourseMetel(CourseInfoMetelList);


        }
    }*/


            //film  没有进行大写转换
      /*  String signKey = MD5Utils.MD5Encode("page=" + page + "size=" + 500 + "sort=" + sort + keyo, "utf8");
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
            log.info("datametes--------------"+datametes);
            List<CourseInfoFilm> CourseInfoFilmlist = JSONArray.toList(JSONArray.fromObject(datametes), new CourseInfoFilm(), new JsonConfig());

                    courseInfoService.updateCourse(CourseInfoFilmlist);




        }*/








  /*  public HashMap<String, Object> findCourse(String courseId, Integer page, Integer size, String sort) {
        //爱课堂
        if (page == null || size == null) page = 1;
        size = 10;
        if (StringUtils.isEmpty(sort)) sort = "date";
        String sign = MD5Utils.MD5Encode("courseid=" + 50745 + "&page=" + page + "&size=" + size + "&sort=" + sort + "&key=" + keyi, "utf8").toUpperCase();
        String s = restTemplate.getForObject("http://222.197.164.130:81/api/courselist?enc=" + sign + "&courseid=" + 50745 + "&page=" + page + "&size=" + size + "&sort=" + sort, String.class);
        Map map = (Map) JSON.parse(s);
        String data = String.valueOf(map.get("data"));
        data = StringEscapeUtils.unescapeJava(data);
        //mails
     *//*   String mails = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?courseid=" + courseId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&enc=" + 123456, String.class);
        Map mapls = (Map) JSON.parse(mails);
        String datals = String.valueOf(mapls.get("data"));
        datals = StringEscapeUtils.unescapeJava(datals);*//*

             *//*      //film  没有进行大写转换
        String signKey = MD5Utils.MD5Encode("courseid=" + courseId + "page=" + page + "size=" + size + "sort=" + sort + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?courseid=" + courseId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&enc=" + signKey, String.class);
        Map mapF = (Map) JSON.parse(ss);
        HashMap<String, Object> TotalMap = new HashMap<>();
        // Integer totalnum = Integer.parseInt(map.get("totalnum").toString());
        Integer totalnum1 = Integer.parseInt(mapF.get("totalnum").toString());
        Integer totalnum2 = Integer.parseInt(mapls.get("totalnum").toString());
        String dataF = String.valueOf(mapF.get("data"));
        dataF = StringEscapeUtils.unescapeJava(dataF);



        // data = data.substring(0, data.length() - 1) + ",";
        dataF = dataF.substring(2);
        // String datatal = data + dataF;
        String  datatal = dataF.substring(0, dataF.length() - 1) + ",";
        datals = datals.substring(2);
        TotalMap.put("totalnum", totalnum1 + totalnum2);
        TotalMap.put("status", "true");
        TotalMap.put("data", datatal + datals);

        return null;*//*
        return null;
    }

    public HashMap<String, Object> findCourseByKey(String keywords, Integer page, Integer size, String sort) {
        //爱课堂
        if (page == null || size == null) page = 1;
        size = 10;
        if (StringUtils.isEmpty(sort)) sort = "date";
       *//* String sign = MD5Utils.MD5Encode("keywords=" + keywords + "&page=" + page + "&size=" + size + "&sort=" + sort + "&key=" + keyi, "utf8").toUpperCase();
        String s = restTemplate.getForObject("http://222.197.164.130:81/api/courselist?enc=" + sign + "&keywords=" + keywords + "&page=" + page + "&size=" + size + "&sort=" + sort, String.class);
        Map map = (Map) JSON.parse(s);*//*
        //mails
        String mails = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist??keywords=" + keywords + "&page=" + page + "&size=" + size + "&sort=" + sort + "&enc=" + 123456, String.class);
        Map mapls = (Map) JSON.parse(mails);


        //film  没有进行大写转换
        String signKey = MD5Utils.MD5Encode("keywords=" + keywords + "page=" + page + "size=" + size + "sort=" + sort + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?keywords=" + keywords + "&page=" + page + "&size=" + size + "&sort=" + sort + "&enc=" + signKey, String.class);
        Map mapF = (Map) JSON.parse(ss);

        HashMap<String, Object> TotalMap = new HashMap<>();
        // Integer totalnum = Integer.parseInt(map.get("totalnum").toString());
        Integer totalnum1 = Integer.parseInt(mapF.get("totalnum").toString());
        Integer totalnum2 = Integer.parseInt(mapls.get("totalnum").toString());
        // String data = String.valueOf(map.get("data"));
        // data = StringEscapeUtils.unescapeJava(data);
        String dataF = String.valueOf(mapF.get("data"));
        dataF = StringEscapeUtils.unescapeJava(dataF);
        String datals = String.valueOf(mapls.get("data"));
        datals = StringEscapeUtils.unescapeJava(datals);
        // data = data.substring(0, data.length() - 1) + ",";
        dataF = dataF.substring(2);
        // String datataLx = data + dataF;
        dataF = dataF.substring(0, dataF.length() - 1) + ",";
        datals = datals.substring(2);
        TotalMap.put("totalnum", totalnum1 + totalnum2);
        TotalMap.put("status", "true");
        TotalMap.put("data", dataF + datals);
        TotalMap.put("status", "true");
        TotalMap.put("data", datals + dataF);
        return TotalMap;
    }

    public HashMap<String, Object> findCourseByUserId(String userId, String sort, Integer page, Integer size) {
        //爱课堂
        if (page == null || size == null) page = 1;
        size = 10;
        if (StringUtils.isEmpty(sort)) sort = "date";
        String sign = MD5Utils.MD5Encode("page=" + page + "&size=" + size + "&sort=" + sort + "userId" + userId + "&key=" + keyi, "utf8").toUpperCase();
        String s = restTemplate.getForObject("http://222.197.164.130:81/api/courselist?enc=" + sign + "&userId=" + userId + "&page=" + page + "&size=" + size + "&sort=" + sort, String.class);
        Map map = (Map) JSON.parse(s);

        //mails
        String mails = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist??userId=" + userId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&enc=" + 123456, String.class);
        Map mapls = (Map) JSON.parse(mails);

        //film  没有进行大写转换
        String signKey = MD5Utils.MD5Encode("page=" + page + "size=" + size + "sort=" + sort + "userId=" + userId + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/courseList?userId=" + userId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&enc=" + signKey, String.class);
        Map mapF = (Map) JSON.parse(ss);

        HashMap<String, Object> TotalMap = new HashMap<>();
        Integer totalnum = Integer.parseInt(map.get("totalnum").toString());
        Integer totalnum1 = Integer.parseInt(mapF.get("totalnum").toString());
        Integer totalnum2 = Integer.parseInt(mapls.get("totalnum").toString());
        String data = String.valueOf(map.get("data"));
        data = StringEscapeUtils.unescapeJava(data);
        String dataF = String.valueOf(mapF.get("data"));
        dataF = StringEscapeUtils.unescapeJava(dataF);
        String datals = String.valueOf(mapls.get("data"));
        datals = StringEscapeUtils.unescapeJava(datals);
        data = data.substring(0, data.length() - 1) + ",";
        dataF = dataF.substring(2);
        String datataLx = data + dataF;
        datataLx = datataLx.substring(0, data.length() - 1) + ",";
        datals = datals.substring(2);
        TotalMap.put("totalnum", totalnum + totalnum1 + totalnum2);
        TotalMap.put("status", "true");
        TotalMap.put("data", datataLx + datals);
        TotalMap.put("totalnum", totalnum + totalnum1);
        TotalMap.put("status", "true");
        TotalMap.put("data", data + dataF);
        return TotalMap;
    }


    public HashMap<String, Object> findCourseByUserIdLearn(String userId, Integer page, Integer size) {
        //爱课堂
        if (page == null || size == null) page = 1;
        size = 10;
        String sign = MD5Utils.MD5Encode("page=" + page + "&size=" + size + "&userid=" + userId + "&key=" + keyi, "utf8").toUpperCase();
        String s = restTemplate.getForObject("http://222.197.164.130:81/api/learninglist?enc=" + sign + "&userid=" + userId + "&page=" + page + "&size=" + size, String.class);
        Map map = (Map) JSON.parse(s);

        //mails
        String mails = restTemplate.getForObject("http://uestc.connect.metel.cn/api/learninglist?enc=" + 123456 + "&userid=" + userId + "&page=" + page + "&size=" + size, String.class);
        Map mapls = (Map) JSON.parse(mails);

        //film  没有进行大写转换
        String signKey = MD5Utils.MD5Encode("page=" + page + "size=" + size + "userid=" + userId + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/learninglist?userid=" + userId + "&page=" + page + "&size=" + size + "&enc=" + signKey, String.class);
        Map mapF = (Map) JSON.parse(ss);

        HashMap<String, Object> TotalMap = new HashMap<>();
        Integer totalnum = Integer.parseInt(map.get("totalnum").toString());
        Integer totalnum1 = Integer.parseInt(mapF.get("totalnum").toString());
        Integer totalnum2 = Integer.parseInt(mapls.get("totalnum").toString());
        String data = String.valueOf(map.get("data"));
        data = StringEscapeUtils.unescapeJava(data);
        String dataF = String.valueOf(mapF.get("data"));
        dataF = StringEscapeUtils.unescapeJava(dataF);
        String datals = String.valueOf(mapls.get("data"));
        datals = StringEscapeUtils.unescapeJava(datals);
        data = data.substring(0, data.length() - 1) + ",";
        dataF = dataF.substring(2);
        String datataLx = data + dataF;
        datataLx = datataLx.substring(0, data.length() - 1) + ",";
        datals = datals.substring(2);
        TotalMap.put("totalnum", totalnum + totalnum1 + totalnum2);
        TotalMap.put("status", "true");
        TotalMap.put("data", datataLx + datals);
        TotalMap.put("totalnum", totalnum + totalnum1);
        TotalMap.put("status", "true");
        TotalMap.put("data", data + dataF);
        return TotalMap;
    }


    public HashMap<String, Object> courseInfoByMarked(String userId, Integer page, Integer size) {
        //爱课堂
        if (page == null || size == null) page = 1;
        size = 10;
        String sign = MD5Utils.MD5Encode("page=" + page + "&size=" + size + "&userid=" + userId + "&key=" + keyi, "utf8").toUpperCase();
        String s = restTemplate.getForObject("http://222.197.164.130:81/api/markedlist?enc=" + sign + "&userid=" + userId + "&page=" + page + "&size=" + size, String.class);
        Map map = (Map) JSON.parse(s);

        //mails
        String mails = restTemplate.getForObject("http://uestc.connect.metel.cn/api/markedlist?enc=" + 123456 + "&userid=" + userId + "&page=" + page + "&size=" + size, String.class);
        Map mapls = (Map) JSON.parse(mails);

        //film  没有进行大写转换
        String signKey = MD5Utils.MD5Encode("page=" + page + "size=" + size + "userid=" + userId + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/markedlist?userid=" + userId + "&page=" + page + "&size=" + size + "&enc=" + signKey, String.class);

        Map mapF = (Map) JSON.parse(ss);
        HashMap<String, Object> TotalMap = new HashMap<>();
        Integer totalnum = Integer.parseInt(map.get("totalnum").toString());
        Integer totalnum1 = Integer.parseInt(mapF.get("totalnum").toString());
        Integer totalnum2 = Integer.parseInt(mapls.get("totalnum").toString());
        String data = String.valueOf(map.get("data"));
        data = StringEscapeUtils.unescapeJava(data);
        String dataF = String.valueOf(mapF.get("data"));
        dataF = StringEscapeUtils.unescapeJava(dataF);
        String datals = String.valueOf(mapls.get("data"));
        datals = StringEscapeUtils.unescapeJava(datals);
        data = data.substring(0, data.length() - 1) + ",";
        dataF = dataF.substring(2);
        String datataLx = data + dataF;
        datataLx = datataLx.substring(0, data.length() - 1) + ",";
        datals = datals.substring(2);
        TotalMap.put("totalnum", totalnum + totalnum1 + totalnum2);
        TotalMap.put("status", "true");
        TotalMap.put("data", datataLx + datals);
        TotalMap.put("data", data + dataF);
        return TotalMap;
    }

    public HashMap<String, Object> findCourseByUserIdPage(String userId, Integer page, Integer size) {
        //爱课堂
        if (page == null || size == null) page = 1;
        size = 10;
        String sign = MD5Utils.MD5Encode("page=" + page + "&size=" + size + "&userid=" + userId + "&key=" + keyi, "utf8").toUpperCase();
        String s = restTemplate.getForObject("http://222.197.164.130:81/api/opencourselist?enc=" + sign + "&userid=" + userId + "&page=" + page + "&size=" + size, String.class);
        log.info(s);
        Map map = (Map) JSON.parse(s);
        //film  没有进行大写转换

        String signKey = MD5Utils.MD5Encode("page=" + page + "size=" + size + "userid=" + userId + keyo, "utf8");

        log.info("http://film.uestc.edu.cn/api/opencourselist?userid=" + userId + "&page=" + page + "&size=" + size + "&enc=" + signKey);
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/opencourselist?userid=" + userId + "&page=" + page + "&size=" + size + "&enc=" + signKey, String.class);

        Map mapF = (Map) JSON.parse(ss);
        HashMap<String, Object> TotalMap = new HashMap<>();
        log.info(StringEscapeUtils.unescapeJava(ss));
        Integer totalnum = Integer.parseInt(map.get("totalnum").toString());
        Integer totalnum1 = Integer.parseInt(mapF.get("totalnum").toString());


        String data = String.valueOf(map.get("data"));
        data = StringEscapeUtils.unescapeJava(data);
        data = data.substring(0, data.length() - 1) + ",";

        String dataF = String.valueOf(mapF.get("data"));
        dataF = StringEscapeUtils.unescapeJava(dataF);
        dataF = dataF.substring(2);

        TotalMap.put("totalnum", totalnum + totalnum1);
        TotalMap.put("status", "true");
        TotalMap.put("data", data + dataF);

        return TotalMap;

    }

    public HashMap<String, Object> teachingData(String courseId, String departmentId, Integer page, Integer size, String sort, String subjecteId, String term, String userId) {
        //爱课堂
        if (page == null || size == null) page = 1;
        size = 10;
        if (StringUtils.isEmpty(sort)) sort = "date";
        if (StringUtils.isNotEmpty(courseId) && StringUtils.isNotEmpty(departmentId)
                && StringUtils.isNotEmpty(subjecteId) && StringUtils.isNotEmpty(term)
                && StringUtils.isNotEmpty(userId)) {
            keyiurl = "courseid=" + courseId + "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId + "&term=" + term + "&userid=" + userId;
            keyourl = "courseid=" + courseId + "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId + "term=" + term + "userid=" + userId;
        }
        if (StringUtils.isNotEmpty(courseId) && StringUtils.isEmpty(departmentId)
                && StringUtils.isEmpty(subjecteId) && StringUtils.isEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "courseid=" + courseId + "&page=" + page + "&size=" + size + "&sort=" + sort;
            keyourl = "courseid=" + courseId + "page=" + page + "size=" + size + "sort=" + sort;
        }
        if (StringUtils.isNotEmpty(courseId) && StringUtils.isNotEmpty(departmentId) && StringUtils.isEmpty(subjecteId) && StringUtils.isEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "courseid=" + courseId + "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort;
            keyourl = "courseid=" + courseId + "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort;

        }
        if (StringUtils.isNotEmpty(courseId) && StringUtils.isNotEmpty(departmentId) && StringUtils.isNotEmpty(subjecteId) && StringUtils.isEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "courseid=" + courseId + "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId;
            keyourl = "courseid=" + courseId + "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId;
        }

        if (StringUtils.isNotEmpty(courseId) && StringUtils.isNotEmpty(departmentId) && StringUtils.isNotEmpty(subjecteId) && StringUtils.isNotEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "courseid=" + courseId + "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId + "&term=" + term;
            keyourl = "courseid=" + courseId + "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId + "term=" + term;
        }

        if (StringUtils.isNotEmpty(courseId) && StringUtils.isNotEmpty(departmentId) && StringUtils.isEmpty(subjecteId) && StringUtils.isEmpty(term)
                && StringUtils.isNotEmpty(userId)) {
            keyiurl = "courseid=" + courseId + "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId + "&term=" + term + "&userid=" + userId;
            keyourl = "courseid=" + courseId + "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId + "term=" + term + "userid=" + userId;
        }
        if (StringUtils.isEmpty(courseId) && StringUtils.isNotEmpty(departmentId) && StringUtils.isEmpty(subjecteId) && StringUtils.isEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort;
            keyourl = "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort;
        }
        if (StringUtils.isEmpty(courseId) && StringUtils.isNotEmpty(departmentId) && StringUtils.isNotEmpty(subjecteId) && StringUtils.isEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId;
            keyourl = "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId;
        }
        if (StringUtils.isEmpty(courseId) && StringUtils.isNotEmpty(departmentId) && StringUtils.isNotEmpty(subjecteId) && StringUtils.isNotEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId + "&term=" + term;
            keyourl = "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId + "term=" + term;
        }

        if (StringUtils.isEmpty(courseId) && StringUtils.isNotEmpty(departmentId) && StringUtils.isNotEmpty(subjecteId) && StringUtils.isNotEmpty(term)
                && StringUtils.isNotEmpty(userId)) {
            keyiurl = "&departmentid=" + departmentId + "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId + "&term=" + term + "&userid=" + userId;
            keyourl = "departmentid=" + departmentId + "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId + "term=" + term + "userid=" + userId;
        }
        if (StringUtils.isEmpty(courseId) && StringUtils.isEmpty(departmentId) && StringUtils.isNotEmpty(subjecteId) && StringUtils.isEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId;
            keyourl = "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId;
        }
        if (StringUtils.isEmpty(courseId) && StringUtils.isEmpty(departmentId) && StringUtils.isNotEmpty(subjecteId) && StringUtils.isNotEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId + "&term=" + term;
            keyourl = "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId + "term=" + term;
        }
        if (StringUtils.isEmpty(courseId) && StringUtils.isEmpty(departmentId) && StringUtils.isNotEmpty(subjecteId) && StringUtils.isNotEmpty(term)
                && StringUtils.isNotEmpty(userId)) {
            keyiurl = "&page=" + page + "&size=" + size + "&sort=" + sort + "&subjecteid=" + subjecteId + "&term=" + term + "&userid=" + userId;
            keyourl = "page=" + page + "size=" + size + "sort=" + sort + "subjecteid=" + subjecteId + "term=" + term + "userid=" + userId;
        }

        if (StringUtils.isEmpty(courseId) && StringUtils.isEmpty(departmentId) && StringUtils.isEmpty(subjecteId) && StringUtils.isNotEmpty(term)
                && StringUtils.isEmpty(userId)) {
            keyiurl = "&page=" + page + "&size=" + size + "&sort=" + sort + "&term=" + term;
            keyourl = "page=" + page + "size=" + size + "sort=" + sort + "term=" + term;
        }

        if (StringUtils.isEmpty(courseId) && StringUtils.isEmpty(departmentId) && StringUtils.isEmpty(subjecteId) && StringUtils.isNotEmpty(term)
                && StringUtils.isNotEmpty(userId)) {
            keyiurl = "&page=" + page + "&size=" + size + "&sort=" + sort + "&term=" + term + "&userid=" + userId;
            keyourl = "page=" + page + "size=" + size + "sort=" + sort + "term=" + term + "userid=" + userId;
        }
        log.info("===========" + keyiurl + "&key=" + keyi);
        String sign = MD5Utils.MD5Encode(keyiurl + "&key=" + keyi, "utf8").toUpperCase();
        log.info("========" + sign);
        //sort=date&page=1&size=10
        String s = restTemplate.getForObject("http://222.197.164.130:81/api/teachingdata?enc=" + sign + "&" + keyiurl, String.class);
        Map map = (Map) JSON.parse(s);
        //film  没有进行大写转换
        String signKey = MD5Utils.MD5Encode(keyourl + keyo, "utf8");
        String ss = restTemplate.getForObject("http://film.uestc.edu.cn/api/teachingdata?enc=" + signKey + "&" + keyiurl, String.class);
        Map mapF = (Map) JSON.parse(ss);
        HashMap<String, Object> TotalMap = new HashMap<>();

        Integer totalnum = Integer.parseInt(map.get("totalnum").toString());
        Integer totalnum1 = Integer.parseInt(mapF.get("totalnum").toString());

        String data = String.valueOf(map.get("data"));
        data = StringEscapeUtils.unescapeJava(data);
        data = data.substring(0, data.length() - 1) + ",";

        String dataF = String.valueOf(mapF.get("data"));
        dataF = StringEscapeUtils.unescapeJava(dataF);
        dataF = dataF.substring(2);
        TotalMap.put("totalnum", totalnum + totalnum1);
        TotalMap.put("status", "true");
        TotalMap.put("data", data + dataF);

        return TotalMap;
*/
        }}}
