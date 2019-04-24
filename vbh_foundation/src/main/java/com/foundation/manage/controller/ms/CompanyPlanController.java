
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.foundation.manage.entity.VBC.CompanyPlan;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.CompanyPlanService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.CompanyPlanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/companyPlan")
public class CompanyPlanController extends BaseController {

    private static final String PREFIX = "/vbc/companyPlan/";

    @Autowired
    private CompanyPlanService companyPlanService;

    /**
     * 跳转到通知列表首页
     *
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "companyPlan.html";
    }

    /**
     * 跳转到添加通知
     *
     */
    @RequestMapping("/companyPlan_add")
    public String companyPlanAdd() {
        return PREFIX + "companyPlan_add.html";
    }

    /**
     * 跳转到修改通知
     * @param planId
     * @param model
     * @return
     */
    @RequestMapping("/companyPlan_update")
    public String companyPlanUpdate(@RequestParam Long planId, Model model) {
        CompanyPlan companyPlan = this.companyPlanService.selectById(planId);
        model.addAllAttributes(BeanUtil.beanToMap(companyPlan));
        LogObjectHolder.me().set(companyPlan);
        return PREFIX + "companyPlan_edit.html";
    }


    /**
     * 获取通知列表
     * @param condition
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.companyPlanService.list(condition);
        return super.warpObject(new CompanyPlanWrapper(list));
    }

    /**
     * 新增通知
     * @param companyPlan
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = CompanyPlanMap.class)
    public Object add(CompanyPlan companyPlan) {
        //if (ToolUtil.isOneEmpty(companyPlan, companyPlan.getTitle(), companyPlan.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        companyPlan.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        companyPlan.setCreateTime(new Date());
		companyPlan.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        companyPlan.setLastUpdateTime(new Date());
        this.companyPlanService.insert(companyPlan);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     * @param planId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
   // @BussinessLog(value = "删除通知", key = "companyPlanId", dict = CompanyPlanMap.class)
    public Object delete(@RequestParam Long planId) {

        //缓存通知名称
       // LogObjectHolder.me().set(ConstantFactory.me().getCompanyPlanTitle(companyPlanId));

        this.companyPlanService.deleteById(planId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     * @param companyPlan
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = CompanyPlanMap.class)
    public Object update(CompanyPlan companyPlan) {
        //if (ToolUtil.isOneEmpty(companyPlan, companyPlan.getCompanyPlanId(), companyPlan.getTitle(), companyPlan.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //CompanyPlan old = this.companyPlanService.selectById(companyPlan.getCompanyPlanId());
        /**
		** add update logic here
		*/
		companyPlan.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        companyPlan.setLastUpdateTime(new Date());
        this.companyPlanService.updateById(companyPlan);
        return SUCCESS_TIP;
    }
	
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
