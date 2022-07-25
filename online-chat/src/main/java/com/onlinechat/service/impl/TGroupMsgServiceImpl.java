package com.onlinechat.service.impl;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.onlinechat.entity.TGroupMsg;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.TUserRecords;
import com.onlinechat.entity.vo.*;
import com.onlinechat.mapper.TGroupMsgMapper;
import com.onlinechat.mapper.TUserMapper;
import com.onlinechat.service.TGroupMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlinechat.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CurrencyEditor;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
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
 * @since 2021-11-06
 */
@Service
public class TGroupMsgServiceImpl extends ServiceImpl<TGroupMsgMapper, TGroupMsg> implements TGroupMsgService {

    @Autowired
    private TGroupMsgMapper groupMsgMapper;

    @Autowired
    private TUserMapper userMapper;


    EmojiConverter emojiConverter = EmojiConverter.getInstance();

    @Override
    public MessageVO saveMessage(String messageEncode, String chatGroupId, String username, Integer messageType) {
        TGroupMsg groupMsg = new TGroupMsg();
        groupMsg.setGroupId(chatGroupId);
        groupMsg.setMessageContent(messageEncode);
        groupMsg.setMessageType(messageType);
        UserVO currentUser = userMapper.selectUser(username);
        groupMsg.setUserId(currentUser.getId());
        groupMsgMapper.insert(groupMsg);
        MessageVO messageVO = new MessageVO();
        messageVO.setId(groupMsg.getId());
        messageVO.setMessageType(messageType);
        messageVO.setMessageContent(messageEncode);
        messageVO.setUserProfile(currentUser.getUserProfile());
        messageVO.setUsername(username);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageVO.setCreateTime(formatter.format(groupMsg.getCreateTime()));
        return messageVO;
    }

    @Override
    public List<MessageVO> getMessageList(String chatGroupId) {
        List<TGroupMsg> messageRecordList = groupMsgMapper.getMessageList(chatGroupId);
        List<MessageVO> messageVOList = new ArrayList<>();
        for (TGroupMsg groupMsg : messageRecordList){
            MessageVO messageVO = new MessageVO();
            BeanUtils.copyProperties(groupMsg, messageVO);
            TUser user = userMapper.selectById(groupMsg.getUserId());
            messageVO.setUsername(user.getUsername());
            messageVO.setUserProfile(user.getUserProfile());
            String messageContent = emojiConverter.toUnicode(messageVO.getMessageContent());
            messageVO.setMessageContent(messageContent);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            messageVO.setCreateTime(formatter.format(groupMsg.getCreateTime()));
            messageVOList.add(messageVO);

        }
        return messageVOList;
    }

    @Override
    public List<BriefMsgVO> getBriefMsgList(List<GroupVO> groupList) {
        // 获得当前用户
        TUser currentUser = Utils.getCurrentUser(userMapper);
//        String currentUserId = "1416192598015488001";
        List<BriefMsgVO> list = new ArrayList<>();
        for(GroupVO groupVO : groupList){
            BriefMsgVO briefMsgVO = groupMsgMapper.getlastMsg(currentUser.getId(),groupVO.getId());
            if(briefMsgVO != null){
                briefMsgVO.setMsgType(1);
                list.add(briefMsgVO);
            }
        }
        return list;
    }
}
