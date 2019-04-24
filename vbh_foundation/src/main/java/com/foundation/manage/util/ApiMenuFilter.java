
package com.foundation.manage.util;


import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.foundation.manage.common.constant.Const;
import com.foundation.manage.common.node.MenuNode;
import com.foundation.manage.config.properties.GunsProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * api接口文档显示过滤
 *
 * @author yt
 */
public class ApiMenuFilter extends MenuNode {

    public static List<MenuNode> build(List<MenuNode> nodes) {

        //如果关闭了接口文档,则不显示接口文档菜单
        GunsProperties gunsProperties = SpringContextHolder.getBean(GunsProperties.class);
        if (!gunsProperties.getSwaggerOpen()) {
            List<MenuNode> menuNodesCopy = new ArrayList<>();
            for (MenuNode menuNode : nodes) {
                if (Const.API_MENU_NAME.equals(menuNode.getName())) {
                    continue;
                } else {
                    menuNodesCopy.add(menuNode);
                }
            }
            nodes = menuNodesCopy;
        }

        return nodes;
    }
}
