
package com.foundation.manage.common.constant.factory;


import com.foundation.manage.entity.Dict;

import java.util.List;

/**
 * 常量生产工厂的接口
 *
 * @author yt
 */
public interface IConstantFactory {

    /**
     * 根据用户id获取用户名称
     * @param userId
     * @return
     */
    String getUserNameById(Long userId);

    /**
     * 根据用户id获取用户账号
     * @param userId
     * @return
     */
    String getUserAccountById(Long userId);

    /**
     * 通过角色ids获取角色名称
     * @param roleIds
     * @return
     */
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     * @param roleId
     * @return
     */
    String getSingleRoleName(Long roleId);

    /**
     * 通过角色id获取角色英文名称
     * @param roleId
     * @return
     */
    String getSingleRoleTip(Long roleId);

    /**
     * 获取部门名称
     * @param deptId
     * @return
     */
    String getDeptName(Long deptId);

    /**
     * 获取菜单的名称们(多个)
     * @param menuIds
     * @return
     */
    String getMenuNames(String menuIds);

    /**
     * 获取菜单名称
     * @param menuId
     * @return
     */
    String getMenuName(Long menuId);

    /**
     * 获取菜单名称通过编号
     * @param code
     * @return
     */
    String getMenuNameByCode(String code);

    /**
     * 获取菜单名称通过编号
     * @param code
     * @return
     */
    Long getMenuIdByCode(String code);

    /**
     * 获取字典名称
     * @param dictId
     * @return
     */
    String getDictName(Long dictId);

    /**
     * 获取通知标题
     * @param dictId
     * @return
     */
    String getNoticeTitle(Long dictId);

    /**
     * 根据字典名称和字典中的值获取对应的名称
     * @param name
     * @param code
     * @return
     */
    String getDictsByName(String name, String code);

    /**
     * 获取性别名称
     * @param sexCode
     * @return
     */
    String getSexName(String sexCode);

    /**
     * 获取用户登录状态
     * @param status
     * @return
     */
    String getStatusName(String status);

    /**
     * 获取菜单状态
     * @param status
     * @return
     */
    String getMenuStatusName(String status);

    /**
     * 查询字典
     * @param id
     * @return
     */
    List<Dict> findInDict(Long id);

    /**
     *  获取被缓存的对象(用户删除业务)
     * @param para
     * @return
     */
    String getCacheObject(String para);

    /**
     * 获取子部门id
     * @param deptId
     * @return
     */
    List<Long> getSubDeptId(Long deptId);

    /**
     * 获取所有父部门id
     * @param deptId
     * @return
     */
    List<Long> getParentDeptIds(Long deptId);

}
