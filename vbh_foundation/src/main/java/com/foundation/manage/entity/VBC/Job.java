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
@TableName("vbc_website_job")
@Data
public  class Job implements Serializable {

 private static final long serialVersionUID = 1L;

 //主键
 @TableId(value = "JOB_ID", type = IdType.AUTO)
 private Long jobId;
 //职位分类
 @TableField("JOB_CATEGORY_ID")
 private Long jobCategoryId;
 //岗位名称
 @TableField("JOB_NAME")
 private String jobName;
 //工作地点
 @TableField("JOB_LOCATION")
 private String jobLocation;
 //工作年限
 @TableField("JOB_YEAR")
 private String jobYear;
 //招聘人数
 @TableField("JOB_NUM")
 private String jobNum;
 //职位描述
 @TableField("JOB_DESC")
 private String jobDesc;
 //任职要求
 @TableField("JOB_NEED_DESC")
 private String jobNeedDesc;
 //0 待上架  1 已上架  2. 已下架
 @TableField("STATUS")
 private Long status;
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
 //语种
 @TableField("LANGUAGE")
 private String language;
 @TableField("EDUCATION")
 private String education;
 @TableField("SALARY")
 private String salary;
 @TableField("JOB_HIGHLIGHTS")
 private String jobHighlights;
 @TableField("JOB_NUMBER")
 private String jobNumber;





}

