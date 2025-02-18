package com.yupi.yuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.exception.ThrowUtils;
import com.yupi.yuoj.model.entity.DailyCheck;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.vo.DailyCheckVO;
import com.yupi.yuoj.service.DailyCheckService;
import com.yupi.yuoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName：DailyCheckController
 * @Author: yq
 * @Date: 2025/2/18 11:04
 * @Description:
 */
@RestController
@RequestMapping("/DailyCheck")
@Slf4j
public class DailyCheckController {

    @Resource
    private UserService userService;
    @Resource
    private DailyCheckService dailyCheckService;

    @GetMapping("/get")
    public List<DailyCheck>  getUserRoutine( HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);

        List<DailyCheckVO> dailyCheckList = dailyCheckService.getDailyCheck(loginUser);

        return ResultUtils.success(dailyCheckList);
    }
}
