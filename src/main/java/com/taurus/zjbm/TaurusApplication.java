package com.taurus.zjbm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.taurus.zjbm.mapper"})
public class TaurusApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaurusApplication.class, args);
    }
}
