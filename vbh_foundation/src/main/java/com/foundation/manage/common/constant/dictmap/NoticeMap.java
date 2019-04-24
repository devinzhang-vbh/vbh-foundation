
package com.foundation.manage.common.constant.dictmap;


import com.foundation.manage.common.constant.dictmap.base.AbstractDictMap;

/**
 * 通知的映射
 *
 * @author yt
 */
public class NoticeMap extends AbstractDictMap {

    @Override
    public void init() {
        put("title", "标题");
        put("content", "内容");
    }

    @Override
    protected void initBeWrapped() {
    }
}
