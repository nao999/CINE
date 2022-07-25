package com.onlinechat.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : FriendGroupVO
 * @Description :
 * @Author : y
 * @Date: 2022-07-04 20:22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendGroupVO {
    private String id;
    private String friendId;
    private String friendUsername;
    private String userProfile;
    // 是否群组成员
    private boolean isGroupMember;
}
