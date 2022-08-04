package com.taurus.zjbm.common.exception;

import com.taurus.zjbm.common.config.IResponse;
import lombok.Data;

/**
 * Business exception.
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = -3346733232647550691L;

    /**
     * DEFAULT_BUSINESS_CODE.
     */
    public static final String DEFAULT_BUSINESS_CODE = BaseResponseEnum.BUSINESS_EXCEPTION.getCode();

    /**
     * DEFAULT_BUSINESS_MESSAGE.
     */
    public static final String DEFAULT_BUSINESS_MESSAGE = BaseResponseEnum.BUSINESS_EXCEPTION.getMessage();

    private final String code;

    private final String message;

    /**
     * default constructor.
     */
    public BusinessException() {

        super();
        this.code = DEFAULT_BUSINESS_CODE;
        this.message = DEFAULT_BUSINESS_MESSAGE;
    }

    /**
     * @param message error message
     */
    public BusinessException(final String message) {

        super(DEFAULT_BUSINESS_CODE, message);
        this.code = DEFAULT_BUSINESS_CODE;
        this.message = message;
    }

    /**
     * @param code    error code
     * @param message error message
     */
    public BusinessException(final String code, final String message) {

        super(code, message);
        this.code = code;
        this.message = message;
    }

    /**
     * @param response Class or enum that implememts IResponse
     */
    public BusinessException(final IResponse response) {

        super(response.getCode(), response.getMessage());
        this.code = response.getCode();
        this.message = response.getMessage();
    }

    @Override
    public String getCode() {

        return this.code;
    }

    @Override
    public String getMessage() {

        return this.message;
    }

    /**
     * @param code    error code
     * @param message error message
     */
    public BusinessException(final String code, final String message, final Object... args) {

        this.code = code;
        this.message = String.format(message, args);
    }

}
