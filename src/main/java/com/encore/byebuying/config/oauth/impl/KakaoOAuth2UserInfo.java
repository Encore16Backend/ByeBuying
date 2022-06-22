package com.encore.byebuying.config.oauth.impl;

import com.encore.byebuying.config.oauth.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private final Map<String, Object> kakao_account;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        log.info("attribute: {}", attributes);
        log.info("kakao_account: {}", kakao_account);
    }

    @Override
    public String getUsername() {
        return "kakao_" + attributes.get("id");
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        return (String) kakao_account.get("email");
    }
}
