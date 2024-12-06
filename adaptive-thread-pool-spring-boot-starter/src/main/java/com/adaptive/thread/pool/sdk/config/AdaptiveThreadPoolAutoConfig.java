package com.adaptive.thread.pool.sdk.config;

import com.adaptive.thread.pool.sdk.domain.model.AdaptiveThreadPoolService;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Haipeng Wang
 * @description 动态配置入口
 * @date 2024/12/4/15:24
 */
@Configurable
@EnableConfigurationProperties(AdaptiveThreadPoolAutoProperties.class)
public class AdaptiveThreadPoolAutoConfig {

    private final Logger log = LoggerFactory.getLogger(AdaptiveThreadPoolAutoConfig.class);

    @Bean("adaptiveThreadPoolService")
    public AdaptiveThreadPoolService adaptiveThreadPoolService(ApplicationContext applicationContext,
                                                               Map<String, ThreadPoolExecutor> threadPoolExecutorMap) {
        String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");

        if(StringUtils.isBlank(applicationName)){
            log.warn("动态线程池，启动提示。SpringBoot应用未配置spring.application.name, 无法获取应用名称！");
        }

        return new AdaptiveThreadPoolService(applicationName, threadPoolExecutorMap);
    }

    @Bean("adaptiveThreadRedissionClient")
    public RedissonClient redissonClient(AdaptiveThreadPoolAutoProperties properties) {
        Config config = new Config();
        config.setCodec(JsonJacksonCodec.INSTANCE);

        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword())
                .setConnectionPoolSize(properties.getPoolSize())
                .setConnectionMinimumIdleSize(properties.getMinIdleSize())
                .setIdleConnectionTimeout(properties.getIdleTimeout())
                .setConnectTimeout(properties.getConnectTimeout())
                .setRetryAttempts(properties.getRetryAttempts())
                .setRetryInterval(properties.getRetryInterval())
                .setPingConnectionInterval(properties.getPingInterval())
                .setKeepAlive(properties.isKeepAlive());

        RedissonClient redissonClient = Redisson.create(config);

        log.info("动态线程池 Redis注册器链接初始化完成 {} {} {}", properties.getHost(), properties.getPoolSize()
                , !redissonClient.isShutdown());

        return redissonClient;
    }

}
