package com.foundation.manage.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.entity.LoginLog;
import com.foundation.manage.mapper.LoginLogMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录记录 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> {

    /**
     * 获取登录日志列表
     *
     */
    public List<Map<String, Object>> getLoginLogs(Page<LoginLog> page, String beginTime, String endTime, String logName, String orderByField, boolean asc) {
        return this.baseMapper.getLoginLogs(page, beginTime, endTime, logName, orderByField, asc);
    }
}
