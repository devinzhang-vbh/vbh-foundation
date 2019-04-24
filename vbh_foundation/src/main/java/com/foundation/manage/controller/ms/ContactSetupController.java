
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.foundation.manage.entity.VBC.ContactSetup;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.ContactSetupService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.ContactSetupWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;


import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通知控制器
 *
 * @author yt
 */
@Controller
@RequestMapping("/contactSetup")
public class ContactSetupController extends BaseController {

    private static final String PREFIX = "/vbc/contactSetup/";

    @Autowired
    private ContactSetupService contactSetupService;

    /**
     * 跳转到通知列表首页
     *
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "contactSetup.html";
    }

    /**
     * 跳转到添加通知
     *
     */
    @RequestMapping("/contactSetup_add")
    public String contactSetupAdd() {
        return PREFIX + "contactSetup_add.html";
    }

    /**
     * 跳转到修改通知
     * @param contactId
     * @param model
     * @return
     */
    @RequestMapping("/contactSetup_update/{contactId}")
    public String contactSetupUpdate(@PathVariable Long contactId, Model model) {
        ContactSetup contactSetup = this.contactSetupService.selectById(contactId);
        model.addAllAttributes(BeanUtil.beanToMap(contactSetup));
        LogObjectHolder.me().set(contactSetup);
        return PREFIX + "contactSetup_edit.html";
    }


    /**
     * 获取通知列表
     * @param condition
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.contactSetupService.list(condition);
        return super.warpObject(new ContactSetupWrapper(list));
    }

    /**
     * 新增通知
     * @param contactSetup
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = ContactSetupMap.class)
    public Object add(ContactSetup contactSetup) {
        //if (ToolUtil.isOneEmpty(contactSetup, contactSetup.getTitle(), contactSetup.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        contactSetup.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        contactSetup.setCreateTime(new Date());
		contactSetup.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        contactSetup.setLastUpdateTime(new Date());
        this.contactSetupService.insert(contactSetup);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     * @param contactId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
   // @BussinessLog(value = "删除通知", key = "contactSetupId", dict = ContactSetupMap.class)
    public Object delete(@RequestParam Long contactId) {

        //缓存通知名称
       // LogObjectHolder.me().set(ConstantFactory.me().getContactSetupTitle(contactSetupId));

        this.contactSetupService.deleteById(contactId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     * @param contactSetup
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = ContactSetupMap.class)
    public Object update(ContactSetup contactSetup) {
        //if (ToolUtil.isOneEmpty(contactSetup, contactSetup.getContactSetupId(), contactSetup.getTitle(), contactSetup.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //ContactSetup old = this.contactSetupService.selectById(contactSetup.getContactSetupId());
        /**
		** add update logic here
		*/
		contactSetup.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        contactSetup.setLastUpdateTime(new Date());
        this.contactSetupService.updateById(contactSetup);
        return SUCCESS_TIP;
    }
	
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
