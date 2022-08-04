package com.taurus.zjbm.mapper;

import com.taurus.zjbm.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;


/**
* @author taurus
*/
@Mapper
public interface TestMapper {

    TestEntity selectById(Long id);

    void update(TestEntity entity);

    void updateByTest(TestEntity entity);

    void insert(TestEntity entity);

    void insertOrUpdate(TestEntity entity);

    TestEntity selectByIdAll(long unit, long userId);

}
