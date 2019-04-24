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
@TableName("vbc_website_news")
@Data
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    @TableId(value = "NEWS_ID", type = IdType.AUTO)
    private Long newsId;
    //新闻分类
    @TableField("NEWS_CATEGORY")
    private Long newsCategory;
    //新闻标题
    @TableField("NEWS_TITLE")
    private String newsTitle;
    //新闻图片
    @TableField("NEWS_IMAGE")
    private String newsImage;
    //新闻链接
    @TableField("NEWS_LINK")
    private String newsLink;
    //新闻来源
    @TableField("NEWS_SOURCE")
    private String newsSource;
    //上架时间
    @TableField(value = "NEW_UP_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date newUpTime;
    //新闻内容
    @TableField("NEWS_CONTENT")
    private String newsContent;
    //状态
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
    //语言
    @TableField("LANGUAGE")
    private String language;

}


