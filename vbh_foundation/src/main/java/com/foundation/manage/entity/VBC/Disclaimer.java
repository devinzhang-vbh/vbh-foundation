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
@TableName("vbc_website_disclaimer")
@Data
public class Disclaimer implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
@TableId(value = "DISCLAIMER_ID", type = IdType.AUTO)
private Long disclaimerId;
//创建人
@TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
private String createBy;
//创建时间
@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
private Date createTime;
//最后修改人
@TableField(value = "LAST_UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
private String lastUpdateBy;
//最后修改时间
@TableField(value = "LAST_UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
private Date lastUpdateTime;
//语言
@TableField("LANGUAGE")
private String language;
//免责声明
@TableField("DISCLAIMER_CONTENT")
private String disclaimerContent;
}


