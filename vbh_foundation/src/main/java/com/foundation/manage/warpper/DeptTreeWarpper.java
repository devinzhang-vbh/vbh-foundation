
package com.foundation.manage.warpper;


import com.foundation.manage.common.node.TreeViewNode;

import java.util.List;

/**
 * 部门列表树的包装
 *
 * @author yt
 */
public class DeptTreeWarpper {

    public static void clearNull(List<TreeViewNode> list) {
        if (list == null) {
            return;
        } else {
            if (list.size() == 0) {
                return;
            } else {
                for (TreeViewNode node : list) {
                    if (node.getNodes() != null) {
                        if (node.getNodes().size() == 0) {
                            node.setNodes(null);
                        } else {
                            clearNull(node.getNodes());
                        }
                    }
                }
            }
        }
    }
}
