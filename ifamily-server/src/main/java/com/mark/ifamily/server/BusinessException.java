package com.mark.ifamily.server;

/**
* Created by mark.zhu on 2016/10/8.
*/
class BusinessException extends Exception{
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
