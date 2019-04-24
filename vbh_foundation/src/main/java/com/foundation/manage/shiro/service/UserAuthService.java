
package com.foundation.manage.shiro.service;


import com.foundation.manage.entity.User;
import com.foundation.manage.shiro.ShiroUser;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import java.util.List;

/**
 * 定义shirorealm所需数据的接口
 *
 * @author yt
 */
public interface UserAuthService {

    /**
     * 根据账号获取登录用户
     * @param account
     * @return
     */
    User user(String account);

    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     *@return
     */
    ShiroUser shiroUser(User user);

    /**
     * 获取权限列表通过角色id
     * @return
     * @param roleId 角色id
     */
    List<String> findPermissionsByRoleId(Long roleId);

    /**
     * 根据角色id获取角色名称
     * @return
     * @param roleId 角色id
     */
    String findRoleNameByRoleId(Long roleId);

    /**
     * 获取shiro的认证信息
     * @param shiroUser
     * @param user
     * @param realmName
     * @return
     */
    SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName);

}
