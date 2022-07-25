package com.onlinechat.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlinechat.entity.TApplication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.vo.ApplicationVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-11-12
 */
public interface TApplicationMapper extends BaseMapper<TApplication> {

    List<ApplicationVO> getApplications(String id);

}
