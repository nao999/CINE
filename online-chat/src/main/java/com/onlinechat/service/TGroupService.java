package com.onlinechat.service;

import com.onlinechat.entity.TGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlinechat.entity.vo.GroupMsgVO;
import com.onlinechat.entity.vo.GroupVO;
import com.onlinechat.entity.vo.UserVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-05
 */
public interface TGroupService extends IService<TGroup> {

    void createGroup(GroupVO group);

    List<GroupVO> getGroupList();

    GroupMsgVO getMemberList(String groupId);

    void editGroup(String groupId, List<UserVO> memberList);

    void updateGroupMsg(GroupVO groupVO);

    boolean isGroupMember(String groupId,String friendId);

    void addFriendGroup(GroupVO group);
}
