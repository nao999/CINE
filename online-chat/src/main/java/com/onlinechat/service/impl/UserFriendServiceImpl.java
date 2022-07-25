package com.onlinechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.UserFriend;
import com.onlinechat.entity.vo.FriendGroupVO;
import com.onlinechat.entity.vo.UserFriendVO;
import com.onlinechat.mapper.TUserMapper;
import com.onlinechat.mapper.UserFriendMapper;
import com.onlinechat.service.TGroupService;
import com.onlinechat.service.UserFriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlinechat.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-24
 */
@Service
public class UserFriendServiceImpl extends ServiceImpl<UserFriendMapper, UserFriend> implements UserFriendService {

    @Autowired
    private UserFriendMapper userFriendMapper;

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TGroupService groupService;

    @Override
    public void addFriend(UserFriend userFriend) {

        TUser user = Utils.getCurrentUser(userMapper);
        userFriend.setUserId(user.getId());
        // 当前用户要添加，同时对方也得记录！
        UserFriend userFriendReverse = new UserFriend();
        userFriendReverse.setUserId(userFriend.getFriendId());
        userFriendReverse.setFriendId(userFriend.getUserId());
        userFriendMapper.insert(userFriend);
        userFriendMapper.insert(userFriendReverse);
    }

    @Override
    public List<UserFriendVO> getFriendList() {
        List<UserFriendVO> friendList = new ArrayList<>();
        TUser user = Utils.getCurrentUser(userMapper);
//        String id = "1416192598015488001";
        friendList = userFriendMapper.getFriendList(user.getId());
        return friendList;
    }

    @Override
    public boolean isFriend(String friendId) {
        TUser user = Utils.getCurrentUser(userMapper);
        QueryWrapper<UserFriend> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getId());
        wrapper.eq("friend_id", friendId);
        UserFriend userFriend = userFriendMapper.selectOne(wrapper);
        return userFriend != null ;
    }

    @Override
    public List<FriendGroupVO> getFriendGroupList(String groupId) {
        List<FriendGroupVO> friendGroupList = new ArrayList<>();
        TUser user = Utils.getCurrentUser(userMapper);
//        String id = "1416192598015488001";
        friendGroupList = userFriendMapper.getFriendGroupList(user.getId());
        // 遍历判断是否为群组成员
        for(FriendGroupVO friendGroupVO :  friendGroupList){
            boolean isGroupMember = groupService.isGroupMember(groupId, friendGroupVO.getFriendId());
            friendGroupVO.setGroupMember(isGroupMember);
        }
        return friendGroupList;
    }
}
