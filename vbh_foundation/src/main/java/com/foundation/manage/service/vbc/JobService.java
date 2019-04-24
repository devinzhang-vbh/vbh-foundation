package com.foundation.manage.service.vbc;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.entity.VBC.Job;
import com.foundation.manage.mapper.JobMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通知表 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class JobService extends ServiceImpl<JobMapper, Job> {

    @Resource
    private JobMapper jobMapper;
    /**
     * 获取通知列表
     *
     */
    public List<Map<String, Object>> list(String condition) {
        return this.baseMapper.list(condition);
    }
    public List<Map<String, Object>> list(Map<String,Object> map) {
        return this.baseMapper.list1(map);
    }

    public List<Map<String, Object>> listAll() {
        return jobMapper.listAll();
    }

    public List<Map<String, Object>> listByName(String condition) {
        return jobMapper.listByName(condition);
    }

    public List<Map<String, Object>> listByLanguage(String condition) {
        return jobMapper.listByLanguage(condition);
    }
}
