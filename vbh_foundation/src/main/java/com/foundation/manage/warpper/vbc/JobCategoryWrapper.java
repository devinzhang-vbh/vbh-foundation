
package com.foundation.manage.warpper.vbc;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author yt
 */
public class JobCategoryWrapper extends BaseControllerWrapper {

    public JobCategoryWrapper(Map<String, Object> single) {
        super(single);
    }

    public JobCategoryWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public JobCategoryWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public JobCategoryWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Long creater = (Long) map.get("createUser");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }
}
