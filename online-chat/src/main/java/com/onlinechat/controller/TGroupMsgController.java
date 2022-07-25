package com.onlinechat.controller;


import com.onlinechat.base.RestResponse;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.vo.MessageVO;
import com.onlinechat.service.TGroupMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-06
 */
@RestController
@RequestMapping("/group-msg")
@CrossOrigin
public class TGroupMsgController {

    @Autowired
    private TGroupMsgService groupMsgService;

    @GetMapping("/getHistory")
    public RestResponse<List<MessageVO>> getHistory(@RequestParam("chatGroupId") String chatGroupId){
        List<MessageVO> messageVOList = null;
        try {
            messageVOList = groupMsgService.getMessageList(chatGroupId);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(messageVOList);
    }

}

