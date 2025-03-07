package com.yupi.yuoj.model.dto.user;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 用户更新个人信息请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;



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