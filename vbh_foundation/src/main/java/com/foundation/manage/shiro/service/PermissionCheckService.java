
package com.foundation.manage.shiro.service;

/**
 * 检查用接口
 * @author 70719
 */
public interface PermissionCheckService {

    /**
     * 检查当前登录用户是否拥有指定的角色访问当
     * @param permissions
     * @return
     */
    boolean check(Object[] permissions);

    /**
     * 检查当前登录用户是否拥有当前请求的servlet的权限
     * @return
     */
    boolean checkAll();
}
