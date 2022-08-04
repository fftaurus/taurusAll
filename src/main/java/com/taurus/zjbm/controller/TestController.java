package com.taurus.zjbm.controller;

import com.taurus.zjbm.common.aop.annotation.RepeatRequest;
import com.taurus.zjbm.entity.TestEntity;
import com.taurus.zjbm.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author taurus
 */
@RestController
@Slf4j
@Api(tags = {"测试类"})
@RequestMapping("/v1/test")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 新增
     */
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/add")
    @RepeatRequest(opened = true,time = 10)
    public void addTest(@RequestBody TestEntity entity,final HttpServletResponse response) {

        testService.addTest(entity,response);
    }

}
