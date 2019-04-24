package com.foundation.manage.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.foundation.manage.common.node.TreeViewNode;
import com.foundation.manage.common.node.ZTreeNode;
import com.foundation.manage.entity.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 获取ztree的节点列表
     * @return
     */
    List<ZTreeNode> tree();

    /**
     * 获取所有部门列表
     * @param condition
     * @param deptId
     * @return
     */
    List<Map<String, Object>> list(@Param("condition") String condition, @Param("deptId") String deptId);

    /**
     * 获取所有部门树列表
     * @return
     */
    List<TreeViewNode> treeViewNodes();
}
