package com.foundation.manage.service;


import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.common.exception.BizExceptionEnum;
import com.foundation.manage.common.node.TreeViewNode;
import com.foundation.manage.common.node.ZTreeNode;
import com.foundation.manage.entity.Dept;
import com.foundation.manage.mapper.DeptMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class DeptService extends ServiceImpl<DeptMapper, Dept> {

    @Resource
    private DeptMapper deptMapper;

    /**
     * 新增部门
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void addDept(Dept dept) {

        if (ToolUtil.isOneEmpty(dept, dept.getSimpleName(), dept.getFullName(), dept.getPid(), dept.getDescription())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        //完善pids,根据pid拿到pid的pids
        this.deptSetPids(dept);

        this.insert(dept);
    }

    /**
     * 修改部门
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void editDept(Dept dept) {

        if (ToolUtil.isOneEmpty(dept, dept.getDeptId(), dept.getSimpleName(), dept.getFullName(), dept.getPid(), dept.getDescription())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        //完善pids,根据pid拿到pid的pids
        this.deptSetPids(dept);

        this.updateById(dept);
    }

    /**
     * 删除部门
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(Long deptId) throws Exception{
        Dept dept = deptMapper.selectById(deptId);

        //根据like查询删除所有级联的部门
        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("PIDS", "%[" + dept.getDeptId() + "]%");
        List<Dept> subDepts = deptMapper.selectList(wrapper);
        for (Dept temp : subDepts) {
            this.deleteById(temp.getDeptId());
        }

        this.deleteById(dept.getDeptId());
    }

    /**
     * 获取ztree的节点列表
     *
     */
    public List<ZTreeNode> tree() {
        return this.baseMapper.tree();
    }

    /**
     * 获取ztree的节点列表
     *
     */
    public List<TreeViewNode> treeviewNodes() {
        return this.baseMapper.treeViewNodes();
    }

    /**
     * 获取所有部门列表
     *
     */
    public List<Map<String, Object>> list(String condition, String deptId) {
        return this.baseMapper.list(condition, deptId);
    }

    /**
     * 设置部门的父级ids
     *
     */
    private void deptSetPids(Dept dept) {
        if (ToolUtil.isEmpty(dept.getPid()) || dept.getPid().equals(0L)) {
            dept.setPid(0L);
            dept.setPids("[0],");
        } else {
            Long pid = dept.getPid();
            Dept temp = this.selectById(pid);
            String pids = temp.getPids();
            dept.setPid(pid);
            dept.setPids(pids + "[" + pid + "],");
        }
    }
}
