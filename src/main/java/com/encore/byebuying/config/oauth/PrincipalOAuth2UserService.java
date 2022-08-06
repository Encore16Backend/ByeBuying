package com.encore.byebuying.config.oauth;

import com.encore.byebuying.config.Exception.OAuth2AuthenticationProcessingException;
import com.encore.byebuying.config.auth.PrincipalDetails;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.code.ProviderType;
import com.encore.byebuying.domain.user.dto.CreateUserDTO;
import com.encore.byebuying.domain.user.repository.UserRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor @Slf4j
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        User saveUser = userRepository.findByUsername(oAuth2UserInfo.getUsername()).orElse(null);
        if (saveUser != null && !saveUser.getProvider().equals(providerType)) {
            throw new OAuth2AuthenticationProcessingException("Miss Match Provider Type. User: {}"+ saveUser);
        }

        saveUser = createUser(oAuth2UserInfo, providerType);
        return PrincipalDetails.create(saveUser, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo oAuth2UserInfo, ProviderType providerType) {
        CreateUserDTO dto = CreateUserDTO.builder()
            .username(oAuth2UserInfo.getUsername())
            .password("OAUTHLOGIN")
            .email(oAuth2UserInfo.getEmail())
            .defaultLocationIdx(0)
            .locations(new ArrayList<>()).build();
        return userRepository.save(
            User.initUser()
                .dto(dto)
                .provider(providerType).build());
    }
}
