package com.foundation.manage.common.page;

import lombok.Data;

import java.util.List;

/**
 * @author heyutong.jerry@vhbledger.com
 * @version 1.0
 * @Description: 返回数据体
 * @date 20190422
 **/
public @Data class ReturnData<T>  {
    /**
     * 接口返回样式
     * "data": {
     * "msg": "",
     * "code": 0,
     * "obj": {
     * "refereeId": 13485,
     * "userPhone": "15094008616"
     * }
     * }
     */

    private String msg = "";
    private int code = 0;
    private List<T> obj = null;

}
