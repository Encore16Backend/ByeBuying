package com.encore.byebuying.domain.order.controller;

import com.encore.byebuying.config.SecurityConfig;
import com.encore.byebuying.config.auth.PrincipalDetails;
import com.encore.byebuying.config.auth.PrincipalUserDetailsService;
import com.encore.byebuying.config.oauth.PrincipalOAuth2UserService;
import com.encore.byebuying.config.properties.AppProperties;
import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.code.RoleType;
import com.encore.byebuying.domain.order.service.OrderService;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.CreateUserDTO;
import com.encore.byebuying.filter.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.encore.byebuying.domain.code.ProviderType.LOCAL;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = SecurityConfig.class)
@ImportAutoConfiguration(classes = SecurityConfig.class)
@WebMvcTest(OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
class OrderControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private OrderService orderService;
    @MockBean
    private PrincipalUserDetailsService principalUserDetailsService;
    @MockBean
    private PrincipalOAuth2UserService oAuth2UserService;
    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private AppProperties appProperties;
    private MockMvc mockMvc;
    private PrincipalDetails loginUser;
    private final TestPrincipalDetailsService testPrincipalDetailsService = new TestPrincipalDetailsService();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        loginUser = (PrincipalDetails) testPrincipalDetailsService.loadUserByUsername(TestPrincipalDetailsService.USERNAME);
    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/api/orders/test").with(user(loginUser)))
                .andExpect(content().string("test"))
                .andDo(print());
    }

    public static class TestPrincipalDetailsService implements UserDetailsService {
        public static final String USERNAME = "test";
        private User getUser() {
            CreateUserDTO dto = new CreateUserDTO("test", "1111","test@naver.com");
            return User.initUser()
                .dto(dto)
                .provider(LOCAL)
                .build();
        }
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if (USERNAME.equals(username)) {
                return PrincipalDetails.create(getUser());
            }
            return null;
        }
    }
}