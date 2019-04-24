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
@TableName("vbc_website_company_manager")
@Data
public class CompanyManager implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
@TableId(value = "MANAGER_ID", type = IdType.AUTO)
private Long managerId;
//姓名
@TableField("MANAGER_NAME")
private String managerName;
@TableField("MANAGER_ENGLISH_NAME")
private String managerEnglishName;
//头像
@TableField("MANAGER_IMAGE")
private String managerImage;
//职位
@TableField("MANAGER_JOB")
private String managerJob;
//职位简介
@TableField("MANAGER_JOB_DESC")
private String managerJobDesc;
//序列
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
//语种
@TableField("LANGUAGE")
private String language;


}


