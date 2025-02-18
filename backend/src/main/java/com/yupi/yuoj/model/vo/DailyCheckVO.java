package com.yupi.yuoj.model.vo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yupi.yuoj.model.entity.DailyCheck;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帖子视图
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class DailyCheckVO implements Serializable {

    private final static Gson GSON = new Gson();

    /**
     * id
     */
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
    /**
     * 包装类转对象
     *
     * @param dailyCheckVO
     * @return
     */
    public static DailyCheck voToObj(DailyCheckVO dailyCheckVO) {
        if (dailyCheckVO == null) {
            return null;
        }
        DailyCheck dailyCheck = new DailyCheck();
        BeanUtils.copyProperties(dailyCheckVO, dailyCheck);
        return dailyCheck;
    }

    /**
     * 对象转包装类
     *
     * @param dailyCheck
     * @return
     */
    public static DailyCheckVO objToVo(DailyCheck dailyCheck) {
        if (dailyCheck == null) {
            return null;
        }
        DailyCheckVO dailyCheckVO = new DailyCheckVO();
        BeanUtils.copyProperties(dailyCheck, dailyCheckVO);
        return dailyCheckVO;
    }
}
