package com.foundation.manage.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.foundation.manage.entity.VBC.Job;
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
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 获取通知列表
     * @param condition
     * @return
     */
    List<Map<String, Object>> list(@Param("condition") String condition);
    //@Select("select job.*, image.JOB_IMAGE_URL FROM vbc_website_job job ,vbc_website_job_image image WHERE job.JOB_ID=image.JOB_IMAGE_JOB_ID order by CREATE_TIME DESC")
    List<Map<String, Object>> list1(Map<String,Object> map);

    List<Map<String, Object>> listAll();

    List<Map<String, Object>> listByLanguage(@Param("condition") String condition);

    List<Map<String, Object>> listByName(@Param("condition") String condition);

}
