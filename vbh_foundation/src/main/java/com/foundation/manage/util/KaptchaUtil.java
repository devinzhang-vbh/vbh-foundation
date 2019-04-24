
package com.foundation.manage.util;

import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.foundation.manage.config.properties.GunsProperties;

/**
 * 验证码工具类
 * @author 70719
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(GunsProperties.class).getKaptchaOpen();
    }
}