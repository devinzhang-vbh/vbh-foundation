
package com.foundation.manage.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.foundation.manage.common.annotion.BussinessLog;
import com.foundation.manage.common.constant.dictmap.NoticeMap;
import com.foundation.manage.common.constant.factory.ConstantFactory;
import com.foundation.manage.common.exception.BizExceptionEnum;
import com.foundation.manage.entity.Notice;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.NoticeService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.NoticeWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通知控制器
 *
 * @author yt
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    private static final String PREFIX = "/system/notice/";

    @Autowired
    private NoticeService noticeService;

    /**
     * 跳转到通知列表首页
     *
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "notice.html";
    }

    /**
     * 跳转到添加通知
     *
     */
    @RequestMapping("/notice_add")
    public String noticeAdd() {
        return PREFIX + "notice_add.html";
    }

    /**
     * 跳转到修改通知
     * @param noticeId
     * @param model
     * @return
     */
    @RequestMapping("/notice_update/{noticeId}")
    public String noticeUpdate(@PathVariable Long noticeId, Model model) {
        Notice notice = this.noticeService.selectById(noticeId);
        model.addAllAttributes(BeanUtil.beanToMap(notice));
        LogObjectHolder.me().set(notice);
        return PREFIX + "notice_edit.html";
    }

    /**
     * 跳转到首页通知
     * @return
     */
    @RequestMapping("/hello")
    public String hello() {
        List<Map<String, Object>> notices = noticeService.list(null);
        super.setAttr("noticeList", notices);
        return PREFIX + "notice_index.html";
    }

    /**
     * 获取通知列表
     * @param condition
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.noticeService.list(condition);
        return super.warpObject(new NoticeWrapper(list));
    }

    /**
     * 新增通知
     * @param notice
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "新增通知", key = "title", dict = NoticeMap.class)
    public Object add(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getTitle(), notice.getContent())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        notice.setCreateUser(ShiroKit.getUserNotNull().getId());
        notice.setCreateTime(new Date());
        this.noticeService.insert(notice);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     * @param noticeId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除通知", key = "noticeId", dict = NoticeMap.class)
    public Object delete(@RequestParam Long noticeId) {

        //缓存通知名称
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(noticeId));

        this.noticeService.deleteById(noticeId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     * @param notice
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改通知", key = "title", dict = NoticeMap.class)
    public Object update(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getNoticeId(), notice.getTitle(), notice.getContent())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Notice old = this.noticeService.selectById(notice.getNoticeId());
        old.setTitle(notice.getTitle());
        old.setContent(notice.getContent());
        this.noticeService.updateById(old);
        return SUCCESS_TIP;
    }

}
