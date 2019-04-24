package com.foundation.manage.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.entity.OperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 获取操作日志
     * @param page
     * @param beginTime
     * @param endTime
     * @param logName
     * @param logType
     * @param orderByField
     * @param isAsc
     * @return
     */
    List<Map<String, Object>> getOperationLogs(@Param("page") Page<OperationLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("logType") String logType, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);


}
