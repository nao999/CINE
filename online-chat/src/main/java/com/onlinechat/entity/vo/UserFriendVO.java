package com.onlinechat.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : UserFriendVO
 * @Description :
 * @Author : y
 * @Date: 2021-10-24 19:40
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendVO {
    private String id;
    private String friendId;
    private String friendUsername;
    private String userProfile;
}
