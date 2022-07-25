package com.onlinechat.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : OnlineExamException
 * @Description :
 * @Author : y
 * @Date: 2021-10-24 10:00
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineChatException extends RuntimeException {
    private Integer code;   // 状态码
    private String msg;     // 异常信息
}
