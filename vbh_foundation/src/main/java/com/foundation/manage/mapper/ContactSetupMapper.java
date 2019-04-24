package com.foundation.manage.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.foundation.manage.entity.VBC.ContactSetup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通知表 Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
public interface ContactSetupMapper extends BaseMapper<ContactSetup> {

    /**
     * 获取通知列表
     * @param condition
     * @return
     */
    List<Map<String, Object>> list(@Param("condition") String condition);

}
