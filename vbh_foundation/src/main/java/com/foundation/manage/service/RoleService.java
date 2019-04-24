package com.foundation.manage.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.common.constant.Const;
import com.foundation.manage.common.constant.cache.Cache;
import com.foundation.manage.common.constant.factory.ConstantFactory;
import com.foundation.manage.common.exception.BizExceptionEnum;
import com.foundation.manage.common.node.ZTreeNode;
import com.foundation.manage.entity.Relation;
import com.foundation.manage.entity.Role;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.mapper.RelationMapper;
import com.foundation.manage.mapper.RoleMapper;
import com.foundation.manage.model.RoleDto;
import com.foundation.manage.util.CacheUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RelationMapper relationMapper;

    /**
     * 添加角色
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role) {

        if (ToolUtil.isOneEmpty(role, role.getName(), role.getPid(), role.getDescription())) {
            throw new RequestEmptyException();
        }

        role.setRoleId(null);

        this.insert(role);
    }

    /**
     * 编辑角色
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void editRole(RoleDto roleDto) {

        if (ToolUtil.isOneEmpty(roleDto, roleDto.getName(), roleDto.getPid(), roleDto.getDescription())) {
            throw new RequestEmptyException();
        }

        Role old = this.selectById(roleDto.getRoleId());
        BeanUtil.copyProperties(roleDto, old);
        this.updateById(old);

        //删除缓存
        CacheUtil.removeAll(Cache.CONSTANT);
    }

    /**
     * 设置某个角色的权限
     *
     * @param roleId 角色id
     * @param ids    权限的id
     */
    @Transactional(rollbackFor = Exception.class)
    public void setAuthority(Long roleId, String ids) {

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);

        // 添加新的权限
        for (Long id : Convert.toLongArray(ids.split(","))) {
            Relation relation = new Relation();
            relation.setRoleId(roleId);
            relation.setMenuId(id);
            this.relationMapper.insert(relation);
        }
    }

    /**
     * 删除角色
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void delRoleById(Long roleId) {

        if (ToolUtil.isEmpty(roleId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        //不能删除超级管理员角色
        if (roleId.equals(Const.ADMIN_ROLE_ID)) {
            throw new ServiceException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }

        //缓存被删除的角色名称
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        //删除角色
        this.roleMapper.deleteById(roleId);

        //删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);

        //删除缓存
        CacheUtil.removeAll(Cache.CONSTANT);
    }

    /**
     * 根据条件查询角色列表
     *
     * @return
     */
    public List<Map<String, Object>> selectRoles(String condition) {
        return this.baseMapper.selectRoles(condition);
    }

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     */
    public int deleteRolesById(Long roleId) {
        return this.baseMapper.deleteRolesById(roleId);
    }

    /**
     * 获取角色列表树
     *
     * @return
     */
    public List<ZTreeNode> roleTreeList() {
        return this.baseMapper.roleTreeList();
    }

    /**
     * 获取角色列表树
     *
     * @return
     */
    public List<ZTreeNode> roleTreeListByRoleId(String[] roleId) {
        return this.baseMapper.roleTreeListByRoleId(roleId);
    }

}
