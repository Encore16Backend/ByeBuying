package com.encore.byebuying.domain.user.controller;

import com.encore.byebuying.config.auth.LoginUser;
import com.encore.byebuying.domain.common.paging.PagingRequest;
import com.encore.byebuying.domain.platfrom2server.service.WebClientService;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.UpdateLocationDTO;
import com.encore.byebuying.domain.user.dto.UpdateUserDTO;
import com.encore.byebuying.domain.user.dto.GetLocationDTO;
import com.encore.byebuying.domain.user.service.UserService;
import com.encore.byebuying.domain.user.vo.LocationVO;
import com.encore.byebuying.domain.user.vo.UserVO;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final WebClientService webClientService;

    /**
     * User
     */
    @PostMapping
    public ResponseEntity<?> createUser(@AuthenticationPrincipal LoginUser loginUser,
        @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        UserVO vo = userService.saveUser(loginUser.getUserId(), updateUserDTO);
//        webClientService.newUser(newUser.getUsername());
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal LoginUser loginUser,
        @PathVariable(value = "id") long userId) {
        UserVO user = userService.getUser(loginUser.getUserId(), userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/sy/user") // 관리자 유저들 확인 todo: 관리자 UserDTO 나중에 수정, 유저 정보에 들어갈 것들 생각 필요함
    public ResponseEntity<Page<User>> getUsers(
            @Valid PagingRequest pagingRequest) {
        Page<User> users = userService.getUsers(pagingRequest.getPageRequest());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // todo: 수정 or 삭제 시 비밀번호 확인할 것인지 확인 필요?

    @GetMapping("/check-duplicated-username") // 아이디 중복 검사 확인
    public ResponseEntity<?> checkDuplicatedUsername(
            @RequestParam(value = "username") String username) {
        boolean isDuplicated = userService.checkDuplicatedUsername(username);
        return new ResponseEntity<>(isDuplicated, HttpStatus.OK);
    }

    @DeleteMapping ("/{id}")// 토큰 필요, 삭제 전 /api/user/getUser 에서 토큰 및 비밀번호 확인
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal LoginUser loginUser,
        @PathVariable(value = "id") long userId) {
        userService.deleteUser(loginUser.getUserId(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Location
     */

    @GetMapping("/{user-id}/locations") // 회원 배송지
    public ResponseEntity<?> getUserLocationList(@AuthenticationPrincipal LoginUser loginUser,
        @PathVariable(value = "user-id") long userId, @Valid PagingRequest pagingRequest) {
        Page<LocationVO> result = userService
            .getUserLocationList(loginUser.getUserId(), userId, pagingRequest.getPageRequest());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{user-id}/locations/find-one")
    public ResponseEntity<?> getUserLocation(@AuthenticationPrincipal LoginUser loginUser,
        @PathVariable(value = "user-id") long userId, GetLocationDTO dto) {
        LocationVO vo = userService.getUserLocation(loginUser.getUserId(), userId, dto);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    @PostMapping("/{user-id}/locations")
    public ResponseEntity<?> updateUserLocation(@AuthenticationPrincipal LoginUser loginUser,
        @PathVariable(value = "user-id") long userId, UpdateLocationDTO dto) {
        LocationVO vo = userService.updateUserLocation(loginUser.getUserId(), userId, dto);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    @DeleteMapping("/{user-id}/locations/{location-id}")
    public ResponseEntity<Void> deleteUserLocation(@AuthenticationPrincipal LoginUser loginUser,
        @PathVariable(value = "user-id") long userId, @PathVariable(value = "location-id") long locationId) {
        userService.deleteUserLocation(loginUser.getUserId(), userId, locationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Token
     */
    @GetMapping("/token/refresh")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
