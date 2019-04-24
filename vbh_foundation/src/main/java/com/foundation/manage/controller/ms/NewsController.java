
package com.foundation.manage.controller.ms;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.foundation.manage.entity.VBC.News;
import com.foundation.manage.entity.VBC.NewsCategory;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.NewsCategoryService;
import com.foundation.manage.service.vbc.NewsService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.util.DateUtil;
import com.foundation.manage.util.MapUtils;
import com.foundation.manage.warpper.vbc.NewsWrapper;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
@RequestMapping("/news")
public class NewsController extends BaseController {

    private static final String PREFIX = "/vbc/news/";

    @Autowired
    private NewsService newsService;


    @Autowired
    private NewsCategoryService newsCategoryService;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "news.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/news_add")
    public String newsAdd() {
        return PREFIX + "news_add.html";
    }

    /**
     * 跳转到修改通知
     *
     * @param newsId
     * @param model
     * @return
     */
    @RequestMapping("/news_update/{newsId}")
    public String newsUpdate(@PathVariable Long newsId, Model model) {
        News news = this.newsService.selectById(newsId);
        System.out.println(news.getNewsContent());
        Map map = MapUtils.java2Map(news);
        Date newUpTime = news.getNewUpTime() != null ? news.getNewUpTime() : news.getLastUpdateTime();
        String newUpTimeStr = DateUtil.date2Str(newUpTime,"yyyy-MM-dd HH:mm:ss");
        map.put("newUpTime",newUpTimeStr);
        /*for (Map<String, Object> map : list) {
            Object newsCategory = map.get("newsCategory");
            if (newsCategory != null) {
                NewsCategory category = newsCategoryService.selectById(((Integer)newsCategory));
                String newsCategoryName = category == null ? "" : category.getNewsCategoryName();
                map.put("newsCategoryName", newsCategoryName);
            }

        }*/
        model.addAllAttributes(map);
        LogObjectHolder.me().set(news);
        return PREFIX + "news_edit.html";
    }


    /**
     * 获取通知列表
     *
     * @param titleQuery
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "titleQuery", required=false) String titleQuery) {
        List<Map<String, Object>> list = this.newsService.list(titleQuery);

        for (Map<String, Object> map : list) {
            Object newsCategory = map.get("newsCategory");
            if (newsCategory != null) {
                NewsCategory category = newsCategoryService.selectById(((Integer)newsCategory));
                String newsCategoryName = category == null ? "" : category.getNewsCategoryName();
                map.put("newsCategoryName", newsCategoryName);
            }

        }


        return super.warpObject(new NewsWrapper(list));
    }

    /**
     * 新增通知
     *
     * @param news
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = NewsMap.class)
    public Object add(News news) {
        //if (ToolUtil.isOneEmpty(news, news.getTitle(), news.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}

        String content = news.getNewsContent();
        System.out.println("content:" + content);
        String newContent = content;
        try {
            newContent = URLDecoder.decode(content, "UTF-8");
            System.out.println("newContent:" + newContent);
            news.setNewsContent(newContent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        news.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        news.setCreateTime(new Date());
        news.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        news.setLastUpdateTime(new Date());
        this.newsService.insert(news);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     *
     * @param newsId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    // @BussinessLog(value = "删除通知", key = "newsId", dict = NewsMap.class)
    public Object delete(@RequestParam Long newsId) {

        //缓存通知名称
        // LogObjectHolder.me().set(ConstantFactory.me().getNewsTitle(newsId));

        this.newsService.deleteById(newsId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     *
     * @param news
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = NewsMap.class)
    public Object update(News news) {
        //if (ToolUtil.isOneEmpty(news, news.getNewsId(), news.getTitle(), news.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //News old = this.newsService.selectById(news.getNewsId());
        /**
         ** add update logic here
         */
        String content = news.getNewsContent();
        System.out.println("content:" + content);
        String newContent = content;
        try {
            newContent = URLDecoder.decode(content, "UTF-8");
            System.out.println("newContent:" + newContent);
            news.setNewsContent(newContent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(news.getStatus()==2){
            news.setStatus(0l);
        }
        news.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        news.setLastUpdateTime(new Date());
        this.newsService.updateById(news);
        return SUCCESS_TIP;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
