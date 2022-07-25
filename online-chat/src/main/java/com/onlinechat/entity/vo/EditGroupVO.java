package com.onlinechat.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName : EditGroupVO
 * @Description :
 * @Author : y
 * @Date: 2021-11-11 20:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditGroupVO {
    private String groupId;
    private List<UserVO> memberList;
}
