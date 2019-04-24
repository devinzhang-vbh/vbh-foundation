
package com.foundation.manage.common.node;

import cn.stylefeng.roses.kernel.model.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * jquery ztree 插件的节点
 *
 * @author yt
 */
@Data
public class TreeViewNode implements Tree {

    /**
     * 附加信息，一般用于存业务id
     */
    private String tags;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 节点名称
     */
    private String text;

    /**
     * 子节点
     */
    private List<TreeViewNode> nodes;

    @Override
    public String getNodeId() {
        return tags;
    }

    @Override
    public String getNodeParentId() {
        return parentId;
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.nodes = childrenNodes;
    }
}
