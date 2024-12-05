package com.adaptive.thread.pool.sdk.config;

import com.adaptive.thread.pool.sdk.domain.model.AdaptiveThreadPoolService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
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

}
