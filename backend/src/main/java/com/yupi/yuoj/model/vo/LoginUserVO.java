package com.yupi.yuoj.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONUtil;
import com.yupi.yuoj.model.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * 已登录用户视图（脱敏）
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 **/
@Data
public class LoginUserVO implements Serializable {

    /**
     * 用户 id
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
     * @param LoginUserVO
     * @return
     */
    public static User voToObj(LoginUserVO LoginUserVO) {
        if (LoginUserVO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(LoginUserVO, user);
        List<String> tagList = LoginUserVO.getUserTags();
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
    public static LoginUserVO objToVo(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO LoginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, LoginUserVO);
        List<String> userTagList = JSONUtil.toList(user.getUserTags(), String.class);
        LoginUserVO.setUserTags(userTagList);
        return LoginUserVO;
    }


    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}