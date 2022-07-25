package com.onlinechat.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName : GroupVO
 * @Description :
 * @Author : y
 * @Date: 2021-11-05 21:15
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupVO {
    private String id;
    private String groupName;
    private String groupProfile;
    private List<UserFriendVO> memberList;
}
