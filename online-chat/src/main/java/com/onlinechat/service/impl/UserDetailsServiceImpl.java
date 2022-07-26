package com.onlinechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlinechat.entity.SecurityUser;
import com.onlinechat.entity.TUser;
import com.onlinechat.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName : UserDetailsServiceImpl
 * @Description :
 * @Author : y
 * @Date: 2021-08-02 21:48
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private TUserService userService;



    /***
     * 根据账号获取用户信息
     * @param username:
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中取出用户信息
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        TUser user = userService.getOne(wrapper);

        // 判断用户是否存在
        if (null == user){
            throw new UsernameNotFoundException("用户名不存在！");
        }


//        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(user);
//        securityUser.setPermissionValueList(authorities);
        return securityUser;
    }
}
