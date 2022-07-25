package com.onlinechat.controller;


import com.onlinechat.base.RestResponse;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.vo.BriefMsgVO;
import com.onlinechat.entity.vo.GroupVO;
import com.onlinechat.entity.vo.MessageVO;
import com.onlinechat.entity.vo.UserFriendVO;
import com.onlinechat.mapper.TUserRecordsMapper;
import com.onlinechat.service.TGroupMsgService;
import com.onlinechat.service.TGroupService;
import com.onlinechat.service.TUserRecordsService;
import com.onlinechat.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-25
 */
@RestController
@CrossOrigin
@RequestMapping("/user-records")
public class TUserRecordsController {

    @Autowired
    private TUserRecordsService userRecordsService;

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private TGroupMsgService groupMsgService;

    @Autowired
    private TGroupService groupService;

    @GetMapping("/getUserRecords")
    public RestResponse<List<BriefMsgVO>> getUserRecords(){
        List<BriefMsgVO> msgList = new ArrayList<>();
        try {
            // 先查出朋友列表
            List<UserFriendVO> friendList = userFriendService.getFriendList();
            // 根据朋友列表查聊天信息
            List<BriefMsgVO> friendMsgList = userRecordsService.getMsgList(friendList);
            // 查出所有群组
            List<GroupVO> groupList = groupService.getGroupList();
            // 根据群组查聊天信息
            List<BriefMsgVO> groupMsgList =groupMsgService.getBriefMsgList(groupList);
            msgList.addAll(friendMsgList);
            msgList.addAll(groupMsgList);
            Collections.sort(msgList,new Comparator<BriefMsgVO>(){
                @Override
                public int compare(BriefMsgVO o1, BriefMsgVO o2) {
                    if ((o1.getMessageTime().getTime() > o2.getMessageTime().getTime())){
                        return -1;
                    }
                    if (o1.getMessageTime().getTime() == o2.getMessageTime().getTime()){
                        return 0;
                    }
                    return 1;
                }
            });
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(msgList);
    }

    @GetMapping("/getHistory")
    public RestResponse<List<MessageVO>> getHistory(@RequestParam("friendName") String friendName){
        List<MessageVO> messageVOList = null;
        try {
            messageVOList = userRecordsService.getRecordsList(friendName);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(messageVOList);
    }
}

