
package com.foundation.manage.common.constant;

/**
 * jwt相关配置
 *
 * @author yt
 */
public interface JwtConstants {

    String AUTH_HEADER = "Authorization";

    String SECRET = "defaultSecret";

    Long EXPIRATION = 604800L;

    String AUTH_PATH = "/guns/auth";

}
