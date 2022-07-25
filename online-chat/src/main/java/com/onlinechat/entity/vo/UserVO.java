package com.onlinechat.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : UserVO
 * @Description :
 * @Author : y
 * @Date: 2021-10-23 21:40
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String id;

    @ApiModelProperty(value = "登录账号")
    private String username;

    private String userProfile;

}
