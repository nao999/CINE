package com.onlinechat.mapper;

import com.onlinechat.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlinechat.entity.vo.UserVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-10-15
 */
public interface TUserMapper extends BaseMapper<TUser> {

    UserVO selectUser(String username);
}
