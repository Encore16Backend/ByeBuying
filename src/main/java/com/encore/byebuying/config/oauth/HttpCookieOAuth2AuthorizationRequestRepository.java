package com.encore.byebuying.config.oauth;

import com.encore.byebuying.utils.CookieUtils;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class HttpCookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int cookieExpireSeconds = 180;

    // cookie에 저장되어있던 authorizationRequest 들을 가져옴
    // authorizationUri, authorizationGrantType, responseType, clientid, redirectUri, scopes, additionalParameters
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    // 플랫폼으로 보내기 위한 Request를 `oauth2_auth_request` 라는 cookie에 저장
    // authorizationUri, authorizationGrantType, responseType, clientid, redirectUri, scopes, additionalParameters
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            return;
        }

        CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtils.serialize(authorizationRequest), cookieExpireSeconds);

        // http://localhost:8081/oauth2/authorization/naver?redirect_uri=http://localhost:3000/oauth2/redirect 요청을 받았을 때
        // http://localhost:3000/oauth2/redirect 를 가져옴. 존재하면 cookie에 넣어줌
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        log.info("redirect uri : {}", redirectUriAfterLogin);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    // remove를 재정의해서 cookie를 가져오고 remove는 successHandler나 failHandler에서 할 수 있도록 함
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    // 사용자 정보를 다 가지고 온 뒤 리다이렉트를 하면 기존에 남아있던 쿠키들을 제거해주기 위해 사용
    // OAuth2AuthorizationRequest와 클라이언트에서 파라미터로 요청한 redirect_uri가 된다.
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}
