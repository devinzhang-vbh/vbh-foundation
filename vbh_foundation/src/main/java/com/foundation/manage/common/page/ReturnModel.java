package com.foundation.manage.common.page;

import lombok.Data;

import java.util.List;

/**
 * @author heyutong.jerry@vhbledger.com
 * @version 1.0
 * @Description: 接口返回实体对象
 * @date 20190422
 **/
public @Data class ReturnModel<T> {
    /**
     * 接口返回样式
     * {
     * "state": 200,
     * "msg": "操作成功！",
     * "data": {
     *  "msg": "",
     *  "code": 0,
     *      "obj": {
     *      "refereeId": 13485,
     *      "userPhone": "15094008616"
     *      }
     * }
     * }
     */
    public static final int SUCCESS=200;//成功
    public static final int FAILURE=500;//失败
    public static final String SUCCESS_MSG ="操作成功";//成功
    public static final String FAILURE_MSG ="操作失败";//失败

    private int state = FAILURE;
    private String msg = FAILURE_MSG;
    private ReturnData<T> data= new ReturnData<>();
}
