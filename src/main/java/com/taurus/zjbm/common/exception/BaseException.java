package com.taurus.zjbm.common.exception;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -5430955945096897330L;

    private final String code;

    private String message;

    /**
     * default constructor.
     */
    public BaseException() {

        super(BaseResponseEnum.SYSTEM_EXCEPTION.getMessage());
        this.code = BaseResponseEnum.SYSTEM_EXCEPTION.getCode();
        this.message = BaseResponseEnum.SYSTEM_EXCEPTION.getMessage();

    }

    /**
     * @param message error message
     */
    public BaseException(final String message) {

        super(message);
        this.code = BaseResponseEnum.SYSTEM_EXCEPTION.getCode();
    }

    /**
     * @param code    error code
     * @param message error message
     */
    public BaseException(final String code, final String message) {

        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {

        return this.code;
    }

    @Override
    public String getMessage() {

        return this.message;
    }
}
