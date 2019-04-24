package com.foundation.manage.service;


import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.factory.UserFactory;
import com.foundation.manage.common.constant.Const;
import com.foundation.manage.common.constant.state.ManagerStatus;
import com.foundation.manage.common.exception.BizExceptionEnum;
import com.foundation.manage.common.node.MenuNode;
import com.foundation.manage.entity.User;
import com.foundation.manage.mapper.UserMapper;
import com.foundation.manage.model.UserDto;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.shiro.ShiroUser;
import com.foundation.manage.util.ApiMenuFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private MenuService menuService;

    /**
     * 添加用戶
     *
     */
    public void addUser(UserDto user){

        // 判断账号是否重复
        User theUser = this.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new ServiceException(BizExceptionEnum.USER_ALREADY_REG);
        }

        // 完善账号信息
        String salt = ShiroKit.getRandomSalt(5);
        String password = ShiroKit.md5(user.getPassword(), salt);

        this.insert(UserFactory.createUser(user, password, salt));
    }

    /**
     * 修改用户
     *
     */
    public void editUser(UserDto user){
        User oldUser = this.selectById(user.getUserId());

        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            this.updateById(UserFactory.editUser(user, oldUser));
        } else {
            this.assertAuth(user.getUserId());
            ShiroUser shiroUser = ShiroKit.getUserNotNull();
            if (shiroUser.getId().equals(user.getUserId())) {
                this.updateById(UserFactory.editUser(user, oldUser));
            } else {
                throw new ServiceException(BizExceptionEnum.NO_PERMITION);
            }
        }
    }

    /**
     * 删除用户
     *
     */
    public void deleteUser(Long userId){

        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ServiceException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        this.assertAuth(userId);
        this.setStatus(userId, ManagerStatus.DELETED.getCode());
    }

    /**
     * 修改用户状态
     *
     */
    public int setStatus(Long userId, String status) {
        return this.baseMapper.setStatus(userId, status);
    }

    /**
     * 修改密码
     *
     */
    public void changePwd(String oldPassword,String newPassword) {
        Long userId = ShiroKit.getUserNotNull().getId();
        User user = this.selectById(userId);

        String oldMd5 = ShiroKit.md5(oldPassword, user.getSalt());

        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPassword, user.getSalt());
            user.setPassword(newMd5);
            this.updateById(user);
        } else {
            throw new ServiceException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 根据条件查询用户列表
     *
     */
    public List<Map<String, Object>> selectUsers(DataScope dataScope, String name, String beginTime, String endTime, Long deptId) {
        return this.baseMapper.selectUsers(dataScope, name, beginTime, endTime, deptId);
    }

    /**
     * 设置用户的角色
     *
     */
    public int setRoles(Long userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    /**
     * 通过账号获取用户
     *
     */
    public User getByAccount(String account) {
        return this.baseMapper.getByAccount(account);
    }

    /**
     * 获取用户菜单列表
     *
     */
    public List<MenuNode> getUserMenuNodes(List<Long> roleList) {
        if (roleList == null || roleList.size() == 0) {
            return new ArrayList<>();
        } else {
            List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
            List<MenuNode> titles = MenuNode.buildTitle(menus);
            return ApiMenuFilter.build(titles);
        }

    }

    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     *
     */
    public void assertAuth(Long userId) {
        if (ShiroKit.isAdmin()) {
            return;
        }
        List<Long> deptDataScope = ShiroKit.getDeptDataScope();
        User user = this.selectById(userId);
        Long deptId = user.getDeptId();
        if (deptDataScope.contains(deptId)) {
            return;
        } else {
            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
        }

    }

}
