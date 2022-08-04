package com.taurus.zjbm.service.impl;

import com.taurus.zjbm.entity.TestEntity;
import com.taurus.zjbm.mapper.TestMapper;
import com.taurus.zjbm.service.TestService;
import com.taurus.zjbm.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;


/**
* @author taurus
*/
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addTest(TestEntity entity,final HttpServletResponse response) {

        log.info("入参:{}", JsonUtil.toJson(entity));
        testMapper.insertOrUpdate(entity);

    }

}
