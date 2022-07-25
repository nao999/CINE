package com.onlinechat.service;

import com.onlinechat.entity.UserFriend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlinechat.entity.vo.FriendGroupVO;
import com.onlinechat.entity.vo.UserFriendVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-24
 */
public interface UserFriendService extends IService<UserFriend> {

    void addFriend(UserFriend userFriend);

    List<UserFriendVO> getFriendList();

    boolean isFriend(String friendId);

    List<FriendGroupVO> getFriendGroupList(String groupId);

}
