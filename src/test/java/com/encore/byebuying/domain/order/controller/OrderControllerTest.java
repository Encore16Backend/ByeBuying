package com.encore.byebuying.domain.order.controller;

import com.encore.byebuying.config.SecurityConfig;
import com.encore.byebuying.config.auth.PrincipalUserDetailsService;
import com.encore.byebuying.config.oauth.PrincipalOAuth2UserService;
import com.encore.byebuying.config.properties.AppProperties;
import com.encore.byebuying.filter.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SecurityConfig.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PrincipalUserDetailsService principalUserDetailsService;
    @MockBean
    private PrincipalOAuth2UserService oAuth2UserService;
    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private AppProperties appProperties;
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/api/orders/test").with(SecurityMockMvcRequestPostProcessors.user("test")));
    }

}