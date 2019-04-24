package com.foundation.manage.service.vbc;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.entity.VBC.CompanyManager;
import com.foundation.manage.mapper.CompanyManagerMapper;
import org.springframework.stereotype.Service;

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
public class CompanyManagerService extends ServiceImpl<CompanyManagerMapper, CompanyManager> {

    /**
     * 获取通知列表
     *
     */
    public List<Map<String, Object>> list(String condition) {
        return this.baseMapper.list(condition);
    }
}
