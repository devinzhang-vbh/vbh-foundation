package com.foundation.manage.mapper;

import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.foundation.manage.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户状态
     * @param userId
     * @param status
     * @return
     */
    int setStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 修改密码
     * @param userId
     * @param pwd
     * @return
     */
    int changePwd(@Param("userId") Long userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     * @param dataScope
     * @param name
     * @param beginTime
     * @param endTime
     * @param deptId
     * @return
     */
    List<Map<String, Object>> selectUsers(@Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptId") Long deptId);

    /**
     * 设置用户的角色
     * @param userId
     * @param roleIds
     * @return
     */
    int setRoles(@Param("userId") Long userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     * @param account
     * @return
     */
    User getByAccount(@Param("account") String account);

}
