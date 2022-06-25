package com.encore.byebuying.domain.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Message {
    private String username;
    private String content;

    public Message(String username, String content) {
        this.username = username;
        this.content = content;
    }
}
