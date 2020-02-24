package com.vivedu.ckd.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class ThirdController {
    @RequestMapping(value = "/caslogin")
    public String newsDetail(HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        log.info("登陆认证");
        HashMap<String, Object> hashMapMap = new HashMap<>();
        String uid = request.getHeader("UID");
        if (StringUtils.isNotEmpty(uid)) {
            //获取当前已认证用户的姓名
            String name = request.getHeader("USER_NAME");
            //获取认证服务器的访问地址
            String authServerUrl = request.getHeader("AUTH_SERVER_URL");
            hashMapMap.put("uid", uid);
            log.info("===================================="+uid);
            hashMapMap.put("name", name);
            log.info("================================"+name);
            //String service = getCurrentPath(request, response);

        }
        return hashMapMap.toString();

    }

}






