package com.encore.byebuying;

import com.encore.byebuying.domain.*;
import com.encore.byebuying.service.ItemService;
import com.encore.byebuying.service.ReviewService;
import com.encore.byebuying.service.UserService;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableJpaAuditing
public class ByebuyingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ByebuyingApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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
				.baseUrl("http://192.168.45.7:5000")
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();
	}

//	@Bean
//	CommandLineRunner run(UserService userService, ItemService itemService, ReviewService reviewService){
//		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_MANAGER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//
//			userService.saveUser(new User(null, "test0", "test0",
//					"TEST LOCATION 1", 1, "test0@test.com", new ArrayList<>()));
//			userService.saveUser(new User(null, "test1", "test1",
//					"TEST LOCATION 2", 2, "test1@test.com", new ArrayList<>()));
//			userService.saveUser(new User(null, "test2", "test2",
//					"TEST LOCATION 3", 3, "test2@test.com", new ArrayList<>()));
//			userService.saveUser(new User(null, "test3", "test3",
//					"TEST LOCATION 4", 4, "test3@test.com", new ArrayList<>()));
//
//			userService.addRoleToUser("test0", "ROLE_SUPER_ADMIN");
//			userService.addRoleToUser("test1", "ROLE_MANAGER");
//			userService.addRoleToUser("test2", "ROLE_USER");
//			userService.addRoleToUser("test3", "ROLE_USER");
//			userService.addRoleToUser("test3", "ROLE_MANAGER");
//			userService.addRoleToUser("test3", "ROLE_ADMIN");
//
//			itemService.saveCategory(new Category(null, "상의"));
//			itemService.saveCategory(new Category(null, "반팔"));
//			itemService.saveCategory(new Category(null, "긴팔"));
//			itemService.saveCategory(new Category(null, "하의"));
//			itemService.saveCategory(new Category(null, "반바지"));
//			itemService.saveCategory(new Category(null, "긴바지"));
//			itemService.saveCategory(new Category(null, "아우터"));
//			itemService.saveCategory(new Category(null, "코트"));
//			itemService.saveCategory(new Category(null, "패딩"));
//			itemService.saveCategory(new Category(null, "모자"));
//			itemService.saveCategory(new Category(null, "신발"));
//
//			itemService.saveItem(new Item(null, "상의1", new ArrayList<>(), new ArrayList<>(),
//					20000, 11, 0, 3.3, "상의1로 반팔 상품입니다.",0));
//			itemService.saveItem(new Item(null, "상의2", new ArrayList<>(), new ArrayList<>(),
//					25000, 131, 0, 2.1, "상의2로 긴팔 상품입니다.",0));
//			itemService.saveItem(new Item(null, "상의3", new ArrayList<>(), new ArrayList<>(),
//					15000, 56, 0, 3.6, "상의3로 긴팔 상품입니다.",0));
//			itemService.saveItem(new Item(null, "상의4", new ArrayList<>(), new ArrayList<>(),
//					10000, 72, 0, 4.1, "상의4로 긴팔 상품입니다.",0));
//			itemService.saveItem(new Item(null, "상의5", new ArrayList<>(), new ArrayList<>(),
//					55000, 26, 0, 5.0, "상의5로 긴팔 상품입니다.",0));
//			itemService.saveItem(new Item(null, "상의6", new ArrayList<>(), new ArrayList<>(),
//					35000, 32, 0, 2.5, "상의6로 긴팔 상품입니다.",0));
//			itemService.saveItem(new Item(null, "상의7", new ArrayList<>(), new ArrayList<>(),
//					45000, 9, 0, 3.2, "상의7로 긴팔 상품입니다.",0));
//
//			itemService.saveItem(new Item(null, "하의1", new ArrayList<>(), new ArrayList<>(),
//					30000, 0, 0, 4, "하의1로 반바지 상품입니다.",0));
//			itemService.saveItem(new Item(null, "하의2", new ArrayList<>(), new ArrayList<>(),
//					35000, 0, 0, 3, "하의2로 긴바지 상품입니다.",0));
//
//			itemService.saveImage(new Image(null, "path/상의1"));
//			itemService.saveImage(new Image(null, "path/상의2"));
//			itemService.saveImage(new Image(null, "path/상의3"));
//			itemService.saveImage(new Image(null, "path/상의4"));
//			itemService.saveImage(new Image(null, "path/상의5"));
//			itemService.saveImage(new Image(null, "path/상의6"));
//			itemService.saveImage(new Image(null, "path/상의7"));
//			itemService.saveImage(new Image(null, "path/하의1"));
//			itemService.saveImage(new Image(null, "path/하의2"));
//
//			itemService.addCategoryToItem("상의1", "상의");
//			itemService.addCategoryToItem("상의1", "반팔");
//			itemService.addCategoryToItem("상의2", "상의");
//			itemService.addCategoryToItem("상의2", "긴팔");
//			itemService.addCategoryToItem("상의3", "상의");
//			itemService.addCategoryToItem("상의3", "긴팔");
//			itemService.addCategoryToItem("상의4", "상의");
//			itemService.addCategoryToItem("상의4", "반팔");
//			itemService.addCategoryToItem("상의5", "상의");
//			itemService.addCategoryToItem("상의5", "긴팔");
//			itemService.addCategoryToItem("상의6", "상의");
//			itemService.addCategoryToItem("상의6", "반팔");
//			itemService.addCategoryToItem("상의7", "상의");
//			itemService.addCategoryToItem("상의7", "반팔");
//			itemService.addCategoryToItem("하의1", "하의");
//			itemService.addCategoryToItem("하의2", "하의");
//			itemService.addCategoryToItem("하의1", "반바지");
//			itemService.addCategoryToItem("하의2", "긴바지");
//
//			itemService.addImageToItem("상의1", "path/상의1");
//			itemService.addImageToItem("상의2", "path/상의2");
//			itemService.addImageToItem("상의3", "path/상의3");
//			itemService.addImageToItem("상의4", "path/상의4");
//			itemService.addImageToItem("상의5", "path/상의5");
//			itemService.addImageToItem("상의6", "path/상의6");
//			itemService.addImageToItem("상의7", "path/상의7");
//			itemService.addImageToItem("하의1", "path/하의1");
//			itemService.addImageToItem("하의2", "path/하의2");
//
//			reviewService.saveReview(new Review(null,"test0", "상의1", 2, "content0", new Date(122, 10, 15)));
//			reviewService.saveReview(new Review(null,"test0", "상의2", 3, "content1", new Date(121, 1, 3)));
//			reviewService.saveReview(new Review(null,"test0", "상의3", 4, "content2", new Date(125, 11, 5)));
//			reviewService.saveReview(new Review(null,"test0", "하의1", 5, "content0", new Date(120, 12, 25)));
//			reviewService.saveReview(new Review(null,"test0", "하의2", 1.5, "content1", new Date(118, 2, 8)));
//			reviewService.saveReview(new Review(null,"test0", "하의1", 2, "content2", new Date(121, 11, 2)));
//			reviewService.saveReview(new Review(null,"test0", "하의1", 3.5, "content2", new Date(121, 11, 3)));
//			reviewService.saveReview(new Review(null,"test0", "하의1", 2.5, "content2", new Date(121, 11, 4)));
//			reviewService.saveReview(new Review(null,"test0", "하의1", 2.5, "content2", new Date(121, 11, 5)));
//			reviewService.saveReview(new Review(null,"test0", "하의1", 2.5, "content2", new Date(121, 11, 16)));
//			reviewService.saveReview(new Review(null,"test1", "하의1", 5, "content2", new Date(121, 11, 22)));
//			reviewService.saveReview(new Review(null,"test2", "하의1", 4.5, "content2", new Date(121, 11, 12)));
//			reviewService.saveReview(new Review(null,"test3", "하의1", 3.5, "content2", new Date(121, 11, 7)));
//		};
//	}

}
