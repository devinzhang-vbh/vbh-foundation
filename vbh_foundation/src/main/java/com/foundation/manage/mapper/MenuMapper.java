package com.foundation.manage.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.foundation.manage.common.node.MenuNode;
import com.foundation.manage.common.node.ZTreeNode;
import com.foundation.manage.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据条件查询菜单
     * @param condition
     * @param level
     * @param menuId
     * @param code
     * @return
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition, @Param("level") String level, @Param("menuId") Long menuId, @Param("code") String code);

    /**
     * 根据条件查询菜单
     * @param roleId
     * @return
     */
    List<Long> getMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取菜单列表树
     * @return
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     * @param menuIds
     * @return
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds);

    /**
     * 删除menu关联的relation
     * @param menuId
     * @return
     */
    int deleteRelationByMenu(Long menuId);

    /**
     * 获取资源url通过角色id
     * @param roleId
     * @return
     */
    List<String> getResUrlsByRoleId(Long roleId);

    /**
     * 根据角色获取菜单
     * @param roleIds
     * @return
     */
    List<MenuNode> getMenusByRoleIds(List<Long> roleIds);

}
