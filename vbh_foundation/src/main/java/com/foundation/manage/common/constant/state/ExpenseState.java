
package com.foundation.manage.common.constant.state;

/**
 * 是否是菜单的枚举
 *
 * @author yt
 */
public enum ExpenseState {

    /**
     * 待提交
     */
    SUBMITING(1, "待提交"),
    /**
     * 待审核
     */
    CHECKING(2, "待审核"),
    /**
     * 审核通过
     */
    PASS(3, "审核通过"),
    /**
     * 审核未通过
     */
    UN_PASS(4, "未通过");

    int code;
    String message;

    ExpenseState(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (ExpenseState s : ExpenseState.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
