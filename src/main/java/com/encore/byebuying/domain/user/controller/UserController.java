package com.encore.byebuying.domain.user.controller;

import com.encore.byebuying.config.auth.LoginUser;
import com.encore.byebuying.domain.common.paging.PagingRequest;
import com.encore.byebuying.domain.platfrom2server.service.WebClientService;
import com.encore.byebuying.domain.user.Location;
import com.encore.byebuying.domain.user.User;
import com.encore.byebuying.domain.user.dto.CreateUserDTO;
import com.encore.byebuying.domain.user.repository.LocationRepository;
import com.encore.byebuying.domain.user.service.UserService;
import com.encore.byebuying.domain.user.vo.UserVO;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final LocationRepository locationRepository;
    private final WebClientService webClientService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        String username = userService.saveUser(createUserDTO);
//        webClientService.newUser(newUser.getUsername());
        return new ResponseEntity<>(username, HttpStatus.OK);
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
//    @PostMapping("/user/getUser") // 회원 확인용 - 수정 or 삭제
//    public ResponseEntity<User> getUser(@RequestBody User userinfo) {
//        User user = userService.getUser(userinfo.getUsername());
//        System.out.println(userinfo.getUsername()+" "+userinfo.getPassword());
//        if (passwordEncoder.matches(userinfo.getPassword(), user.getPassword())){
//            return ResponseEntity.ok().body(user);
//        }
//        return ResponseEntity.badRequest().body(null);
//    }

    @GetMapping("/{id}/locations") // 회원 배송지
    public ResponseEntity<?> getUserLocation(@AuthenticationPrincipal LoginUser loginUser,
        @PathVariable(value = "id") long userId) {
        Page<Location> locations = userService.getUserLocation(loginUser.getUserId(), userId);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

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

    @GetMapping("/token/refresh")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // todo: 수정 필요
//    @PutMapping // 토큰 필요
//    public ResponseEntity<?> updateUser(@RequestBody CreateUserDTO userForm) {
//        User user = userService.getUser(userForm.getUsername());
//        if (user == null){
//            return new ResponseEntity<>("FAIL", HttpStatus.OK);
//        }
//    //        if (userForm.getPassword() != null && !userForm.getPassword().equals("")) // 비밀번호도 수정될 때
//    //            user.setPassword(userForm.getPassword());
//    //        user.setEmail(userForm.getEmail());
//    //        user.setDefaultLocationIdx(userForm.getDefaultLocationIdx());
//
//        // 현재주소 갈아엎고 새주소 넣기
//        List<Location> list = (List<Location>) user.getLocations();
//        Long[] idList = new Long[list.size()];
//        for(int i=0;i<list.size();i++)
//          idList[i]=list.get(i).getId();
//
//        user.getLocations().clear();
//        for(Long id : idList) {
//          locationRepository.deleteById(id);
//        }
//
//        user.getLocations().addAll(userForm.getLocations());
//    //        userService.saveUser(user);
//        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//    }



//    @PostMapping("/user/update/admin") // 관리자가 유저 정보 수정
//    public ResponseEntity<?> adminUpdateUser(@RequestBody UserForm userForm){
////        {
////            "username" : "qwer1234",
////                "password" : "12341234",
////                "email" : "JHJKINGAKKKKKK@ByeBuying.com",
////                "locations" : [{"location":"외곽"},{"location":"외곽2"}]
////        }
//        System.out.println("userForm = "+userForm);
//        User user = userService.getUser(userForm.getUsername());
//        System.out.println("user = " + user);
//        if (user == null){
//            return new ResponseEntity<>("FAIL", HttpStatus.OK);
//        }
//        if (userForm.getPassword() != null && !userForm.getPassword().equals("")) // 비밀번호도 수정될 때
//            user.setPassword(userForm.getPassword());
//        user.setEmail(userForm.getEmail());
//        user.setDefaultLocationIdx(userForm.getDefaultLocationIdx());
//
//        // 현재주소 갈아엎고 새주소 넣기
//        List<Location> list = (List<Location>) user.getLocations();
//        System.out.println("list = " + list);
//        Long[] idList = new Long[list.size()];
//        System.out.println("idListBefore = " + idList);
//        // 위치 인덱스 번호를 뺴옴
//        for(int i=0;i<list.size();i++){
//            idList[i]=list.get(i).getId();
//        }
//        user.getLocations().clear();
//        if (user.getLocations() != null){
//            for(Long id : idList) {
//                locationRepo.deleteById(id);
//            }
//        }
//        System.out.println("userForm.getLocations() = " + userForm.getLocations());
//        user.getLocations().addAll(userForm.getLocations());
//        userService.saveUser(user);
//        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
//    }
}
