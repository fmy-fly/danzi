package com.yupi.yuoj.model.vo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yupi.yuoj.model.entity.DailyCheck;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 帖子视图
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class DailyCheckVO implements Serializable {

    private final static Gson GSON = new Gson();
    private static final Logger log = LoggerFactory.getLogger(DailyCheckVO.class);

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
    private String stringDate;
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

        // 日期字符串
        String dateString = "2025-02-18";

        // 创建 SimpleDateFormat 对象并指定格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            // 将字符串转换为 Date
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            log.error("e: ", e);
        }
        dailyCheck.setDate(date);

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

        // 转换日期格式为 yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(dailyCheck.getDate());
        dailyCheckVO.setStringDate(formattedDate);
        return dailyCheckVO;
    }
}
