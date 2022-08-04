package com.taurus.zjbm.common.exception;

import com.taurus.zjbm.common.config.IResponse;

@SuppressWarnings("nls")
public enum BaseResponseEnum implements IResponse {

    /**
     * "000000", "SUCCESS"
     */
    SUCCESS("000000", "SUCCESS"),

    /**
     * "100000", "ERROR"
     */
    BUSINESS_EXCEPTION("100000", "ERROR"),

    /**
     * "400", "参数校验错误"
     */
    PARAMETER_EXCEPTION("400", "参数校验错误"),

    /**
     * "405", "权限不足"
     */
    AUTHORITY_EXCEPTION("405", "权限不足"),

    /**
     * "500", "系统错误"
     */
    SYSTEM_EXCEPTION("500", "系统错误");

    private final String code;

    private final String message;

    BaseResponseEnum(final String code, final String message) {

        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {

        return this.code;
    }

    @Override
    public String getMessage() {

        return this.message;
    }

    @Override
    public Object getBody() {

        return null;
    }
}
