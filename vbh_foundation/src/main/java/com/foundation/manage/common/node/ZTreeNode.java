
package com.foundation.manage.common.node;

import lombok.Data;

/**
 * jquery ztree 插件的节点
 *
 * @author yt
 */
@Data
public class ZTreeNode {

    /**
     *  //节点id
     */
    private Long id;

    /**
     *   //父节点id
     */
    private Long pId;

    /**
     *  //节点名称
     */
    private String name;

    /**
     * //是否打开节点
     */
    private Boolean open;

    /**
     * //是否被选中
     */
    private Boolean checked;

    /**
     * 创建ztree的父级节点
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:51 PM
     */
    public static ZTreeNode createParent() {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setChecked(true);
        zTreeNode.setId(0L);
        zTreeNode.setName("顶级");
        zTreeNode.setOpen(true);
        zTreeNode.setPId(0L);
        return zTreeNode;
    }
}
