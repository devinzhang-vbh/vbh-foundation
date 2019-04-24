
package com.foundation.manage.common.constant.state;

/**
 * 业务是否成功的日志记录
 *
 * @author yt
 *
 */
public enum LogSucceed {

    /**
     *
     * 成功
     */
    SUCCESS("成功"),
    /**
     * 失败
     */
    FAIL("失败");

    String message;

    LogSucceed(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
