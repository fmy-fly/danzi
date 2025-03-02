package com.yupi.yuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.exception.ThrowUtils;
import com.yupi.yuoj.model.dto.Routine.RecordAddRequest;
import com.yupi.yuoj.model.dto.question.JudgeCase;
import com.yupi.yuoj.model.dto.question.JudgeConfig;
import com.yupi.yuoj.model.dto.question.QuestionAddRequest;
import com.yupi.yuoj.model.entity.DailyCheck;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.vo.DailyCheckVO;
import com.yupi.yuoj.service.DailyCheckService;
import com.yupi.yuoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public BaseResponse<List<DailyCheckVO>>   getUserRoutine(HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        List<DailyCheckVO> dailyCheckList = dailyCheckService.getDailyCheck(loginUser);
        return ResultUtils.success(dailyCheckList);
    }

    @PostMapping("/add")
    public BaseResponse<Long> addRecord(@RequestBody RecordAddRequest recordAddRequest, HttpServletRequest request) {
        if (recordAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        DailyCheck dailyCheck = new DailyCheck();
        BeanUtils.copyProperties( recordAddRequest, dailyCheck);

        String dateS = recordAddRequest.getDate();

        // 创建 SimpleDateFormat 对象并指定格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            // 将字符串转换为 Date
            date = dateFormat.parse(dateS);
        } catch (ParseException e) {
            log.error("e: ", e);
        }
        dailyCheck.setDate(date);

        boolean check = recordAddRequest.isCheck();
        User loginUser = userService.getLoginUser(request);
        dailyCheck.setUserId(loginUser.getId());
        int checkedValue = check ? 1 : 0;
        dailyCheck.setQuestionId(1L);
        dailyCheck.setChecked(checkedValue);
        boolean result = dailyCheckService.save(dailyCheck);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newDailyCheckId = dailyCheck.getId();
        return ResultUtils.success(newDailyCheckId);
    }

}
