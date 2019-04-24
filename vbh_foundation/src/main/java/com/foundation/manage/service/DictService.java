package com.foundation.manage.service;

import cn.hutool.core.bean.BeanUtil;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foundation.manage.common.exception.BizExceptionEnum;
import com.foundation.manage.entity.Dict;
import com.foundation.manage.mapper.DictMapper;
import com.foundation.manage.model.DictDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author yt
 * @since 2018-12-07
 */
@Service
public class DictService extends ServiceImpl<DictMapper, Dict> {

    @Resource
    private DictMapper dictMapper;

    /**
     * 添加字典
     *
     */
    public void addDict(DictDto dictDto) {

        if (ToolUtil.isOneEmpty(dictDto, dictDto.getCode(), dictDto.getName())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        if (ToolUtil.isEmpty(dictDto.getDictTypeId())) {
            this.addDictType(dictDto);
        } else {
            this.addDictItem(dictDto);
        }
    }

    /**
     * 添加字典类型
     *
     */
    private void addDictType(DictDto dictDto) {
        Dict dict = new Dict();
        BeanUtil.copyProperties(dictDto, dict);

        //类型的父级id都为0
        dict.setPid(0L);

        this.insert(dict);
    }

    /**
     * 添加字典子类型
     *
     */
    private void addDictItem(DictDto dictDto) {
        Dict dict = new Dict();
        BeanUtil.copyProperties(dictDto, dict);

        //字典的父级id为字典tyeId
        dict.setPid(dictDto.getDictTypeId());

        this.insert(dict);
    }

    /**
     * 删除字典
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDict(Long dictId) {

        //删除这个字典的子词典
        Wrapper<Dict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("PID", dictId);
        dictMapper.delete(dictEntityWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }

    /**
     * 根据编码获取词典列表
     *
     */
    public List<Dict> selectByCode(String code) {
        return this.baseMapper.selectByCode(code);
    }

    /**
     * 根据父类编码获取词典列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:26 PM
     */
    public List<Dict> selectByParentCode(String code) {
        return this.baseMapper.selectByParentCode(code);
    }

    /**
     * 查询字典列表
     *
     */
    public List<Map<String, Object>> list(String conditiion) {
        return this.baseMapper.list(conditiion);
    }
}
