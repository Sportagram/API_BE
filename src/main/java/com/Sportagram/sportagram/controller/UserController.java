//package com.Sportagram.sportagram.controller;
//
//
//import com.Sportagram.sportagram.dto.CustomOAuth2User;
//import com.Sportagram.sportagram.dto.UserDto;
//import com.Sportagram.sportagram.entity.Users;
//import com.Sportagram.sportagram.repository.UserRepository;
//import com.Sportagram.sportagram.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserRepository userRepository;
//    private final UserService userService;
//
//
//    // 사용자 정보 조회 API
//    // 설정 페이지에 사용하는 api 입니다.
//
////    @PostMapping("/user/settings")
////    public ResponseEntity<UserDto> setUserSettings(@RequestBody UserDto userJoinResDto) {
////
////        userService.save(userJoinResDto);
////        return ResponseEntity.ok(userJoinResDto);
////    }
//@PostMapping("/user/settings")
//public ResponseEntity<?> setUserSettings(@RequestBody UserDto userDto) {
//    try {
//        Long userId = userService.save(userDto); // UserService의 save 호출
//        return ResponseEntity.ok("사용자 저장 완료. ID: " + userId);
//    } catch (IllegalStateException e) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // 닉네임 중복 등 예외 처리
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 저장 중 오류가 발생했습니다.");
//    }
//}
//
//
//    @GetMapping("/user/settings")
//    public ResponseEntity<Map<String, Object>> getUserSettings(Authentication authentication) {
//
//        if (authentication != null) {
//            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//            Users user = userRepository.findByUserName(oAuth2User.getUsername());
//            Map<String, Object> userInfo = new HashMap<>();
//            //userInfo.put("nickname", user.getNickName());
//            userInfo.put("email", user.getEmail());
//            //userInfo.put("myteam", user.getMyTeam());
//
//            return ResponseEntity.ok(userInfo);
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//
//    }
//
//    /*
//    @GetMapping("/user/settings/{userId}")
//    public ResponseEntity<Map<String, Object>> getUserSettings(Authentication authentication) {
//
//
//        // postman에서 정보 조회 되는지 확인하기 위한 test code
//        // 실제 실행 시엔 주석처리하고 바로 위 주석을 사용할 것
//        try {
//            // 테스트를 위해 첫 번째 사용자 정보 반환
//            Users firstUser = userRepository.findAll().get(0);
//
//            Map<String, Object> userInfo = new HashMap<>();
//            userInfo.put("nickname", firstUser.getNickName());
//            userInfo.put("email", firstUser.getEmail());
//            userInfo.put("myteam", firstUser.getMyTeam());
//
//            return ResponseEntity.ok(userInfo);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//     */
//
//    // 사용자 정보 수정 API
//    // 수정 완료 버튼 클릭 시 정보 수정 되도록 하면 될 듯 합니다.
//
//
//    @PutMapping("/user/settings")
//    public ResponseEntity<?> updateUserSettings(
//            @RequestBody UserSettingsRequest request,
//            Authentication authentication) {
//        if (authentication != null) {
//            try {
//                CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//                Users user = userRepository.findByUserName(oAuth2User.getUsername());
//
//                // 닉네임과 응원팀 정보 업데이트
//                user.setNickName(request.getNickname());
//                user.setMyTeam(request.getMyteam());
//
//                userRepository.save(user);
//                return ResponseEntity.ok().build();
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//            }
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
//
//    // 다음 코드는 사용자 정보 수정 test를 위함
//    // 실제 실행 시 주석 처리 및 위쪽의 주석을 사용 바람
//    /*
//    @PutMapping("/user/settings/{userId}")
//    public user updateUser(@PathVariable String userId, @RequestBody Map<String, String> updates) {
//        Users user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
//
//        if (updates.containsKey("nickname")) {
//            user.setNickname(updates.get("nickname"));
//        }
//        if (updates.containsKey("myteam")) {
//            user.setMyteam(updates.get("myteam"));
//        }
//
//        return userRepository.save(user);
//    }
//
//     */
//}
//
//// 사용자 설정 수정 요청을 위한 DTO
//class UserSettingsRequest {
//    private String nickname;
//    private String myteam;
//
//    // getters and setters
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getMyteam() {
//        return myteam;
//    }
//
//    public void setMyteam(String myteam) {
//        this.myteam = myteam;
//    }
//}

// Authentication 대신 UserDto를 사용하여 유저를 생성할 수 있도록 수정
package com.Sportagram.sportagram.controller;

import com.Sportagram.sportagram.dto.UserDto;
import com.Sportagram.sportagram.entity.Users;
import com.Sportagram.sportagram.repository.UserRepository;
import com.Sportagram.sportagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * 사용자 신규 등록 API
     */
    @PostMapping("/user/settings")
    public ResponseEntity<?> setUserSettings(@RequestBody UserDto userDto) {
        try {
            Long userId = userService.save(userDto); // UserService의 save 호출
            return ResponseEntity.ok(Map.of("message", "사용자 저장 완료", "userId", userId));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "사용자 저장 중 오류가 발생했습니다."));
        }
    }

    /**
     * 사용자 정보 조회 API
     */
    @GetMapping("/user/settings")
    public ResponseEntity<?> getUserSettings(@RequestBody UserDto userDto) {
        try {
            Users user = userRepository.findByEmail(userDto.email())
                    .orElseThrow(() -> new IllegalStateException("해당 이메일을 가진 사용자를 찾을 수 없습니다."));

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userName", user.getUserName());
            userInfo.put("nickName", user.getNickName());
            userInfo.put("email", user.getEmail());
            userInfo.put("myTeam", user.getMyTeam());

            return ResponseEntity.ok(userInfo);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "사용자 정보 조회 중 오류 발생"));
        }
    }

    /**
     * 사용자 정보 수정 API
     */
    @PutMapping("/user/settings")
    public ResponseEntity<?> updateUserSettings(@RequestBody UserDto userDto) {
        try {
            Users user = userRepository.findByEmail(userDto.email())
                    .orElseThrow(() -> new IllegalStateException("해당 이메일을 가진 사용자를 찾을 수 없습니다."));

            // 사용자 정보 업데이트
            user.setNickName(userDto.nickName());
            user.setMyTeam(userDto.myTeam());
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "사용자 정보가 성공적으로 수정되었습니다."));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "사용자 정보 수정 중 오류 발생"));
        }
    }
}
