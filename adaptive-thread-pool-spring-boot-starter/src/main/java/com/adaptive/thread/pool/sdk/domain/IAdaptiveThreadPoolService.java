package com.adaptive.thread.pool.sdk.domain;

import com.adaptive.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @author Haipeng Wang
 * @description 动态线程池服务
 * @date 2024/12/5/16:23
 */
public interface IAdaptiveThreadPoolService {

    List<ThreadPoolConfigEntity> queryThreadPoolList();

    ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName);

    void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity);
}
