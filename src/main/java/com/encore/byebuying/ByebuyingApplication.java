package com.encore.byebuying;

import com.encore.byebuying.domain.*;
import com.encore.byebuying.service.ItemService;
import com.encore.byebuying.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ByebuyingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ByebuyingApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, ItemService itemService){
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "test0", "test0",
					"TEST LOCATION 1", 1, "test0@test.com", new ArrayList<>()));
			userService.saveUser(new User(null, "test1", "test1",
					"TEST LOCATION 2", 2, "test1@test.com", new ArrayList<>()));
			userService.saveUser(new User(null, "test2", "test2",
					"TEST LOCATION 3", 3, "test2@test.com", new ArrayList<>()));
			userService.saveUser(new User(null, "test3", "test3",
					"TEST LOCATION 4", 4, "test3@test.com", new ArrayList<>()));

			userService.addRoleToUser("test0", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("test1", "ROLE_MANAGER");
			userService.addRoleToUser("test2", "ROLE_USER");
			userService.addRoleToUser("test3", "ROLE_USER");
			userService.addRoleToUser("test3", "ROLE_MANAGER");
			userService.addRoleToUser("test3", "ROLE_ADMIN");

			itemService.saveCategory(new Category(null, "상의"));
			itemService.saveCategory(new Category(null, "반팔"));
			itemService.saveCategory(new Category(null, "긴팔"));
			itemService.saveCategory(new Category(null, "하의"));
			itemService.saveCategory(new Category(null, "반바지"));
			itemService.saveCategory(new Category(null, "긴바지"));
			itemService.saveCategory(new Category(null, "아우터"));
			itemService.saveCategory(new Category(null, "코트"));
			itemService.saveCategory(new Category(null, "패딩"));
			itemService.saveCategory(new Category(null, "모자"));
			itemService.saveCategory(new Category(null, "신발"));

			itemService.saveItem(new Item(null, "상의1", new ArrayList<>(), new ArrayList<>(),
					20000, 0, 0, 0, "상의1로 반팔 상품입니다."));
			itemService.saveItem(new Item(null, "상의2", new ArrayList<>(), new ArrayList<>(),
					15000, 0, 0, 0, "상의2로 긴팔 상품입니다."));
			itemService.saveItem(new Item(null, "하의1", new ArrayList<>(), new ArrayList<>(),
					30000, 0, 0, 0, "하의1로 반바지 상품입니다."));
			itemService.saveItem(new Item(null, "하의2", new ArrayList<>(), new ArrayList<>(),
					35000, 0, 0, 0, "하의2로 긴바지 상품입니다."));
			
			itemService.saveImage(new Image(null, "path/상의1"));
			itemService.saveImage(new Image(null, "path/상의2"));
			itemService.saveImage(new Image(null, "path/하의1"));
			itemService.saveImage(new Image(null, "path/하의2"));
			
			itemService.addCategoryToItem("상의1", "상의");
			itemService.addCategoryToItem("상의1", "반팔");
			itemService.addCategoryToItem("상의2", "상의");
			itemService.addCategoryToItem("상의2", "긴팔");
			itemService.addCategoryToItem("하의1", "하의");
			itemService.addCategoryToItem("하의2", "하의");
			itemService.addCategoryToItem("하의1", "반바지");
			itemService.addCategoryToItem("하의2", "긴바지");
			
			itemService.addImageToItem("상의1", "path/상의1");
			itemService.addImageToItem("상의2", "path/상의2");
			itemService.addImageToItem("하의1", "path/하의1");
			itemService.addImageToItem("하의2", "path/하의2");
		};
	}

}
