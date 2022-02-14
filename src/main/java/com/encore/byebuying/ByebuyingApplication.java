package com.encore.byebuying;

import com.encore.byebuying.domain.Role;
import com.encore.byebuying.domain.User;
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
	CommandLineRunner run(UserService userService){
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
		};
	}

}
