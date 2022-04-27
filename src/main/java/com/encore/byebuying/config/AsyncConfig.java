package com.encore.byebuying.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // 비동기 기능 활성화
public class AsyncConfig extends AsyncConfigurerSupport {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); // 비동기로 호출하는 Thread에 대한 설정
        executor.setCorePoolSize(2); // 기존적으로 실행을 대기하고 있는 Thread의 개수
        executor.setMaxPoolSize(10); // 동시 동작하는 최대 Thread 개수
        // MaxPoolSize를 초과하는 요청이 Thread 생성 요청 시 해당 내용을 Queue에 저장하게 되고,
        // 사용할 수 있는 Thread 여유 자리가 발생하면 하나씩 꺼내 동작
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("ByeBuying-async-"); // Spring이 생성하는 쓰레드의 접두사를 지정
        executor.initialize();
        return executor;
    }
}