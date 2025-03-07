package com.yupi.yuoj.model.dto.user;

import com.yupi.yuoj.common.PageRequest;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 开放平台id
     */
    private String unionId;

    /**
     * 公众号openId
     */
    private String mpOpenId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;


    /**
     * 用户座右铭
     */
    private String userSentence;
    /**
     * 用户QQ
     */
    private String userQQ;
    /**
     * 用户学校
     */
    private String userCollege;

    /**
     * 用户爱好
     */
    private List<String> userTags;

    private static final long serialVersionUID = 1L;
}