package com.foundation.manage.entity.VBC;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 通知表
 * </p>
 *
 * @author yt
 */
@TableName("vbc_website_job_category")
@Data
public class JobCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
@TableId(value = "JOB_CATEGORY_ID", type = IdType.AUTO)
private Long jobCategoryId;
//职位分类名称
@TableField("JOB_CATEGORY_NAME")
private String JobCategoryName;
//图片地址
@TableField("JOB_CATEGORY_IMAGE")
private String jobCategoryImage;
//状态
@TableField("STATUS")
private Long status;
//序号
@TableField("SEQ")
private Long seq;
//创建人
@TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
private String createBy;
//创建日期
@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
private Date createTime;
//最后修改人
@TableField(value = "LAST_UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
private String lastUpdateBy;
//最后修改日期
@TableField(value = "LAST_UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
private Date lastUpdateTime;
//语言
@TableField("LANGUAGE")
private String language;



}


