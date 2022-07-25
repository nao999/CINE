package com.onlinechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlinechat.entity.GroupToUser;
import com.onlinechat.entity.TGroup;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.UserFriend;
import com.onlinechat.entity.vo.GroupMsgVO;
import com.onlinechat.entity.vo.GroupVO;
import com.onlinechat.entity.vo.UserFriendVO;
import com.onlinechat.entity.vo.UserVO;
import com.onlinechat.mapper.GroupToUserMapper;
import com.onlinechat.mapper.TGroupMapper;
import com.onlinechat.mapper.TUserMapper;
import com.onlinechat.service.TGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlinechat.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-05
 */
@Service
public class TGroupServiceImpl extends ServiceImpl<TGroupMapper, TGroup> implements TGroupService {

    @Autowired
    private TGroupMapper groupMapper;

    @Autowired
    private GroupToUserMapper groupToUserMapper;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public void createGroup(GroupVO group) {
        TGroup tGroup = new TGroup();
        TUser currentUser = Utils.getCurrentUser(userMapper);
        group.setGroupName(currentUser.getUsername() + "," + group.getGroupName());
        tGroup.setGroupName(group.getGroupName());
        groupMapper.insert(tGroup);
        // 保存群组成员
        for(UserFriendVO userVO : group.getMemberList()){
            GroupToUser groupToUser = new GroupToUser();
            groupToUser.setGroupId(tGroup.getId());
            if(userVO.getFriendId() == null){
                groupToUser.setUserId(userVO.getId());
            }else {
                groupToUser.setUserId(userVO.getFriendId());
            }
            groupToUserMapper.insert(groupToUser);
        }
        // 把发起人加入群组
        GroupToUser groupToUser = new GroupToUser();
        groupToUser.setGroupId(tGroup.getId());
        groupToUser.setUserId(currentUser.getId());
        groupToUserMapper.insert(groupToUser);
    }

    @Override
    public List<GroupVO> getGroupList() {
        TUser currentUser = Utils.getCurrentUser(userMapper);
        List<GroupVO> groupList = groupMapper.getGroupList(currentUser.getId());
        groupList.removeAll(Collections.singleton(null));
        return groupList;
    }

    @Override
    public GroupMsgVO getMemberList(String groupId) {
        List<UserVO> userVOList = groupToUserMapper.getMemberList(groupId);
        String groupName = groupMapper.selectGroupName(groupId);
        String groupProfile = groupMapper.selectGroupProfile(groupId);
        GroupMsgVO groupMsgVO = new GroupMsgVO();
        groupMsgVO.setId(groupId);
        groupMsgVO.setGroupName(groupName);
        groupMsgVO.setUserVOList(userVOList);
        groupMsgVO.setGroupProfile(groupProfile);
        return groupMsgVO;
    }

    @Override
    public void editGroup(String groupId, List<UserVO> memberList) {
        QueryWrapper<GroupToUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", groupId);
        groupToUserMapper.delete(queryWrapper);
        for(UserVO userVO : memberList){
            GroupToUser groupToUser = new GroupToUser();
            groupToUser.setUserId(userVO.getId());
            groupToUser.setGroupId(groupId);
            groupToUserMapper.insert(groupToUser);
        }
    }

    @Override
    public void updateGroupMsg(GroupVO groupVO) {
        TGroup group = groupMapper.selectById(groupVO.getId());
        if(groupVO.getGroupName() != null){
            group.setGroupName(groupVO.getGroupName());
        }
        if(groupVO.getGroupProfile() != null){
            group.setGroupProfile(groupVO.getGroupProfile());
        }
        groupMapper.updateById(group);

    }

    @Override
    public boolean isGroupMember(String groupId,String friendId) {
        QueryWrapper<GroupToUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id", groupId);
        queryWrapper.eq("user_id", friendId);
        GroupToUser groupToUser = groupToUserMapper.selectOne(queryWrapper);
        return groupToUser != null;
    }

    @Override
    public void addFriendGroup(GroupVO group) {
        // 保存群组成员
        for(UserFriendVO userVO : group.getMemberList()){
            GroupToUser groupToUser = new GroupToUser();
            groupToUser.setGroupId(group.getId());
            if(userVO.getFriendId() == null){
                groupToUser.setUserId(userVO.getId());
            }else {
                groupToUser.setUserId(userVO.getFriendId());
            }
            groupToUserMapper.insert(groupToUser);
        }
    }


}
