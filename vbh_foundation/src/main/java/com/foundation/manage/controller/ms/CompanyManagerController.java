
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.foundation.manage.entity.VBC.CompanyManager;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.CompanyManagerService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.CompanyManagerWrapper;
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
@RequestMapping("/companyManager")
public class CompanyManagerController extends BaseController {

    private static final String PREFIX = "/vbc/companyManager/";

    @Autowired
    private CompanyManagerService companyManagerService;

    /**
     * 跳转到通知列表首页
     *
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "companyManager.html";
    }

    /**
     * 跳转到添加通知
     *
     */
    @RequestMapping("/companyManager_add")
    public String companyManagerAdd() {
        return PREFIX + "companyManager_add.html";
    }

    /**
     * 跳转到修改通知
     * @param managerId
     * @param model
     * @return
     */
    @RequestMapping("/companyManager_update/{managerId}")
    public String companyManagerUpdate(@PathVariable Long managerId, Model model) {
        CompanyManager companyManager = this.companyManagerService.selectById(managerId);
        model.addAllAttributes(BeanUtil.beanToMap(companyManager));
        LogObjectHolder.me().set(companyManager);
        return PREFIX + "companyManager_edit.html";
    }


    /**
     * 获取通知列表
     * @param language
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "language",required = false) String language) {
        String languages="US";
        if(language==null){
            languages="CN";
        }
        List<Map<String, Object>> list = this.companyManagerService.list(languages);
        return super.warpObject(new CompanyManagerWrapper(list));
    }

    /**
     * 新增通知
     * @param companyManager
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = CompanyManagerMap.class)
    public Object add(CompanyManager companyManager) {
        //if (ToolUtil.isOneEmpty(companyManager, companyManager.getTitle(), companyManager.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        companyManager.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        companyManager.setCreateTime(new Date());
		companyManager.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        companyManager.setLastUpdateTime(new Date());
        this.companyManagerService.insert(companyManager);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     * @param managerId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
   // @BussinessLog(value = "删除通知", key = "companyManagerId", dict = CompanyManagerMap.class)
    public Object delete(@RequestParam Long managerId) {

        //缓存通知名称
       // LogObjectHolder.me().set(ConstantFactory.me().getCompanyManagerTitle(companyManagerId));

        this.companyManagerService.deleteById(managerId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     * @param companyManager
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = CompanyManagerMap.class)
    public Object update(CompanyManager companyManager) {
        //if (ToolUtil.isOneEmpty(companyManager, companyManager.getCompanyManagerId(), companyManager.getTitle(), companyManager.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //CompanyManager old = this.companyManagerService.selectById(companyManager.getCompanyManagerId());
        /**
		** add update logic here
		*/
		companyManager.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        companyManager.setLastUpdateTime(new Date());
        this.companyManagerService.updateById(companyManager);
        return SUCCESS_TIP;
    }
	
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
