
package com.foundation.manage.common.constant.factory;

import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.common.constant.state.Order;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 *
 * @author yt
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpContext.getRequest();

        //每页多少条数据
        int limit = Integer.valueOf(request.getParameter("limit"));

        //每页的偏移量(本页当前有多少条)
        int offset = Integer.valueOf(request.getParameter("offset"));

        //排序字段名称
        String sort = request.getParameter("sort");

        //asc或desc(升序或降序)
        String order = request.getParameter("order");

        //如果没有排序字段名称
        if (ToolUtil.isEmpty(sort)) {

            Page<T> page = new Page<>((offset / limit + 1), limit);
            page.setOpenSort(false);
            return page;
        } else {

            //如果有排序字段
            Page<T> page = new Page<>((offset / limit + 1), limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }
}
