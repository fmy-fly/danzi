package com.yupi.yuoj.model.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName daily_check
 */
@TableName(value ="daily_check")
@Data
public class DailyCheck implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户 id
     */
    private Long userId;
    /**
     * 日期
     */
    private Date date;
    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 是否打卡
     */
    private Integer checked;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */

    private Date updateTime;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}