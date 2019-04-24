package com.foundation.manage.service;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.entity.Relation;
import com.foundation.manage.mapper.RelationMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class RelationService extends ServiceImpl<RelationMapper, Relation> {

}
