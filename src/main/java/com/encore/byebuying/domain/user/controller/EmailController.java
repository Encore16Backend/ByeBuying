package com.encore.byebuying.domain.user.controller;

import com.encore.byebuying.domain.user.repository.UserRepository;
import com.encore.byebuying.domain.user.service.UserService;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class EmailController {
	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	
	@GetMapping("/checkMail")
	public String SendMail(@RequestParam(value = "email") String email) throws Exception {
		log.info("email : {}",email);
		boolean flag = userRepository.existsByEmail(email);
		if (flag) {
			return "EXIST";
		}
		try {
			Random random = new Random();
			String key = "";
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email); // 스크립트에서 보낸 메일을 받을 사용자 이메일 주소
			// 입력 키를 위한 코드
			for (int i = 0; i < 3; i++) {
				int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
				key += (char) index;
			}
			int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
			key += numIndex;
			message.setSubject("인증번호 입력을 위한 메일 전송");
			message.setText("인증 번호 : " + key);
			javaMailSender.send(message);
			return key;
			
		} catch (Exception e) {
			
			return "FAIL";
		}
	}
}
