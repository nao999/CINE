package com.onlinechat.mapper;

import com.onlinechat.entity.TGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlinechat.entity.vo.GroupVO;
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
public interface TGroupMapper extends BaseMapper<TGroup> {

    List<GroupVO> getGroupList(String id);

    String selectGroupName(String groupId);

    String selectGroupProfile(String groupId);
}
