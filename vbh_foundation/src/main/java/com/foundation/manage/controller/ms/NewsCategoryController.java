
package com.foundation.manage.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;

import com.foundation.manage.entity.VBC.NewsCategory;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.service.vbc.NewsCategoryService;
import com.foundation.manage.shiro.ShiroKit;
import com.foundation.manage.warpper.vbc.NewsCategoryWrapper;
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
@RequestMapping("/newsCategory")
public class NewsCategoryController extends BaseController {

    private static final String PREFIX = "/vbc/newsCategory/";

    @Autowired
    private NewsCategoryService newsCategoryService;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "newsCategory.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/newsCategory_add")
    public String newsCategoryAdd() {

        return PREFIX + "newsCategory_add.html";
    }

    /**
     * 跳转到修改通知
     *
     * @param newsCategoryId
     * @param model
     * @return
     */
    @RequestMapping("/newsCategory_update/{newsCategoryId}")
    public String newsCategoryUpdate(@PathVariable Long newsCategoryId, Model model) {
        NewsCategory newsCategory = this.newsCategoryService.selectById(newsCategoryId);
        model.addAllAttributes(BeanUtil.beanToMap(newsCategory));
        LogObjectHolder.me().set(newsCategory);
        return PREFIX + "newsCategory_edit.html";
    }


    /**
     * 获取通知列表
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "newsCategory", required = false) String newsCategory) {
        List<Map<String, Object>> list = this.newsCategoryService.list(newsCategory);
//        for (Map<String, Object> map : list) {
//            for (String key : map.keySet()) {
//                System.out.println("key:"+key+"  value:"+map.get(key));
//            }
//
//        }
        return super.warpObject(new NewsCategoryWrapper(list));
    }

    /**
     * 新增通知
     *
     * @param newsCategory
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    //@BussinessLog(value = "新增通知", key = "title", dict = NewsCategoryMap.class)
    public Object add(NewsCategory newsCategory) {
        //if (ToolUtil.isOneEmpty(newsCategory, newsCategory.getTitle(), newsCategory.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        newsCategory.setCreateBy(ShiroKit.getUserNotNull().getAccount());
        newsCategory.setCreateTime(new Date());
        newsCategory.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        newsCategory.setLastUpdateTime(new Date());
        this.newsCategoryService.insert(newsCategory);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     *
     * @param newsCategoryId
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    // @BussinessLog(value = "删除通知", key = "newsCategoryId", dict = NewsCategoryMap.class)
    public Object delete(@RequestParam Long newsCategoryId) {

        //缓存通知名称
        // LogObjectHolder.me().set(ConstantFactory.me().getNewsCategoryTitle(newsCategoryId));

        this.newsCategoryService.deleteById(newsCategoryId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     *
     * @param newsCategory
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    //@BussinessLog(value = "修改通知", key = "title", dict = NewsCategoryMap.class)
    public Object update(NewsCategory newsCategory) {
        //if (ToolUtil.isOneEmpty(newsCategory, newsCategory.getNewsCategoryId(), newsCategory.getTitle(), newsCategory.getContent())) {
        //    throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        //}
        //NewsCategory old = this.newsCategoryService.selectById(newsCategory.getNewsCategoryId());
        /**
         ** add update logic here
         */
        newsCategory.setLastUpdateBy(ShiroKit.getUserNotNull().getAccount());
        newsCategory.setLastUpdateTime(new Date());
        this.newsCategoryService.updateById(newsCategory);
        return SUCCESS_TIP;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

}
