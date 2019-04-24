package com.foundation.manage.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.foundation.manage.entity.LoginLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录记录 Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 获取登录日志
     * @param page
     * @param beginTime
     * @param endTime
     * @param logName
     * @param orderByField
     * @param isAsc
     * @return
     */
    List<Map<String, Object>> getLoginLogs(@Param("page") Page<LoginLog> page, @Param("beginTime") String beginTime,
                                           @Param("endTime") String endTime, @Param("logName") String logName, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);
}
