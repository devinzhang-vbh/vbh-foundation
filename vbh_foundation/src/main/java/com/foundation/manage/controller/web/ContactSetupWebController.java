
package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.foundation.manage.entity.VBC.ContactSetup;
import com.foundation.manage.service.vbc.ContactSetupService;
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
@Controller
@Api(value="api-docs",tags = "联系我们接口")
@RequestMapping("/api/contactSetup")
public class ContactSetupWebController extends BaseController {


    @Autowired
    private ContactSetupService contactSetupService;




    /**
     * 获取通知列表
     * @param lang
     * @return
     */
    @ApiOperation(value = "联系我们列表",httpMethod = "POST")
    @RequestMapping(value = "/list")
    @ResponseBody
    public List<ContactSetup> list(String lang) {
        if (lang == null || lang.equals("")) {
            lang = "CN";
        }
        Wrapper<ContactSetup> wrapper= new EntityWrapper<>();
        wrapper.eq("LANGUAGE",lang);
        wrapper.orderBy("SEQ",true);
        return contactSetupService.selectList(wrapper);
    }

}
