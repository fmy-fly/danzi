package com.yupi.yuoj.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.mapper.DailyCheckMapper;
import com.yupi.yuoj.mapper.PostFavourMapper;
import com.yupi.yuoj.model.entity.DailyCheck;
import com.yupi.yuoj.model.entity.Post;
import com.yupi.yuoj.model.entity.PostFavour;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.vo.DailyCheckVO;
import com.yupi.yuoj.service.DailyCheckService;
import com.yupi.yuoj.service.PostFavourService;
import com.yupi.yuoj.service.PostService;
import javax.annotation.Resource;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author fmy
* @description 针对表【daily_check】的数据库操作Service实现
* @createDate 2025-02-17 15:39:32
*/
@Service
public class DailyCheckServiceImpl extends ServiceImpl<DailyCheckMapper, DailyCheck>
    implements DailyCheckService {
    @Resource
    DailyCheckMapper dailyCheckMapper;

    @Override
    public List<DailyCheckVO> getDailyCheck(User userLogin) {
        QueryWrapper<DailyCheck> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userLogin.getId());
        List<DailyCheck> dailyChecks = dailyCheckMapper.selectList(queryWrapper);

        return dailyChecks.stream()
                .map(DailyCheckVO::objToVo)
                .collect(Collectors.toList());
    }
}




