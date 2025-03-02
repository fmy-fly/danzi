package com.yupi.yuoj.model.dto.Routine;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class RecordAddRequest implements Serializable {

    /**
     * 内容
     */
    private String date;

    /**
     * 标签列表
     */
    private boolean check;


    private static final long serialVersionUID = 1L;
}