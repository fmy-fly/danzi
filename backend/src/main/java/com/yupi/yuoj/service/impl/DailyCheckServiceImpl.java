package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.model.entity.DailyCheck;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.vo.DailyCheckVO;
import com.yupi.yuoj.service.DailyCheckService;
import com.yupi.yuoj.mapper.DailyCheckMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author fmy
* @description 针对表【daily_check】的数据库操作Service实现
* @createDate 2025-02-17 15:39:32
*/
@Service
public class DailyCheckServiceImpl extends ServiceImpl<DailyCheckMapper, DailyCheck>
    implements DailyCheckService{


    @Override
    public List<DailyCheckVO> getDailyCheck(User userLogin) {
        QueryWrapper<DailyCheck> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userLogin.getId());
        return DailyCheckMapper.selectList(queryWrapper);

    }
}




