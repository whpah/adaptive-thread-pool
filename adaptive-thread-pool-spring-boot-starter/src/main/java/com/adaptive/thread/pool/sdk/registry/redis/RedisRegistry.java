package com.adaptive.thread.pool.sdk.registry.redis;

import com.adaptive.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import com.adaptive.thread.pool.sdk.registry.IRegistry;
import lombok.AllArgsConstructor;
import org.redisson.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Haipeng Wang
 * @description
 * @date 2024/12/6/9:19
 */
@AllArgsConstructor
public class RedisRegistry implements IRegistry {

    private final Logger log = LoggerFactory.getLogger(RedisRegistry.class);
    private final RedisClient redisClient;

    @Override
    public void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntities) {

    }

    @Override
    public void reportThreadPoolConfigParameters(ThreadPoolConfigEntity threadPoolConfigEntity) {

    }
}
