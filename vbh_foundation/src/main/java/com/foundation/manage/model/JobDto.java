package com.foundation.manage.model;

import lombok.Data;

import java.util.Date;

@Data
public class JobDto {
    private Long jobId;
    //职位分类
    private Long jobCategoryId;
    //岗位名称
    private String jobName;
    //工作地点
    private String jobLocation;
    //工作年限
    private String jobYear;
    //招聘人数
    private Long jobNum;
    //职位描述
    private String jobDesc;
    //任职要求
    private String jobNeedDesc;
    private Long status;
    //创建人
    private String createBy;
    //创建日期
    private Date createTime;
    //最后修改人
    private String lastUpdateBy;
    //最后修改日期
    private Date lastUpdateTime;
    //语种
    private String language;
    private String education;
    private String salary;
    private String jobHighlights;
    private String jobImageUrl;
}
