package com.vivedu.ckd.controller;

import com.alibaba.fastjson.JSON;
import com.vivedu.ckd.service.DockingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/api")
public class DockingContoller {

    @Autowired
    private DockingService dockingService;

    @GetMapping(path = "/courseList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "课程信息")
    public String courseInfo(
            @RequestParam(required = false, value = "courseid") @ApiParam(value = "课程编号") String courseId,
            @RequestParam(required = false, value = "keywords") @ApiParam(value = "课程关键字") String keywords,
            @RequestParam(required = false, value = "page", defaultValue = "1") @ApiParam(value = "页数") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") @ApiParam(value = "页大小") Integer size,
            @RequestParam(required = false, value = "sort", defaultValue = "date") @ApiParam(value = "排序") String sort,
            @RequestParam(required = false, value = "userId") @ApiParam(value = "学工编号") String userId) throws InterruptedException {
        log.info("进入/courseList方法");
          dockingService.findAllCourse(page, size, sort);
            return JSON.toJSONString(null);
        }

}

