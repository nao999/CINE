package com.onlinechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.vo.UserVO;
import com.onlinechat.exception.OnlineChatException;
import com.onlinechat.mapper.TUserMapper;
import com.onlinechat.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlinechat.utils.JwtUtils;
import com.onlinechat.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-15
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    @Autowired
    private TUserMapper userMapper;

    @Override
    public TUser getUserByUserName(String username) {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();

        wrapper.eq("username", username);

        TUser tUser = baseMapper.selectOne(wrapper);
        return tUser;
    }

    @Override
    public UserVO searchFriend(String username) {
        UserVO userVO = userMapper.selectUser(username);
        return userVO;
    }

    @Override
    public String login(String userName, String password) {
        // 判断非空
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            throw new OnlineChatException(SystemCode.LOGINERROR.getCode(), SystemCode.LOGINERROR.getMessage());
        }

        // 得到用户信息
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();

        wrapper.eq("username", userName);

        TUser tUser = baseMapper.selectOne(wrapper);

        if(!password.equals(tUser.getPassword())){
            throw new OnlineChatException(SystemCode.LOGINERROR.getCode(), SystemCode.LOGINERROR.getMessage());
        }

        // 生成token
        String token = JwtUtils.getJwtToken(tUser.getId(), tUser.getUsername());

        return token;
    }

    @Override
    public UserVO getUserInfo() {
        TUser currentUser = Utils.getCurrentUser(userMapper);
        UserVO userVO = new UserVO();
        userVO.setId(currentUser.getId());
        userVO.setUsername(currentUser.getUsername());
        userVO.setUserProfile(currentUser.getUserProfile());
        return userVO;
    }

    @Override
    public void editProfile(TUser user) {
        TUser currentUser = Utils.getCurrentUser(userMapper);
        currentUser.setUserProfile(user.getUserProfile());
        userMapper.updateById(currentUser);
    }

    @Override
    public void updateUser(UserVO userVO) {
        TUser user = userMapper.selectById(userVO.getId());
        if(userVO.getUserProfile() != null) {
            user.setUserProfile(userVO.getUserProfile());
        }
        if(userVO.getUsername() != null) {
            user.setUsername(userVO.getUsername());
        }
        userMapper.updateById(user);
    }
}
