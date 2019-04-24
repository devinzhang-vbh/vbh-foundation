
package com.foundation.manage.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * spring session配置
 * EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)  //session过期时间  如果部署多机环境,需要打开注释
 * @author yt
 */
@ConditionalOnProperty(prefix = "guns", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}
