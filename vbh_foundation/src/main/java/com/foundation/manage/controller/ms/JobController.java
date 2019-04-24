
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.foundation.manage.entity.VBC.Job;
import com.foundation.manage.entity.VBC.JobCategory;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.JobCategoryService;
import com.foundation.manage.service.vbc.JobService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.JobWrapper;
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

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知控制器
 *
 * @author yt
 */
@Controller
@RequestMapping("/job")
public class JobController extends BaseController {

    private static final String PREFIX = "/vbc/job/";

    @Autowired
    private JobService jobService;

    @Autowired
    private JobCategoryService jobCategoryService;

    /**
     * 跳转到通知列表首页
     *
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "job.html";
    }

    /**
     * 跳转到添加通知
     *
     */
    @RequestMapping("/job_add")
    public String jobAdd() {
        return PREFIX + "job_add.html";
    }

    /**
     * 跳转到修改通知
     * @param jobId
     * @param model
     * @return
     */
    @RequestMapping("/job_update/{jobId}")
    public String jobUpdate(@PathVariable Long jobId, Model model) {
        Map<String,Object> condition=new HashMap<>();
        Map<String,Object> mapresult=new HashMap<>();

        List<JobCategory> jobCategories = jobCategoryService.selectByMap(condition);
        Job job = this.jobService.selectById(jobId);
        //mapresult.put("jobCategories",jobCategories);
        //mapresult.put("job",job);
       // model.addAllAttributes(mapresult);
        model.addAllAttributes(BeanUtil.beanToMap(job));
        LogObjectHolder.me().set(job);
        return PREFIX + "job_edit.html";
    }
    /**
     * 根据id查询
     * @param
     * @param
     * @return
     */
    @RequestMapping("/query/{jobId}")
    @ResponseBody
    public Job queryByIdjob(@PathVariable Long jobId) {

        //List<JobCategory> jobCategories = jobCategoryService.selectByMap(condition);
        Job job = this.jobService.selectById(jobId);
        return job;
    }


    /**
     * 获取通知列表
     * @param
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "jobname" ,required=false) String jobname) {
        if(jobname==null){
            jobname="CN";
        }
        List<Map<String, Object>> list = this.jobService.list(jobname);
        //List<Map<String, Object>> list = this.jobService.listAll();
        for(Map<String, Object> map:list){
            Integer id = (Integer) map.get("jobCategoryId");
            JobCategory jobCategory = this.jobCategoryService.selectById(id);
            String name="";
            if(jobCategory==null){
                name="该分类已被删除";
            }else{
                name=jobCategory.getJobCategoryName();
            }
            Integer STATUS = (Integer) map.get("status");
            map.put("jobCategoryId",name);
        }
        return super.warpObject(new JobWrapper(list));
    }
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public Object listAll() {
        Wrapper<Job> wrapper=new EntityWrapper<>();
        List<Map<String, Object>> list = this.jobService.selectMaps(wrapper);
        //List<Map<String, Object>> list = this.jobService.listAll();
        for(Map<String, Object> map:list){
            Integer id = (Integer) map.get("jobCategoryId");
            JobCategory jobCategory = this.jobCategoryService.selectById(id);
            String name="";
            if(jobCategory==null){
                name="该分类已被删除";
            }else{
                name=jobCategory.getJobCategoryName();
            }
            Integer STATUS = (Integer) map.get("status");
            map.put("jobCategoryId",name);
        }
        return super.warpObject(new JobWrapper(list));
    }
    /**
    * @Description 在添加或者修改job时获取job分类列表
    * @Date 2019/3/7 18:40
    * @Param [job]
    * @return java.lang.Object
    */
    @RequestMapping(value = "/category_list")
    @ResponseBody
    public ResponseData list(){
        Map <String,Object> map=new HashMap();
        map.put("STATUS",1);
        List<JobCategory> jobCategories = jobCategoryService.selectByMap(map);

        return ResponseData.success(jobCategories);
    }

    /**
     * 新增通知
     * @param job
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = JobMap.class)
    public Object add(Job job) {
        //if (ToolUtil.isOneEmpty(job, job.getTitle(), job.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        String jobName="";
        try {
            jobName = URLDecoder.decode(job.getJobName(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        job.setJobName(jobName);
        job.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        job.setCreateTime(new Date());
        job.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        job.setLastUpdateTime(new Date());
        this.jobService.insert(job);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     * @param jobId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    // @BussinessLog(value = "删除通知", key = "jobId", dict = JobMap.class)
    public Object delete(@RequestParam Long jobId) {

        //缓存通知名称
        // LogObjectHolder.me().set(ConstantFactory.me().getJobTitle(jobId));

        this.jobService.deleteById(jobId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     * @param job
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = JobMap.class)
    public Object update(Job job) {
        //if (ToolUtil.isOneEmpty(job, job.getJobId(), job.getTitle(), job.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //Job old = this.jobService.selectById(job.getJobId());
        /**
         ** add update logic here
         */

        String jobName="";
        try {
            jobName = URLDecoder.decode(job.getJobName(), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        job.setJobName(jobName);
        job.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        job.setLastUpdateTime(new Date());
        this.jobService.updateById(job);
        return SUCCESS_TIP;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
