package com.foundation.manage.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.entity.OperationLog;
import com.foundation.manage.mapper.OperationLogMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class OperationLogService extends ServiceImpl<OperationLogMapper, OperationLog> {

    /**
     * 获取操作日志列表
     *
     */
    public List<Map<String, Object>> getOperationLogs(Page<OperationLog> page, String beginTime, String endTime, String logName, String s, String orderByField, boolean asc) {
        return this.baseMapper.getOperationLogs(page, beginTime, endTime, logName, s, orderByField, asc);
    }

}
