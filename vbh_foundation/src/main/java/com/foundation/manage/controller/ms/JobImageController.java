
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.foundation.manage.entity.VBC.Job;
import com.foundation.manage.entity.VBC.JobImage;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.JobImageService;
import com.foundation.manage.service.vbc.JobService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.JobImageWrapper;
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
@RequestMapping("/jobImage")
public class JobImageController extends BaseController {

    private static final String PREFIX = "/vbc/jobImage/";

    @Autowired
    private JobImageService jobImageService;
    @Autowired
    private JobService jobService;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "jobImage.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/jobImage_add")
    public String jobImageAdd() {
        return PREFIX + "jobImage_add.html";
    }

    /**
     * 跳转到修改通知
     *
     * @param jobImageId
     * @param model
     * @return
     */
    @RequestMapping("/jobImage_update/{jobImageId}")
    public String jobImageUpdate(@PathVariable Long jobImageId, Model model) {
        JobImage jobImage = this.jobImageService.selectById(jobImageId);
        model.addAllAttributes(BeanUtil.beanToMap(jobImage));
        LogObjectHolder.me().set(jobImage);
        return PREFIX + "jobImage_edit.html";
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
        String conditions="US";
        if(condition==null){
            conditions="CN";
        }
        List<Map<String, Object>> list = this.jobImageService.list(conditions);
        for (Map<String, Object> map : list
                ) {
            Integer jobImage = (Integer) map.get("jobImageJobId");
            if (jobImage != null) {
                Job job = jobService.selectById(jobImage);
                if(job!=null){
                    String jobname =job.getJobName()==null?"":job.getJobName();
                    map.put("jobName",jobname);
                }else{
                    map.put("jobName","");
                }

            }

        }
        return super.warpObject(new JobImageWrapper(list));
    }

    /**
     * 新增通知
     *
     * @param jobImage
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = JobImageMap.class)
    public Object add(JobImage jobImage) {
        //if (ToolUtil.isOneEmpty(jobImage, jobImage.getTitle(), jobImage.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        jobImage.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        jobImage.setCreateTime(new Date());
        jobImage.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        jobImage.setLastUpdateTime(new Date());
        this.jobImageService.insert(jobImage);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     *
     * @param jobImageId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    // @BussinessLog(value = "删除通知", key = "jobImageId", dict = JobImageMap.class)
    public Object delete(@RequestParam Long jobImageId) {

        //缓存通知名称
        // LogObjectHolder.me().set(ConstantFactory.me().getJobImageTitle(jobImageId));

        this.jobImageService.deleteById(jobImageId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     *
     * @param jobImage
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = JobImageMap.class)
    public Object update(JobImage jobImage) {
        //if (ToolUtil.isOneEmpty(jobImage, jobImage.getJobImageId(), jobImage.getTitle(), jobImage.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //jobImage old = this.jobImageService.selectById(jobImage.getJobImageId());
        /**
         ** add update logic here
         */
        jobImage.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        jobImage.setLastUpdateTime(new Date());
        this.jobImageService.updateById(jobImage);
        return SUCCESS_TIP;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
