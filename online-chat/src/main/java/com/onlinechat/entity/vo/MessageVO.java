package com.onlinechat.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName : MessageVO
 * @Description :
 * @Author : y
 * @Date: 2021-11-01 18:08
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVO {
    private String id;
    private String username;
    private String userProfile;
    private String messageContent;
    private String createTime;
    // 消息类型(文字：0，图片: 1)
    private Integer messageType;
}
