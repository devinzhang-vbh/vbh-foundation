package com.foundation.manage.service;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.common.constant.factory.ConstantFactory;
import com.foundation.manage.common.constant.state.MenuStatus;
import com.foundation.manage.common.exception.BizExceptionEnum;
import com.foundation.manage.common.node.MenuNode;
import com.foundation.manage.common.node.ZTreeNode;
import com.foundation.manage.entity.Menu;
import com.foundation.manage.mapper.MenuMapper;
import com.foundation.manage.model.MenuDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 添加菜单
     *

     */
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(MenuDto menuDto) {

        if (ToolUtil.isOneEmpty(menuDto, menuDto.getCode(), menuDto.getName(), menuDto.getPid(), menuDto.getMenuFlag(), menuDto.getUrl())) {
            throw new RequestEmptyException();
        }

        //判断是否已经存在该编号
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menuDto.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new ServiceException(BizExceptionEnum.EXISTED_THE_MENU);
        }

        //组装属性，设置父级菜单编号
        Menu resultMenu = this.menuSetPcode(menuDto);

        resultMenu.setStatus(MenuStatus.ENABLE.getCode());

        this.insert(resultMenu);
    }

    /**
     * 删除菜单
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void delMenu(Long menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.menuMapper.deleteRelationByMenu(menuId);
    }

    /**
     * 删除菜单包含所有子菜单
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void delMenuContainSubMenus(Long menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Wrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("PCODES", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getMenuId());
        }
    }

    /**
     * 根据条件查询菜单
     *
     */
    public List<Map<String, Object>> selectMenus(String condition, String level, Long menuId) {

        //获取menuId的code
        String code = "";
        if (menuId != null) {
            Menu menu = this.selectById(menuId);
            code = menu.getCode();
        }

        return this.baseMapper.selectMenus(condition, level, menuId, code);
    }

    /**
     * 根据条件查询菜单
     *
     * @return
     */
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return this.baseMapper.getMenuIdsByRoleId(roleId);
    }

    /**
     * 获取菜单列表树
     *
     * @return
     */
    public List<ZTreeNode> menuTreeList() {
        return this.baseMapper.menuTreeList();
    }

    /**
     * 获取菜单列表树
     *
     * @return
     */
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
        return this.baseMapper.menuTreeListByMenuIds(menuIds);
    }

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     */
    public int deleteRelationByMenu(Long menuId) {
        return this.baseMapper.deleteRelationByMenu(menuId);
    }

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     */
    public List<String> getResUrlsByRoleId(Long roleId) {
        return this.baseMapper.getResUrlsByRoleId(roleId);
    }

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     */
    public List<MenuNode> getMenusByRoleIds(List<Long> roleIds) {
        return this.baseMapper.getMenusByRoleIds(roleIds);
    }

    /**
     * 根据code查询菜单
     *
     */
    public Menu selectByCode(String code) {
        Menu menu = new Menu();
        menu.setCode(code);
        return this.baseMapper.selectOne(menu);
    }

    /**
     * 根据请求的父级菜单编号设置pcode和层级
     *
     */
    public Menu menuSetPcode(MenuDto menuParam) {

        Menu resultMenu = new Menu();
        BeanUtil.copyProperties(menuParam, resultMenu);

        if (ToolUtil.isEmpty(menuParam.getPid()) || menuParam.getPid().equals(0L)) {
            resultMenu.setPcode("0");
            resultMenu.setPcodes("[0],");
            resultMenu.setLevels(1);
        } else {
            Long pid = menuParam.getPid();
            Menu pMenu = this.selectById(pid);
            Integer pLevels = pMenu.getLevels();
            resultMenu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menuParam.getCode().equals(menuParam.getPcode())) {
                throw new ServiceException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            resultMenu.setLevels(pLevels + 1);
            resultMenu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }

        return resultMenu;
    }

}
