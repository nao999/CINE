package com.onlinechat.controller;


import com.onlinechat.base.RestResponse;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.UserFriend;
import com.onlinechat.entity.vo.FriendGroupVO;
import com.onlinechat.entity.vo.UserFriendVO;
import com.onlinechat.service.UserFriendService;
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
 * @since 2021-10-24
 */
@RestController
@CrossOrigin
@RequestMapping("/userFriend")
public class UserFriendController {

    @Autowired
    private UserFriendService userFriendService;

    // 包含是否为群组人员的查好友
    @GetMapping("/getGroupsFriendList")
    public RestResponse<List<FriendGroupVO>> getGroupsFriendList(@RequestParam("groupId") String groupId){
        List<FriendGroupVO> friendGroupVOList = new ArrayList<>();
        try {
            friendGroupVOList = userFriendService.getFriendGroupList(groupId);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(friendGroupVOList);
    }

    @GetMapping("/isFriend")
    public RestResponse<Map<String,Boolean>> isFriend(@RequestParam("friendId") String friendId){
        Map<String,Boolean> map = new HashMap<>();
        try {
            boolean isFriend = userFriendService.isFriend(friendId);
            map.put("isFriend", isFriend);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(map);
    }

    @PostMapping("/addFriend")
    public RestResponse<String> addFriend(@RequestBody UserFriend userFriend){
        try {
            userFriendService.addFriend(userFriend);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/getFriendList")
    public RestResponse<List<UserFriendVO>> getFriendList(){
        List<UserFriendVO> friendList = new ArrayList<>();
        try {
            friendList = userFriendService.getFriendList();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(friendList);
    }

}

