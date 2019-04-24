
package com.foundation.manage.warpper;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import com.foundation.manage.common.constant.factory.ConstantFactory;
import com.foundation.manage.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * 字典列表的包装
 *
 * @author yt
 */
public class DictWarpper extends BaseControllerWrapper {

    public DictWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        StringBuilder detail = new StringBuilder();
        Long id = Long.valueOf(map.get("dictId").toString());
        List<Dict> dicts = ConstantFactory.me().findInDict(id);
        if (dicts != null) {
            for (Dict dict : dicts) {
                detail.append(dict.getCode()).append(":").append(dict.getName()).append(",");
            }
            map.put("detail", StrUtil.removeSuffix(detail.toString(), ","));
        }
    }
}
