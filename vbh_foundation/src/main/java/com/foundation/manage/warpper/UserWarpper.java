
package com.foundation.manage.warpper;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author yt
 */
public class UserWarpper extends BaseControllerWrapper {

    public UserWarpper(Map<String, Object> single) {
        super(single);
    }

    public UserWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public UserWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public UserWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("sexName", ConstantFactory.me().getSexName((String) map.get("sex")));
        map.put("roleName", ConstantFactory.me().getRoleName((String) map.get("roleId")));
        map.put("deptName", ConstantFactory.me().getDeptName((Long) map.get("deptId")));
        map.put("statusName", ConstantFactory.me().getStatusName((String) map.get("status")));
    }

}
