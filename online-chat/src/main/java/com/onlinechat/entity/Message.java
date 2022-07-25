package com.onlinechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName : Message
 * @Description :
 * @Author : y
 * @Date: 2021-10-25 10:22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String from;
    private String to;
    private String content;
    private Date createTime;
    private String fromNickname;
    private String fromUserProfile;
    private Integer messageTypeId;
}
