
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.foundation.manage.entity.VBC.JobCategory;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.JobCategoryService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.JobCategoryWrapper;
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
@RequestMapping("/jobCategory")
public class JobCategoryController extends BaseController {

    private static final String PREFIX = "/vbc/jobCategory/";

    @Autowired
    private JobCategoryService jobCategoryService;

    /**
     * 跳转到通知列表首页
     *
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "jobCategory.html";
    }

    /**
     * 跳转到添加通知
     *
     */
    @RequestMapping("/jobCategory_add")
    public String jobCategoryAdd() {
        return PREFIX + "jobCategory_add.html";
    }

    /**
     * 跳转到修改通知
     * @param jobCategoryId
     * @param model
     * @return
     */
    @RequestMapping("/jobCategory_update/{jobCategoryId}")
    public String jobCategoryUpdate(@PathVariable Long jobCategoryId, Model model) {
        JobCategory jobCategory = this.jobCategoryService.selectById(jobCategoryId);
        model.addAllAttributes(BeanUtil.beanToMap(jobCategory));
        LogObjectHolder.me().set(jobCategory);
        return PREFIX + "jobCategory_edit.html";
    }

    /**
     * 根据id查询
     * @param
     * @param
     * @return
     */
    @RequestMapping("/query/{jobCategoryId}")
    @ResponseBody
    public JobCategory queryByIdjob(@PathVariable Long jobCategoryId) {

        //List<JobCategory> jobCategories = jobCategoryService.selectByMap(condition);
        JobCategory jobCategory = this.jobCategoryService.selectById(jobCategoryId);
        return jobCategory;
    }


/**
* @Description 查询所有类别
* @Param [jobtype]
* @return java.lang.Object
*/
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public Object listAll(@RequestParam(value = "jobtype",required = false) String jobtype) {
        List<Map<String, Object>> list = this.jobCategoryService.list(jobtype);
        for(Map<String, Object> map:list){
            Integer status = (Integer) map.get("status");
            if(status==1){
                map.put("status","启用");
            }else{
                map.put("status","禁用");
            }
        }
        return super.warpObject(new JobCategoryWrapper(list));
    }
    /**
     * 获取通知列表
     * @param jobtype
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "jobtype",required = false) String jobtype) {

        String con="US";
        if(jobtype==null){
            con="CN";
        }
        //Map<String,Object>map=new ConcurrentHashMap<>();
        List<Map<String, Object>> list = this.jobCategoryService.list(con);
        for(Map<String, Object> map:list){
            Integer status = (Integer) map.get("status");
            if(status==1){
                map.put("status","启用");
            }else{
                map.put("status","禁用");
            }
        }
        return super.warpObject(new JobCategoryWrapper(list));
    }

    /**
     * 新增通知
     * @param jobCategory
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = JobCategoryMap.class)
    public Object add(JobCategory jobCategory) {
        //if (ToolUtil.isOneEmpty(jobCategory, jobCategory.getTitle(), jobCategory.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        jobCategory.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        jobCategory.setCreateTime(new Date());
		jobCategory.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        jobCategory.setLastUpdateTime(new Date());

        this.jobCategoryService.insert(jobCategory);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     * @param jobCategoryId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
   // @BussinessLog(value = "删除通知", key = "jobCategoryId", dict = JobCategoryMap.class)
    public Object delete(@RequestParam Long jobCategoryId) {

        //缓存通知名称
       // LogObjectHolder.me().set(ConstantFactory.me().getJobCategoryTitle(jobCategoryId));

        this.jobCategoryService.deleteById(jobCategoryId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     * @param jobCategory
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = JobCategoryMap.class)
    public Object update(JobCategory jobCategory) {
        //if (ToolUtil.isOneEmpty(jobCategory, jobCategory.getJobCategoryId(), jobCategory.getTitle(), jobCategory.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //JobCategory old = this.jobCategoryService.selectById(jobCategory.getJobCategoryId());
        /**
		** add update logic here
		*/
		jobCategory.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        jobCategory.setLastUpdateTime(new Date());
        this.jobCategoryService.updateById(jobCategory);
        return SUCCESS_TIP;
    }
	
	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
