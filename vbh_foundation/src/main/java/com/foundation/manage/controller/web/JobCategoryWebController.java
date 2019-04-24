
package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.foundation.manage.entity.VBC.JobCategory;
import com.foundation.manage.service.vbc.JobCategoryService;
import com.foundation.manage.warpper.vbc.JobCategoryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通知控制器
 *
 * @author yt
 */
//@Api(value = "api-docs",tags = "职位类别接口")
@RestController
@RequestMapping("/api/jobCategory")
public class JobCategoryWebController extends BaseController {


    @Autowired
    private JobCategoryService jobCategoryService;

    /**
     * 获取职位列表
     * @param lang
     * @return
     */
    //@ApiOperation(value = "获取职位列表",httpMethod = "POST")
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "lang",required = false) String lang) {
        String con="US";
        if(lang==null||lang.equals("")){
            con="CN";
        }
        Wrapper<JobCategory> wrapper = new EntityWrapper<>();
        wrapper.eq("LANGUAGE", lang);
        wrapper.eq("STATUS",1);
        wrapper.orderBy("SEQ",true);
        List<Map<String, Object>> list = this.jobCategoryService.selectMaps(wrapper);
        /*for(Map<String, Object> map:list){
            Integer status = (Integer) map.get("status");
            if(status==1){
                map.put("status","启用");
            }else{
                map.put("status","禁用");
            }
        }*/
        return super.warpObject(new JobCategoryWrapper(list));
    }

}
