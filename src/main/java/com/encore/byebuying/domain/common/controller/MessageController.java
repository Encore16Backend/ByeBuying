package com.encore.byebuying.domain.common.controller;

import com.encore.byebuying.domain.common.Message;
import com.encore.byebuying.domain.common.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public String sendNotification(@RequestBody Message message){
        message.setContent("문의사항 답변이 완료되었습니다.");
        messageService.notify(message);
        return "SUCCES";
    }
}
