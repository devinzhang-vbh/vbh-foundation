
package com.foundation.manage.common.constant.dictmap;


import com.foundation.manage.common.constant.dictmap.base.AbstractDictMap;

/**
 * 日志的字典
 *
 * @author yt
 */
public class LogDict extends AbstractDictMap {

    @Override
    public void init() {
        put("description", "备注");
    }

    @Override
    protected void initBeWrapped() {

    }
}
