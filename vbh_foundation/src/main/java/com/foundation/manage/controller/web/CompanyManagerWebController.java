
package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.foundation.manage.entity.VBC.CompanyManager;
import com.foundation.manage.service.vbc.CompanyManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知控制器
 *
 * @author yt
 */
@Api(value="api-docs",tags = "管理团队接口")
@Controller
@RequestMapping("/api/companyManager")
public class CompanyManagerWebController extends BaseController {


    @Autowired
    private CompanyManagerService companyManagerService;


    /**
     * 获取通知列表
     * @param lang
     * @return
     */
    @ApiOperation(value ="管理团队",httpMethod = "POST")
    @RequestMapping(value = "/list" )
    @ResponseBody
    public List<CompanyManager> list(String lang) {
        if (lang == null || lang.equals("")) {
            lang = "CN";
        }
        Wrapper<CompanyManager> wrapper = new EntityWrapper<>();
        wrapper.eq("LANGUAGE", lang);
        wrapper.orderBy("SEQ",true);
        return companyManagerService.selectList(wrapper);
    }


}
