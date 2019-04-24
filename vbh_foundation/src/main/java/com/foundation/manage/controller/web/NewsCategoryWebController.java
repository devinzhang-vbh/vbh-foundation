package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.foundation.manage.entity.VBC.NewsCategory;
import com.foundation.manage.service.vbc.NewsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "api-docs", tags="文章分类接口")
@RestController
@RequestMapping("/api/newsCategory")
public class NewsCategoryWebController extends BaseController {

    @Autowired
    private NewsCategoryService newsCategoryService;

    @ApiOperation(value = "文章分类" ,httpMethod = "POST")
    @RequestMapping("/list")
    @ResponseBody
    public List<NewsCategory> list(String lang) {

        if (lang == null || lang.equals("")) {
            lang = "CN";
        }
        Wrapper<NewsCategory> wrapper = new EntityWrapper<>();
        wrapper.eq("LANGUAGE", lang);
        wrapper.eq("STATUS",1);
        return newsCategoryService.selectList(wrapper);
    }
}
