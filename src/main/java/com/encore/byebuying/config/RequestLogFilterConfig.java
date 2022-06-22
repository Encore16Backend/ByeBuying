package com.encore.byebuying.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLogFilterConfig {

  @Bean
  public CommonsRequestLoggingFilter logFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeHeaders(true);
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(10000);
    filter.setIncludeClientInfo(true);
    filter.setBeforeMessagePrefix("Before : ");
    filter.setBeforeMessageSuffix("");
    filter.setAfterMessagePrefix("After : ");
    filter.setAfterMessageSuffix("");
    return filter;
  }
}
