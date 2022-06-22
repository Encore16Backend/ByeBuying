package com.encore.byebuying;

import com.encore.byebuying.config.properties.AppProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({
		AppProperties.class
})
public class ByebuyingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ByebuyingApplication.class, args);
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxInMemorySize(2000000000);
		return multipartResolver;
	}

	@Bean
	public WebClient webClient() {
		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 100000) // 연결 시간 초과 설정
				.responseTimeout(Duration.ofMillis(100000)) // 응답 시간 초과 설정
				.doOnConnected(conn ->
						conn.addHandlerLast(new ReadTimeoutHandler(100000, TimeUnit.MILLISECONDS))
							.addHandlerLast(new WriteTimeoutHandler(100000, TimeUnit.MILLISECONDS)));

		return WebClient.builder()
				.baseUrl("http://192.168.0.47:5000/")
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();
	}
}
