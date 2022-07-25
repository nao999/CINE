package com.onlinechat.service;

import com.onlinechat.entity.TApplication;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlinechat.entity.vo.ApplicationVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-12
 */
public interface TApplicationService extends IService<TApplication> {

    void saveApplication(TApplication application);

    List<ApplicationVO> getApplications();

    void agreeApplication(String applicationId);
}
