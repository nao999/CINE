package com.onlinechat.mapper;

import com.onlinechat.entity.TUserRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlinechat.entity.vo.BriefMsgVO;
import com.onlinechat.entity.vo.MessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-10-25
 */
public interface TUserRecordsMapper extends BaseMapper<TUserRecords> {

    List<TUserRecords> getRecords(@Param("currentUserId") String currentUserId, @Param("friendUserId") String friendUserId);

    BriefMsgVO getlastMsg(@Param("currentUserId") String currentUserId,@Param("friendId") String friendId);
}
