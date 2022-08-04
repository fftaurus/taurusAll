package com.taurus.zjbm.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @Author taurus
 * @Date 2022/8/4
 */
@Slf4j
public class CommonsUtils {

    /**
     *
     * @param num 需要设置的map数量初始值
     * @return
     */
    public static int getMapMaxNum(int num){
        return (int) ((float) num / 0.75F + 1.0F);
    }

    /**
     * BigDecimal 比较值差异
     * @param a
     * @param b
     * @return
     */
    public static boolean getCompare(final BigDecimal a, final BigDecimal b){

        final boolean bool = false;

        if(ObjectUtils.isNotEmpty(a) && ObjectUtils.isNotEmpty(b) && (b.compareTo(a) != 0) ){
            return true;
        }
        if(ObjectUtils.isEmpty(a) && ObjectUtils.isNotEmpty(b)){
            return true;
        }
        if(ObjectUtils.isNotEmpty(a) && ObjectUtils.isEmpty(b)){
            return true;
        }
        return bool;
    }

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    public static String getJsonRequest(HttpServletRequest request) {

        StringBuilder data = new StringBuilder();
        String line;
        BufferedReader reader;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        return data.toString();
    }
}
