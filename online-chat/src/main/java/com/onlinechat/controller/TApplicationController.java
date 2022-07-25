package com.onlinechat.controller;


import com.onlinechat.base.RestResponse;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.TApplication;
import com.onlinechat.entity.UserFriend;
import com.onlinechat.entity.vo.ApplicationVO;
import com.onlinechat.exception.OnlineChatException;
import com.onlinechat.service.TApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-12
 */
@RestController
@CrossOrigin
@RequestMapping("/application")
public class TApplicationController {

    @Autowired
    private TApplicationService applicationService;

    @PutMapping("/agreeApplication")
    public RestResponse<ApplicationVO> agreeApplication(@RequestBody Map<String,String> map){
        String applicationId = map.get("applicationId");
        try {
            applicationService.agreeApplication(applicationId);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/getApplications")
    public RestResponse<List<ApplicationVO>> getApplications(){
        List<ApplicationVO> applicationVOList = null;
        try {
            applicationVOList = applicationService.getApplications();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(applicationVOList);
    }

    @PostMapping("/saveApplication")
    public RestResponse<String> saveApplication(@RequestBody TApplication application){

        try {
            applicationService.saveApplication(application);
        } catch (OnlineChatException e) {
            return RestResponse.fail(e.getCode(), e.getMsg());
        }

        return RestResponse.ok();
    }

}

