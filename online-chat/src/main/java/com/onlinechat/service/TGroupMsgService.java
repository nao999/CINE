package com.onlinechat.service;

import com.onlinechat.entity.TGroupMsg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlinechat.entity.vo.BriefMsgVO;
import com.onlinechat.entity.vo.GroupVO;
import com.onlinechat.entity.vo.MessageVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-06
 */
public interface TGroupMsgService extends IService<TGroupMsg> {

    MessageVO saveMessage(String messageEncode, String chatGroupId, String username, Integer messageType);

    List<MessageVO> getMessageList(String chatGroupId);

    List<BriefMsgVO> getBriefMsgList(List<GroupVO> groupList);
}
