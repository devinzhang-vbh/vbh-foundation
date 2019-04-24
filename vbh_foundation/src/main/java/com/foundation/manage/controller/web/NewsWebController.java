package com.foundation.manage.controller.web;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.entity.VBC.News;
import com.foundation.manage.service.vbc.NewsCategoryService;
import com.foundation.manage.service.vbc.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "api-docs", tags = "文章列表接口")
@RestController
@RequestMapping("/api/news")
public class NewsWebController extends BaseController {

    @Autowired
    private NewsService newsService;


    @Autowired
    private NewsCategoryService newsCategoryService;


    @ApiOperation(value = "文章列表",httpMethod = "POST")
    @RequestMapping("/list")
    @ResponseBody
    public Page<News> getNewsList(String lang, Integer currentPage, Integer pageSize,Integer newsCategoryId) {
        if (lang == null || lang.equals("")) {
            lang = "CN";
        }
        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 20 : pageSize;

        Page<News> page = new Page<>(currentPage, pageSize);

        Wrapper<News> wrapper = new EntityWrapper<>();
        wrapper.eq("LANGUAGE", lang);
        wrapper.eq("STATUS",1);

        if(null != newsCategoryId){
            wrapper.eq("NEWS_CATEGORY", newsCategoryId);
        }
        //wrapper.orderBy("LAST_UPDATE_TIME",false);
        wrapper.orderBy("NEW_UP_TIME",false);
        return newsService.selectPage(page, wrapper);
    }


    @RequestMapping("/detail")
    @ResponseBody
    public News getNewsList(String lang, int newsId) {
        if (lang == null || lang.equals("")) {
            lang = "CN";
        }
        return newsService.selectById(newsId);
    }



    @RequestMapping("/changeStatus")
    @ResponseBody
    public JSONObject changeStatus(Long newsId, String op) {

        JSONObject result = new JSONObject();
        result.put("status", 0);

        News news = newsService.selectById(newsId);
        if (news == null) {
            result.put("msg", "新闻不存在，请刷新页面重试.");
        } else {
            if (op.equals("upStatus")) {
                if (news.getStatus() != 0) {
                    result.put("msg", "新闻已发布或下架请刷新页面重试..");
                } else {
                    news.setStatus(new Long(1));
                    newsService.updateById(news);
                    result.put("status", 1);
                    result.put("msg", "发布成功.");
                }
                //发布
            } else {
                //下架
                if (news.getStatus() == 2) {
                    result.put("msg", "新闻已下架请刷新页面重试..");
                }else if(news.getStatus() == 0){
                    result.put("msg", "新闻未上线不能发布..");
                }else {
                    news.setStatus(new Long(2));
                    newsService.updateById(news);
                    result.put("status", 1);
                    result.put("msg", "下架成功.");
                }
            }
        }
        return result;
    }


}
