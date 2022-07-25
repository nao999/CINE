package com.onlinechat.mapper;

import com.onlinechat.entity.TGroupMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlinechat.entity.vo.BriefMsgVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-11-06
 */
public interface TGroupMsgMapper extends BaseMapper<TGroupMsg> {

    List<TGroupMsg> getMessageList(String chatGroupId);

    BriefMsgVO getlastMsg(@Param("currentUserId") String currentUserId, @Param("groupId") String groupId);
}
