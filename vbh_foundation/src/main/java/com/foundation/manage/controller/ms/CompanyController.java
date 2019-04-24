
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.foundation.manage.entity.VBC.Company;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.CompanyService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.CompanyWrapper;
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
@RequestMapping("/company")
public class CompanyController extends BaseController {

    private static final String PREFIX = "/vbc/company/";

    @Autowired
    private CompanyService companyService;

    /**
     * 跳转到通知列表首页
     *
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "company.html";
    }

    /**
     * 跳转到添加通知
     *
     */
    @RequestMapping("/company_add")
    public String companyAdd() {
        return PREFIX + "company_add.html";
    }

    /**
     * 跳转到修改通知
     * @param companyId
     * @param model
     * @return
     */
    @RequestMapping("/company_update/{companyId}")
    public String companyUpdate(@PathVariable Long companyId, Model model) {
        Company company = this.companyService.selectById(companyId);
        model.addAllAttributes(BeanUtil.beanToMap(company));
        LogObjectHolder.me().set(company);
        return PREFIX + "company_edit.html";
    }
    /**
     * 跳转到公司简介
     * @param companyId
     * @param model
     * @return
     */
    @RequestMapping("/company_update_detail/{companyId}")
    public String companyUpdateDetail(@PathVariable Long companyId, Model model) {
        Company company = this.companyService.selectById(companyId);
        System.out.println(company.getCompanyDesc());
        model.addAllAttributes(BeanUtil.beanToMap(company));
        LogObjectHolder.me().set(company);
        return PREFIX + "company_add_detail.html";
    }


    /**
     * 获取通知列表
     * @param condition
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.companyService.list(condition);
        return super.warpObject(new CompanyWrapper(list));
    }

    /**
     * 新增通知
     * @param company
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = CompanyMap.class)
    public Object add(Company company) {
        //if (ToolUtil.isOneEmpty(company, company.getTitle(), company.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        company.setCreatBy(ShiroKit.getUserNotNull().getAccount());
        company.setCreateTime(new Date());
		company.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        company.setLastUpdateTime(new Date());
        this.companyService.insert(company);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
   // @BussinessLog(value = "删除通知", key = "companyId", dict = CompanyMap.class)
    public Object delete(@RequestParam Long companyId) {

        //缓存通知名称
       // LogObjectHolder.me().set(ConstantFactory.me().getCompanyTitle(companyId));

        this.companyService.deleteById(companyId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     * @param company
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = CompanyMap.class)
    public Object update(Company company) {
        //if (ToolUtil.isOneEmpty(company, company.getCompanyId(), company.getTitle(), company.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //Company old = this.companyService.selectById(company.getCompanyId());
        /**
		** add update logic here
		*/
        String content = company.getCompanyDesc();
        if(content!=null){

            System.out.println("content:" + content);
            String newContent = content;
            try {
                newContent = URLDecoder.decode(content, "UTF-8");
                System.out.println("newContent:" + newContent);
                company.setCompanyDesc(newContent);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

		company.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        company.setLastUpdateTime(new Date());
        this.companyService.updateById(company);
        return SUCCESS_TIP;
    }
	
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
