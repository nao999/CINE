package com.onlinechat.service;

import com.onlinechat.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlinechat.entity.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-15
 */
public interface TUserService extends IService<TUser> {
    TUser getUserByUserName(String username);

    UserVO searchFriend(String username);

    String login(String userName, String password);

    UserVO getUserInfo();

    void editProfile(TUser user);

    void updateUser(UserVO userVO);
}
