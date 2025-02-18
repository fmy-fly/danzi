package com.yupi.yuoj.service;

import com.yupi.yuoj.model.entity.DailyCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.vo.DailyCheckVO;

import java.util.List;

/**
* @author fmy
* @description 针对表【daily_check】的数据库操作Service
* @createDate 2025-02-17 15:39:32
*/
public interface DailyCheckService extends IService<DailyCheck> {
    List<DailyCheckVO> getDailyCheck(User userLogin);
}
