package com.encore.byebuying.config.oauth.impl;

import com.encore.byebuying.config.oauth.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class FacebookOAuth2UserInfo extends OAuth2UserInfo {

    public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
        log.info("attribute: {}", attributes);
    }

    @Override
    public String getUsername() {
        return "facebook_" + attributes.get("id");
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
