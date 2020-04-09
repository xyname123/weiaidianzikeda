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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        String metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + 1 + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
        log.info("metel" + metel);
        Map mapmetel = (Map) JSON.parse(metel);
        Integer datametel = (Integer) mapmetel.get("totalnum");
        int countmetel = datametel / 100 + 1;
        for (int j = 1; j <= countmetel; j++) {

            try {
                metel = restTemplate.getForObject("http://uestc.connect.metel.cn/api/courselist?page=" + j + "&size=" + 100 + "&sort=date" + "&enc=" + 123456, String.class);
                log.info("metel---"+metel);
            } catch (RestClientException e) {
                e.printStackTrace();
            }

        }

    }
}