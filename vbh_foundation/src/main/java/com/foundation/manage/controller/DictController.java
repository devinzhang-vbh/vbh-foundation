
package com.foundation.manage.controller;


import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.foundation.manage.common.annotion.BussinessLog;
import com.foundation.manage.common.annotion.Permission;
import com.foundation.manage.common.constant.Const;
import com.foundation.manage.common.constant.dictmap.DictMap;
import com.foundation.manage.common.constant.factory.ConstantFactory;
import com.foundation.manage.log.LogObjectHolder;
import com.foundation.manage.model.DictDto;
import com.foundation.manage.service.DictService;
import com.foundation.manage.warpper.DictWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 字典控制器
 *
 * @author yt
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private static final String PREFIX = "/system/dict/";

    @Autowired
    private DictService dictService;

    /**
     * 跳转到字典管理首页
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dict.html";
    }

    /**
     *
     * 跳转到添加字典类型
     * @return
     */
    @RequestMapping("/dict_add_type")
    public String deptAddType() {
        return PREFIX + "dict_add_type.html";
    }

    /**
     * 跳转到添加字典条目
     * @param dictId
     * @param model
     * @return
     */
    @RequestMapping("/dict_add_item")
    public String deptAddItem(@RequestParam("dictId") Long dictId, Model model) {
        model.addAttribute("dictTypeId", dictId);
        model.addAttribute("dictTypeName", ConstantFactory.me().getDictName(dictId));
        return PREFIX + "dict_add_item.html";
    }

    /**
     * 新增字典
     * @param dictDto
     * @return
     */
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(DictDto dictDto) {
        this.dictService.addDict(dictDto);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有字典列表
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.dictService.list(condition);
        return super.warpObject(new DictWarpper(list));
    }

    /**
     * 删除字典记录
     * @param dictId
     * @return
     */
    @BussinessLog(value = "删除字典记录", key = "dictId", dict = DictMap.class)
    @RequestMapping(value = "/delete")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData delete(@RequestParam Long dictId) {

        //缓存被删除的名称
        LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));

        this.dictService.deleteDict(dictId);

        return SUCCESS_TIP;
    }

}
