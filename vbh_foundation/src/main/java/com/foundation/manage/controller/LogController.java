
package com.foundation.manage.controller;

import cn.hutool.core.bean.BeanUtil;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.common.annotion.BussinessLog;
import com.foundation.manage.common.annotion.Permission;
import com.foundation.manage.common.constant.Const;
import com.foundation.manage.common.constant.factory.PageFactory;
import com.foundation.manage.common.constant.state.BizLogType;
import com.foundation.manage.common.page.PageInfoBT;
import com.foundation.manage.entity.OperationLog;
import com.foundation.manage.service.OperationLogService;
import com.foundation.manage.warpper.LogWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 日志管理的控制器
 *
 * @author yt
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

    private static String PREFIX = "/system/log/";

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 跳转到日志管理的首页
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "log.html";
    }

    /**
     * 查询操作日志列表
     * @param beginTime
     * @param endTime
     * @param logName
     * @param logType
     * @return
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime,
                       @RequestParam(required = false) String endTime,
                       @RequestParam(required = false) String logName,
                       @RequestParam(required = false) Integer logType) {

        //获取分页参数
        Page<OperationLog> page = new PageFactory<OperationLog>().defaultPage();

        //根据条件查询操作日志
        List<Map<String, Object>> result = operationLogService.getOperationLogs(page, beginTime, endTime, logName,
                BizLogType.valueOf(logType), page.getOrderByField(), page.isAsc());

        page.setRecords(new LogWarpper(result).wrap());

        return new PageInfoBT<>(page);
    }

    /**
     * 查询操作日志详情
     * @param id
     * @return
     */
    @RequestMapping("/detail/{id}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable Long id) {
        OperationLog operationLog = operationLogService.selectById(id);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(operationLog);
        return super.warpObject(new LogWarpper(stringObjectMap));
    }

    /**
     * 清空日志
     * @return
     */
    @BussinessLog(value = "清空业务日志")
    @RequestMapping("/delLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from sys_operation_log");
        return SUCCESS_TIP;
    }
}
