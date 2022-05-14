package com.encore.byebuying.config.oauth.impl;

import com.encore.byebuying.config.oauth.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("response"));
        log.info("attribute: {}", attributes);
    }

    @Override
    public String getUsername() {
        return "naver_" + attributes.get("id");
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
