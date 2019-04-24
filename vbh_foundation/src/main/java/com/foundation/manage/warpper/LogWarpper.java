
package com.foundation.manage.warpper;


import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.foundation.manage.common.constant.factory.ConstantFactory;
import com.foundation.manage.util.Contrast;

import java.util.List;
import java.util.Map;

/**
 * 日志列表的包装类
 *
 * @author yt
 */
public class LogWarpper extends BaseControllerWrapper {

    public LogWarpper(Map<String, Object> single) {
        super(single);
    }

    public LogWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        String message = (String) map.get("message");

        Long userid = (Long) map.get("userId");
        map.put("userName", ConstantFactory.me().getUserNameById(userid));

        int limit = 100;
        //如果信息过长,则只截取前100位字符串
        if (ToolUtil.isNotEmpty(message) && message.length() >= limit) {
            String subMessage = message.substring(0, 100) + "...";
            map.put("message", subMessage);
        }

        //如果信息中包含分割符号;;;   则分割字符串返给前台
        if (ToolUtil.isNotEmpty(message) && message.contains(Contrast.SEPARATOR)) {
            String[] msgs = message.split(Contrast.SEPARATOR);
            map.put("regularMessage", msgs);
        } else {
            map.put("regularMessage", message);
        }
    }
}
