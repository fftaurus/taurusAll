package com.taurus.zjbm.service;

import com.taurus.zjbm.entity.TestEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author taurus
 * @Date 2022/8/4
 */
public interface TestService {

    void addTest(TestEntity entity,final HttpServletResponse response);

}
