package com.yupi.yuoj.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONUtil;
import com.yupi.yuoj.model.dto.question.JudgeConfig;
import com.yupi.yuoj.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 用户视图（脱敏）
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
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
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 包装类转对象
     *
     * @param userVO
     * @return
     */
    public static User voToObj(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        List<String> tagList = userVO.getUserTags();
        if (tagList != null) {
            user.setUserTags(JSONUtil.toJsonStr(tagList));
        }
        return user;
    }

    /**
     * 对象转包装类
     *
     * @param user
     * @return
     */
    public static UserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        List<String> userTagList = JSONUtil.toList(user.getUserTags(), String.class);
        userVO.setUserTags(userTagList);
        return userVO;
    }

    private static final long serialVersionUID = 1L;
}