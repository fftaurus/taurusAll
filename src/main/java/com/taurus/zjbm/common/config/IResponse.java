package com.taurus.zjbm.common.config;

import java.io.Serializable;

public interface IResponse<T> extends Serializable {

    /**
     * 获取响应编码
     *
     * @return
     */
    String getCode();

    /**
     * 获取响应信息
     *
     * @return
     */
    String getMessage();

    /**
     * 获取响应消息体
     *
     * @return
     */
    T getBody();
}
