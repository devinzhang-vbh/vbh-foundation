
package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.entity.VBC.Job;
import com.foundation.manage.entity.VBC.JobCategory;
import com.foundation.manage.entity.VBC.JobImage;
import com.foundation.manage.service.vbc.JobCategoryService;
import com.foundation.manage.service.vbc.JobImageService;
import com.foundation.manage.service.vbc.JobService;
import com.foundation.manage.util.MapUtils;
import com.foundation.manage.warpper.vbc.JobWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 通知控制器
 *
 * @author yt
 */
//@Api(value = "api-docs",tags = "招聘管理模块")
@RestController
@RequestMapping("/api/job")
public class JobWebController extends BaseController {


    @Autowired
    private JobService jobService;

    @Autowired
    private JobCategoryService jobCategoryService;
    @Autowired
    private JobImageService jobImageService;


    /**
     * 模糊搜索获取职位列表
     *
     * @param
     * @return
     */
    //@ApiOperation(value ="模糊搜索获取职位列表",httpMethod = "POST")
    @RequestMapping(value = "/listByName")
    @ResponseBody
    public Object listByName(@RequestParam(value = "jobname", required = false) String jobname) {
        List<Map<String, Object>> list = this.jobService.listByName(jobname);
        //List<Map<String, Object>> list = this.jobService.listAll();
        for (Map<String, Object> map : list) {
            Integer id = (Integer) map.get("jobCategoryId");
            JobCategory jobCategory = this.jobCategoryService.selectById(id);
            String name = "";
            if (jobCategory == null) {
                name = "该分类已被删除";
            } else {
                name = jobCategory.getJobCategoryName();
            }
            Integer STATUS = (Integer) map.get("status");
            map.put("jobCategoryId", name);
        }
        return super.warpObject(new JobWrapper(list));
    }

    /**
     * 获取职位列表
     *
     * @param
     * @return
     */
    //@ApiOperation(value = "获取职位列表",httpMethod = "POST")
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String lang, Integer currentPage, Integer pageSize, String condition,Integer categoryId) {
        //String con = "US";
        if (lang == null || lang.equals("")) {
            lang = "CN";
        }
        if(currentPage==null||currentPage.equals("")){
            currentPage=1;
        }
        if(pageSize==null||pageSize.equals("")){
            pageSize=5;
        }

        Wrapper<Job> wrapper = new EntityWrapper<>();
        Wrapper<JobImage> wrapper1 = new EntityWrapper<>();
        //为了分页方便 先查出来 然后在进行查询图片 将url追加过来
        wrapper.eq("LANGUAGE", lang);
        wrapper.eq("STATUS", 1);
        if(condition!=null){
            wrapper.like("JOB_NAME", condition).or().like("JOB_DESC", condition).or().like("JOB_NEED_DESC", condition);
        }
        if(categoryId!=null){
            wrapper.eq("JOB_CATEGORY_ID",categoryId);
        }
        Page<Job> page = new Page<>(currentPage, pageSize);
        Page<Job> jobPage = this.jobService.selectPage(page, wrapper);
        List<Map<String, Object>> returnList = new ArrayList<>();
        List<Map<String, Object>> returnLists = new ArrayList<>();
        Map<String,Object> returnMap=new HashMap<>();
        Map<String,Object> returnMaps=new HashMap<>();
        //List<JobDto> returnList = new ArrayList<>();
        for (Job job : jobPage.getRecords()) {
           // wrapper1.eq("JOB_IMAGE_JOB_ID", job.getJobId());
            List<Map<String, Object>> jobImages = this.jobImageService.queryByList(job.getJobId());
            //List<jobImage> jobImages = this.jobImageService.selectList(wrapper1);
            if (jobImages.size() != 0) {
                Map<String, Object> map1 = jobImages.get(0);
                String jobImageUrl = (String) map1.get("jobImageUrl");
                //String jobImageUrl = jobImage.getJobImageUrl();
                Map map = MapUtils.java2Map(job);
                map.put("jobImageUrl",jobImageUrl);
                //JobDto jobDto = MapUtils.map2Java(JobDto, map);
                //BeanUtils.copyProperties();
                returnList.add(map);
            }else{
                Map map = MapUtils.java2Map(job);
                map.put("jobImageUrl","该职位暂未上传图片");
                returnList.add(map);
            }
        }
        returnMap.put("total",jobPage.getTotal());
        returnMap.put("size",jobPage.getSize());
        returnMap.put("current",jobPage.getCurrent());
        returnMap.put("pages",jobPage.getPages());
        returnLists.add(returnMap);

        returnMaps.put("returnList",returnList);
        returnMaps.put("returnPage",returnLists);

        return returnMaps;
    }

    //@ApiOperation(value = "获取职位列表明细",httpMethod = "POST")
    @RequestMapping(value = "/detail")
    @ResponseBody
    public JSONObject detail(String jobNumber,String lang) {
        JSONObject result = new JSONObject();
        Wrapper<Job> wrapper = new EntityWrapper<>();
        wrapper.eq("JOB_NUMBER",jobNumber);
        wrapper.eq("LANGUAGE",lang);
        List<Job> jobs = this.jobService.selectList(wrapper);
        Job job=null;
        if(jobs.size()!=0){
             job = jobs.get(0);
            if(job!=null){
                if(job.getStatus()==0){
                    result.put("statu", 1);
                    result.put("msg", "该职位已经被下架,请刷新界面.");
                }else{
                    result.put("statu", 0);
                    result.put("data", job);
                }
            }else{
                result.put("statu", 1);
                result.put("msg", "该职位已经被删除,请刷新界面.");
            }
        }else{
            result.put("statu", 1);
            result.put("msg", "该职位未查询到,请刷新界面.");
        }
        return result;
    }

}
