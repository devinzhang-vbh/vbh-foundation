
package com.foundation.manage.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.treebuild.DefaultTreeBuildFactory;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import com.foundation.manage.common.annotion.BussinessLog;
import com.foundation.manage.common.annotion.Permission;
import com.foundation.manage.common.constant.dictmap.DeptDict;
import com.foundation.manage.common.constant.factory.ConstantFactory;
import com.foundation.manage.common.node.TreeViewNode;
import com.foundation.manage.common.node.ZTreeNode;
import com.foundation.manage.entity.Dept;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.model.DeptDto;
import com.foundation.manage.service.DeptService;
import com.foundation.manage.warpper.DeptTreeWarpper;
import com.foundation.manage.warpper.DeptWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author yt
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {

    private static final String PREFIX = "/system/dept/";

    @Autowired
    private DeptService deptService;

    /**
     * 跳转到部门管理首页
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }

    /**
     * 跳转到添加部门
     * @return
     */
    @RequestMapping("/dept_add")
    public String deptAdd() {
        return PREFIX + "dept_add.html";
    }

    /**
     * 跳转到修改部门
     * @param deptId
     * @return
     */
    @Permission
    @RequestMapping("/dept_update")
    public String deptUpdate(@RequestParam("deptId") Long deptId) {

        if (ToolUtil.isEmpty(deptId)) {
            throw new RequestEmptyException();
        }

        //缓存部门修改前详细信息
        Dept dept = deptService.selectById(deptId);
        LogObjectHolder.me().set(dept);

        return PREFIX + "dept_edit.html";
    }

    /**
     * 获取部门的tree列表，ztree格式
     * @return
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    /**
     * 获取部门的tree列表，treeview格式
     * @return
     */
    @RequestMapping(value = "/treeview")
    @ResponseBody
    public List<TreeViewNode> treeview() {
        List<TreeViewNode> treeViewNodes = this.deptService.treeviewNodes();

        //构建树
        DefaultTreeBuildFactory<TreeViewNode> factory = new DefaultTreeBuildFactory<>();
        factory.setRootParentId("0");
        List<TreeViewNode> results = factory.doTreeBuild(treeViewNodes);

        //把子节点为空的设为null
        DeptTreeWarpper.clearNull(results);

        return results;
    }

    /**
     * 新增部门
     * @param dept
     * @return
     */
    @BussinessLog(value = "添加部门", key = "simpleName", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(Dept dept) {
        this.deptService.addDept(dept);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有部门列表
     * @param condition
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(value = "condition", required = false) String condition,
                       @RequestParam(value = "deptId", required = false) String deptId) {
        List<Map<String, Object>> list = this.deptService.list(condition, deptId);
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 部门详情
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/detail/{deptId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("deptId") Long deptId) {
        Dept dept = deptService.selectById(deptId);
        DeptDto deptDto = new DeptDto();
        BeanUtil.copyProperties(dept, deptDto);
        deptDto.setPName(ConstantFactory.me().getDeptName(deptDto.getPid()));
        return deptDto;
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @BussinessLog(value = "修改部门", key = "simpleName", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(Dept dept) {
        deptService.editDept(dept);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     * @param deptId
     * @return
     * @throws Exception
     */
    @BussinessLog(value = "删除部门", key = "deptId", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long deptId) throws Exception{

        //缓存被删除的部门名称
        LogObjectHolder.me().set(ConstantFactory.me().getDeptName(deptId));

        deptService.deleteDept(deptId);

        return SUCCESS_TIP;
    }

}
