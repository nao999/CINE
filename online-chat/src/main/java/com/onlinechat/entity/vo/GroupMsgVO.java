package com.onlinechat.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName : GroupMsgVO
 * @Description :
 * @Author : y
 * @Date: 2021-11-11 11:13
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMsgVO {
    private String id;
    private String groupName;
    private String groupProfile;
    private List<UserVO> userVOList;
}
