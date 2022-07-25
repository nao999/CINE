package com.onlinechat.controller;


import com.onlinechat.base.RestResponse;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.GroupToUser;
import com.onlinechat.entity.vo.EditGroupVO;
import com.onlinechat.entity.vo.GroupMsgVO;
import com.onlinechat.entity.vo.GroupVO;
import com.onlinechat.entity.vo.UserVO;
import com.onlinechat.mapper.GroupToUserMapper;
import com.onlinechat.service.TGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/group")
@CrossOrigin
public class TGroupController {

    @Autowired
    private TGroupService groupService;

    // 邀请好友进群
    @PostMapping("/addFriendsGroup")
    public RestResponse<String> addFriendsGroup(@RequestBody GroupVO group){
        try {
            groupService.addFriendGroup(group);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/isGroupMember")
    public RestResponse<Map<String,Boolean>> isGroupMember(@RequestParam("groupId") String groupId,@RequestParam("friendId") String friendId){
        Map<String,Boolean> map = new HashMap<>();
        try {
            boolean isGroupMember = groupService.isGroupMember(groupId,friendId);
            map.put("isGroupMember", isGroupMember);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResponse.ok(map);
    }

    @PutMapping("/updateGroupMsg")
    public RestResponse<String> updateGroupMsg(@RequestBody GroupVO groupVO){
        try {
            groupService.updateGroupMsg(groupVO);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PutMapping("/editGroup")
    public RestResponse<String> editGroup(
            @RequestBody EditGroupVO editGroupVO){
        String groupId = editGroupVO.getGroupId();
        List<UserVO> memberList = editGroupVO.getMemberList();

        try {
            groupService.editGroup(groupId,memberList);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/getGroupMsg")
    public RestResponse<GroupMsgVO> getMemberList(@RequestParam("groupId") String groupId){
        GroupMsgVO groupMsgVO = null;
        try {
            groupMsgVO = groupService.getMemberList(groupId);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(groupMsgVO);
    }

    @GetMapping("/getGroupList")
    public RestResponse<List<GroupVO>> getGroupList(){
        List<GroupVO> groupList = null;
        try {
            groupList = groupService.getGroupList();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(groupList);
    }

    @PostMapping("/createGroup")
    public RestResponse<String> createGroup(
            @RequestBody GroupVO group){
        try {
            groupService.createGroup(group);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }
}

