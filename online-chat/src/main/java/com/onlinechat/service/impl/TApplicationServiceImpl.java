package com.onlinechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onlinechat.OnlineChatApplication;
import com.onlinechat.base.SystemCode;
import com.onlinechat.entity.TApplication;
import com.onlinechat.entity.TUser;
import com.onlinechat.entity.vo.ApplicationVO;
import com.onlinechat.exception.OnlineChatException;
import com.onlinechat.mapper.TApplicationMapper;
import com.onlinechat.mapper.TUserMapper;
import com.onlinechat.service.TApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onlinechat.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-12
 */
@Service
public class TApplicationServiceImpl extends ServiceImpl<TApplicationMapper, TApplication> implements TApplicationService {

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TApplicationMapper applicationMapper;


    @Override
    public void saveApplication(TApplication application) {
        TUser user = Utils.getCurrentUser(userMapper);
        application.setApplicationUserId(user.getId());
        QueryWrapper<TApplication> wrapper = new QueryWrapper<>();

        wrapper.eq("application_user_id", application.getApplicationUserId());
        wrapper.eq("application_friend_id", application.getApplicationFriendId());

        TApplication tApplication = applicationMapper.selectOne(wrapper);
        int agreeFlag = -1;
        if(tApplication != null){
            if(tApplication.getStatus() == 0){
                agreeFlag = 0;
            }else {
                agreeFlag = 1;
            }
        }
        if(agreeFlag == -1) {
            application.setStatus(0);
            applicationMapper.insert(application);
        }else if(agreeFlag == 0){
            throw new OnlineChatException(SystemCode.ALREADY_APPLICATION.getCode(), SystemCode.ALREADY_APPLICATION.getMessage());
        }else {
            throw new OnlineChatException(SystemCode.ALREADY_AGREE.getCode(), SystemCode.ALREADY_AGREE.getMessage());
        }
    }

    @Override
    public List<ApplicationVO> getApplications() {
        TUser user = Utils.getCurrentUser(userMapper);
        List<ApplicationVO> applicationVOList = applicationMapper.getApplications(user.getId());
        return applicationVOList;
    }

    @Override
    public void agreeApplication(String applicationId) {
        TApplication application = applicationMapper.selectById(applicationId);
        application.setStatus(1);
        applicationMapper.updateById(application);
    }
}
