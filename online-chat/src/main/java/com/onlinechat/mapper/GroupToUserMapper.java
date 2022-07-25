package com.onlinechat.mapper;

import com.onlinechat.entity.GroupToUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlinechat.entity.vo.UserVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-11-05
 */
public interface GroupToUserMapper extends BaseMapper<GroupToUser> {

    List<UserVO> getMemberList(String groupId);
}
