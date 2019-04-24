
package com.foundation.manage.warpper.vbc;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.common.constant.factory.ConstantFactory;
import com.foundation.manage.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部门列表的包装
 *
 * @author yt
 */
public class NewsWrapper extends BaseControllerWrapper {

    public NewsWrapper(Map<String, Object> single) {
        super(single);
    }

    public NewsWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public NewsWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public NewsWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Long creater = (Long) map.get("createUser");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));

        Date lastUpdateTime = (Date) map.get("lastUpdateTime");
        Object newUpTime =  map.get("newUpTime");

        Date upTimeNew =  newUpTime == null ?lastUpdateTime: (Date)newUpTime;

        String newUpTimeStr = DateUtil.date2Str(upTimeNew,"yyyy-MM-dd HH:mm:ss");

        map.put("newUpTime",newUpTimeStr);

    }
}
