package com.adaptive.thread.pool.sdk.domain.model;

import com.adaptive.thread.pool.sdk.domain.IAdaptiveThreadPoolService;
import com.adaptive.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Haipeng Wang
 * @description 线程池服务实现
 * @date 2024/12/5/16:34
 */
@RequiredArgsConstructor
public class AdaptiveThreadPoolService implements IAdaptiveThreadPoolService {

    private final Logger log = LoggerFactory.getLogger(AdaptiveThreadPoolService.class);

    private final String applicationName;
    private final Map<String, ThreadPoolExecutor> threadPoolExecutorMap;

    @Override
    public List<ThreadPoolConfigEntity> queryThreadPoolList() {
        Set<String> threadPoolBeanNames = threadPoolExecutorMap.keySet();
        ArrayList<ThreadPoolConfigEntity> threadPoolVOS = new ArrayList<>(threadPoolBeanNames.size());
        for(String name: threadPoolBeanNames){
            ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(name);
            
            ThreadPoolConfigEntity threadPoolConfigVO = new ThreadPoolConfigEntity(applicationName, name);

            //线程池配置数据
            threadPoolConfigVO.setCorePoolSize(threadPoolExecutor.getCorePoolSize()); //核心线程数
            threadPoolConfigVO.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize()); //最大线程数
            threadPoolConfigVO.setActiveCount(threadPoolExecutor.getActiveCount()); //当前活跃线程数
            threadPoolConfigVO.setPoolSize(threadPoolExecutor.getPoolSize()); //当前池中线程数
            threadPoolConfigVO.setQueueType(threadPoolExecutor.getQueue().getClass().getSimpleName()); //队列类型
            threadPoolConfigVO.setQueueSize(threadPoolExecutor.getQueue().size()); //当前队列任务数
            threadPoolConfigVO.setRemainingCapacity(threadPoolExecutor.getQueue().remainingCapacity()); //队列剩余任务数
            threadPoolVOS.add(threadPoolConfigVO);
        }
        return threadPoolVOS;
    }

    @Override
    public ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName) {
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolName);
        if(threadPoolExecutor == null) return new ThreadPoolConfigEntity(applicationName, threadPoolName);

        ThreadPoolConfigEntity threadPoolConfigVO = new ThreadPoolConfigEntity(applicationName, threadPoolName);

        //线程池配置数据
        threadPoolConfigVO.setCorePoolSize(threadPoolExecutor.getCorePoolSize()); //核心线程数
        threadPoolConfigVO.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize()); //最大线程数
        threadPoolConfigVO.setActiveCount(threadPoolExecutor.getActiveCount()); //当前活跃线程数
        threadPoolConfigVO.setPoolSize(threadPoolExecutor.getPoolSize()); //当前池中线程数
        threadPoolConfigVO.setQueueType(threadPoolExecutor.getQueue().getClass().getSimpleName()); //队列类型
        threadPoolConfigVO.setQueueSize(threadPoolExecutor.getQueue().size()); //当前队列任务数
        threadPoolConfigVO.setRemainingCapacity(threadPoolExecutor.getQueue().remainingCapacity()); //队列剩余任务数

        if (log.isDebugEnabled()) {
            log.info("自适应线程池 查询配置 应用名:{} 线程名:{} 线程池配置:{}", applicationName, threadPoolName,
                    JSON.toJSONString(threadPoolConfigVO));
        }

        return threadPoolConfigVO;
    }

    @Override
    public void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity) {
        return ;
    }
}
