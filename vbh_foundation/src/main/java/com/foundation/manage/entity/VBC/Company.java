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
@TableName("vbc_website_company")
@Data
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
@TableId(value = "COMPANY_ID", type = IdType.AUTO)
private Long companyId;
//公司名称
@TableField("COMPANY_NAME")
private String companyName;
//公司电话
@TableField("COMPANY_PHONE")
private String companyPhone;
//公司传真
@TableField("COMPANY_FAX")
private String companyFax;
//公司邮箱
@TableField("COMPANY_EMAIL")
private String companyEmail;
//公司地址
@TableField("COMPANY_ADDRESS")
private String companyAddress;
//公司备案号
@TableField("COMPANY_RECORD_NO")
private String companyRecordNo;
//公司简介
@TableField("COMPANY_DESC")
private String companyDesc;
//公司文化简介
@TableField("COMPANY_CULTURE_DESC")
private String companyCultureDesc;
//公司荣誉简介
@TableField("COMPANY_REWARD_DESC")
private String companyRewardDesc;
//创建人
@TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
private String creatBy;
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


