package com.onlinechat.service;

import com.onlinechat.entity.TUserRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlinechat.entity.vo.BriefMsgVO;
import com.onlinechat.entity.vo.MessageVO;
import com.onlinechat.entity.vo.UserFriendVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-25
 */
public interface TUserRecordsService extends IService<TUserRecords> {

    MessageVO saveMessage(String message, String username, String friendName,Integer messageType);

    List<MessageVO> getRecordsList(String friendName);

    List<BriefMsgVO> getMsgList(List<UserFriendVO> friendList);

}
