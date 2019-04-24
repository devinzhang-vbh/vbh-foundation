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
@TableName("vbc_website_company_plan")
@Data
public class CompanyPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
@TableId(value = "PLAN_ID", type = IdType.AUTO)
private Long planId;
//规划时间
@TableField("PLAN_DATE")
private String planDate;
//事项1
@TableField("PLAN_ITEM1")
private String planItem1;
//事项2
@TableField("PLAN_ITEM2")
private String planItem2;
//事项3
@TableField("PLAN_ITEM3")
private String planItem3;
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


