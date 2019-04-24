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
@TableName("vbc_website_contact_setup")
@Data
public class ContactSetup implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
@TableId(value = "CONTACT_ID", type = IdType.AUTO)
private Long contactId;
//官方渠道
@TableField("CONTACT_TYPE")
private String contactType;
//图标地址
@TableField("CONTACT_LOGO")
private String contactLogo;
//二维码地址
@TableField("CONTACT_QRCODE")
private String contactQrcode;
//链接地址
@TableField("CONTACT_URL")
private String contactUrl;
//排序
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


