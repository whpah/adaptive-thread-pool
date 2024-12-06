package com.adaptive.thread.pool.sdk.registry;

import com.adaptive.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @author Haipeng Wang
 * @description 注册中心接口
 * @date 2024/12/6/9:19
 */
public interface IRegistry {

    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntities);

    void reportThreadPoolConfigParameters(ThreadPoolConfigEntity threadPoolConfigEntity);
}
