package com.onlinechat.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName : BriefMsgVO
 * @Description :
 * @Author : y
 * @Date: 2022-07-01 09:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BriefMsgVO {
    // 好友或群组id
    private  String friendOrGroupId;
    // 好友或群组名称
    private String friendOrGroupName;
    // 好友或群组头像
    private String avatar;
    // 最后消息发送人
    private UserVO lastMsgUser;
    // 上次的消息
    private String lastMsg;
    // 消息时间
    private Date messageTime;
    // 消息类型(0:好友,1:群组)
    private int msgType;
}
