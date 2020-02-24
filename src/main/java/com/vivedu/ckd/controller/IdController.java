package com.vivedu.ckd.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/api")
public class IdController {
    @Autowired
    private RestTemplate restTemplate;
    private String idsLoginUrl = "http://idas.uestc.edu.cn/authserver";
    private String idsValidateUrl = "http://idas.uestc.edu.cn/authserver/serviceValidate";
    private String redirectUrl = "";

    //http://idas.uestc.edu.cn/authserver?service=http%3a%2f%2fstudy.uestc.cn%2f
    @GetMapping(path = "/ticketone", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "ticket")
    public String courseInfoByUserId(
            @RequestParam(required = true, value = "ticketone") @ApiParam(value = "ticket编号")
                    String ticketone) {

//当前域名 , 并进行 UrlEncode
         //String service = UrlEncode("http://study3.uestc.edu.cn/");
        String service = "http://study.uestc.cn/";

//如果tiket不存在，返回前端 无
        if (StringUtils.isEmpty(ticketone)) {
            //返回json 无
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("uid", "ticketone无数据");
            return JSON.toJSONString(objectObjectHashMap);
        }
//如果有tiket 进行统一身份认证验证,serviceUrl进行st的校验.


        String validateUrl = idsValidateUrl + "?ticket=" + ticketone + "&service=" + service;

        String xmlString = restTemplate.getForObject(validateUrl, String.class);
        if (xmlString.contains("未能够识别出目标")){
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("false", "认证失败");
            return JSON.toJSONString(objectObjectHashMap);
        }
        log.info("----------------" + xmlString);
        ////获取认证返回的结果.
        //该结果 为 账户信息的结果.XML格式的,取出 <cas:uid>1234567</cas:uid>,转换为 json返回前端

        //todo
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlString);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();
        log.info("根节点：" + root);
//cas:authenticationSuccess
        Iterator channel = root.elementIterator("authenticationSuccess");
        log.info("channel" + channel);
        while (channel.hasNext()) {
            Element recordEle = (Element) channel.next();
            log.info("recordEle" + recordEle);
            //获取cas:attributes节点
            Iterator item = recordEle.elementIterator("attributes");
            log.info("item" + item);
            while (item.hasNext()) {
                Element abkRecord = (Element) item.next();
                String uid = abkRecord.elementTextTrim("uid");
                String cn = abkRecord.elementTextTrim("cn");
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("uid", uid);
                log.info("uid" + uid);
                objectObjectHashMap.put("cn", cn);
                log.info("cn" + cn);
                log.info("------" + JSON.toJSONString(objectObjectHashMap));
                return JSON.toJSONString(objectObjectHashMap);
            }
        }
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("uid", "无数据");
        return JSON.toJSONString(objectObjectHashMap);
    }

}
