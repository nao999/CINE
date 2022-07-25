package com.onlinechat.service.impl;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.onlinechat.entity.Message;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.TUserRecords;
import com.onlinechat.entity.vo.BriefMsgVO;
import com.onlinechat.entity.vo.MessageVO;
import com.onlinechat.entity.vo.UserFriendVO;
import com.onlinechat.entity.vo.UserVO;
import com.onlinechat.mapper.TUserMapper;
import com.onlinechat.mapper.TUserRecordsMapper;
import com.onlinechat.service.TUserRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlinechat.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-25
 */
@Service
public class TUserRecordsServiceImpl extends ServiceImpl<TUserRecordsMapper, TUserRecords> implements TUserRecordsService {

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TUserRecordsMapper userRecordsMapper;

    EmojiConverter emojiConverter = EmojiConverter.getInstance();


    @Override
    public MessageVO saveMessage(String message, String username, String friendName,Integer messageType) {
        UserVO user = userMapper.selectUser(username);
        UserVO friendUser = userMapper.selectUser(friendName);
        TUserRecords userRecords = new TUserRecords();
        userRecords.setUserId(user.getId());
        userRecords.setReceiverId(friendUser.getId());
        userRecords.setMessageContent(message);
        userRecords.setMessageType(messageType);
        userRecordsMapper.insert(userRecords);
        MessageVO messageVO = new MessageVO();
        messageVO.setId(userRecords.getId());
        messageVO.setUsername(username);
        messageVO.setUserProfile(user.getUserProfile());
        messageVO.setMessageContent(message);
        messageVO.setMessageType(messageType);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageVO.setCreateTime(formatter.format(userRecords.getCreateTime()));

        return messageVO;
    }

    @Override
    public List<MessageVO> getRecordsList(String friendName) {
        TUser currentUser = Utils.getCurrentUser(userMapper);
        UserVO friendUser = userMapper.selectUser(friendName);
        List<TUserRecords> userRecordsList = userRecordsMapper.getRecords(currentUser.getId(),friendUser.getId());
        List<MessageVO> messageVOList = new ArrayList<>();
        for (TUserRecords userRecords : userRecordsList){
            MessageVO messageVO = new MessageVO();
            BeanUtils.copyProperties(userRecords,messageVO);
            TUser msgUser = userMapper.selectById(userRecords.getUserId());
            messageVO.setUsername(msgUser.getUsername());
            messageVO.setUserProfile(msgUser.getUserProfile());
            String messageContent = emojiConverter.toUnicode(messageVO.getMessageContent());
            messageVO.setMessageContent(messageContent);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            messageVO.setCreateTime(formatter.format(userRecords.getCreateTime()));
            messageVO.setMessageType(userRecords.getMessageType());
            messageVOList.add(messageVO);

        }
        return messageVOList;
    }

    @Override
    public List<BriefMsgVO> getMsgList(List<UserFriendVO> friendList) {
        // 获得当前用户
        TUser currentUser = Utils.getCurrentUser(userMapper);
//        String currentUserId = "1416192598015488001";
        List<BriefMsgVO> list = new ArrayList<>();
        for(UserFriendVO userFriendVO : friendList){
            BriefMsgVO briefMsgVO = userRecordsMapper.getlastMsg(currentUser.getId(),userFriendVO.getFriendId());
            if(briefMsgVO != null){
                briefMsgVO.setMsgType(0);
                list.add(briefMsgVO);
            }
        }
        return list;
    }
}
