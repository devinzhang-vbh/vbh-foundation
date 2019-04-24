
package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.foundation.manage.entity.VBC.Disclaimer;
import com.foundation.manage.service.vbc.DisclaimerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知控制器
 *
 * @author yt
 */

//@Api(value="api-docs",tags = "免责声明接口")
@RestController
@RequestMapping("/api/disclaimer")
public class DisclaimerWebController extends BaseController {


    @Autowired
    private DisclaimerService disclaimerService;



    /**
     * 获取通知列表
     * @param lang
     * @return
     */
    @RequestMapping(value = "/list")
    //@ApiOperation(value = "免责声明",httpMethod = "POST")
    @ResponseBody
    public List<Disclaimer> list(String lang) {
        if(lang==null || lang.equals("")){
            lang="CN";
        }
        Wrapper<Disclaimer> wrapper=new EntityWrapper<>();
        wrapper.eq("LANGUAGE",lang);
        return disclaimerService.selectList(wrapper);
    }


}
