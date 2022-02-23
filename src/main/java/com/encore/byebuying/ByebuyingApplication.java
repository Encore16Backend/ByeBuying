package com.encore.byebuying;

import com.encore.byebuying.domain.*;
import com.encore.byebuying.service.ItemService;
import com.encore.byebuying.service.ReviewService;
import com.encore.byebuying.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class ByebuyingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ByebuyingApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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
