package com.onlinechat.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : ApplicationVO
 * @Description :
 * @Author : y
 * @Date: 2021-11-12 10:29
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVO {
    private String id;
    private String username;
    private String userProfile;
    // 申请id
    private String applicationId;
    // 申请信息
    private String applicationMsg;
    // 申请状态
    private Integer status;
}
