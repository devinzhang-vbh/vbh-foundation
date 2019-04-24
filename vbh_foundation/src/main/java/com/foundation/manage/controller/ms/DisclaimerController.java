
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.foundation.manage.entity.VBC.Disclaimer;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.DisclaimerService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.DisclaimerWrapper;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
@RequestMapping("/disclaimer")
public class DisclaimerController extends BaseController {

    private static final String PREFIX = "/vbc/disclaimer/";

    @Autowired
    private DisclaimerService disclaimerService;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "disclaimer.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/disclaimer_add")
    public String disclaimerAdd() {
        return PREFIX + "disclaimer_add.html";
    }

    /**
     * 跳转到修改通知
     *
     * @param disclaimerId
     * @param model
     * @return
     */
    @RequestMapping("/disclaimer_update/{disclaimerId}")
    public String disclaimerUpdate(@PathVariable Long disclaimerId, Model model) {
        Disclaimer disclaimer = this.disclaimerService.selectById(disclaimerId);
        model.addAllAttributes(BeanUtil.beanToMap(disclaimer));
        LogObjectHolder.me().set(disclaimer);
        return PREFIX + "disclaimer_edit.html";
    }


    /**
     * 获取通知列表
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.disclaimerService.list(condition);
        return super.warpObject(new DisclaimerWrapper(list));
    }

    /**
     * 新增通知
     *
     * @param disclaimer
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = DisclaimerMap.class)
    public Object add(Disclaimer disclaimer) {
        //if (ToolUtil.isOneEmpty(disclaimer, disclaimer.getTitle(), disclaimer.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        String disclaimerContent = disclaimer.getDisclaimerContent();
        String disclaimerContents = disclaimerContent;
        try {
            disclaimerContents = URLDecoder.decode(disclaimerContent, "UTF-8");
            System.out.println("disclaimerContents:" + disclaimerContents);
            disclaimer.setDisclaimerContent(disclaimerContents);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        disclaimer.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        disclaimer.setCreateTime(new Date());
        disclaimer.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        disclaimer.setLastUpdateTime(new Date());
        this.disclaimerService.insert(disclaimer);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     *
     * @param disclaimerId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    // @BussinessLog(value = "删除通知", key = "disclaimerId", dict = DisclaimerMap.class)
    public Object delete(@RequestParam Long disclaimerId) {

        //缓存通知名称
        // LogObjectHolder.me().set(ConstantFactory.me().getDisclaimerTitle(disclaimerId));

        this.disclaimerService.deleteById(disclaimerId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     *
     * @param disclaimer
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = DisclaimerMap.class)
    public Object update(Disclaimer disclaimer) {
        //if (ToolUtil.isOneEmpty(disclaimer, disclaimer.getDisclaimerId(), disclaimer.getTitle(), disclaimer.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //Disclaimer old = this.disclaimerService.selectById(disclaimer.getDisclaimerId());
        /**
         ** add update logic here
         */
        String disclaimerContent = disclaimer.getDisclaimerContent();
        String disclaimerContents = disclaimerContent;
        try {
            disclaimerContents = URLDecoder.decode(disclaimerContent, "UTF-8");
            System.out.println("disclaimerContents:" + disclaimerContents);
            disclaimer.setDisclaimerContent(disclaimerContents);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        disclaimer.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        disclaimer.setLastUpdateTime(new Date());
        this.disclaimerService.updateById(disclaimer);
        return SUCCESS_TIP;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
