package com.onlinechat.mapper;

import com.onlinechat.entity.UserFriend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlinechat.entity.vo.FriendGroupVO;
import com.onlinechat.entity.vo.UserFriendVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-10-24
 */
public interface UserFriendMapper extends BaseMapper<UserFriend> {

    List<UserFriendVO> getFriendList(String userId);

    List<FriendGroupVO> getFriendGroupList(String userId);
}
