package sparta.a08.trello.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.a08.trello.common.CommonResponseDTO;
import sparta.a08.trello.common.jwt.JwtUtil;
import sparta.a08.trello.common.security.UserDetailsImpl;
import sparta.a08.trello.user.dto.UpdateUserRequestDTO;
import sparta.a08.trello.user.dto.UserRequestDTO;
import sparta.a08.trello.user.entity.User;
import sparta.a08.trello.user.service.UserService;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO> signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        userService.signup(userRequestDTO);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new CommonResponseDTO("회원가입에 성공했습니다.", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO, HttpServletResponse response){
        try {
            userService.login(userRequestDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDTO.getEmail()));

        return ResponseEntity.ok().body(new CommonResponseDTO("로그인 성공.", HttpStatus.OK.value()));
    }

    @GetMapping("/logout")
    public ResponseEntity<CommonResponseDTO> logoutUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        User findUser = userDetails.getUser();
        userService.logoutUser(findUser.getId());

        return ResponseEntity.ok().body(new CommonResponseDTO("로그아웃 성공", HttpStatus.OK.value()));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<CommonResponseDTO> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody UpdateUserRequestDTO updatedUserRequestDTO) {

        User existingUser = userDetails.getUser();
        userService.updateUserProfile(existingUser.getId(), updatedUserRequestDTO);

        return ResponseEntity.ok().body(new CommonResponseDTO("프로필이 수정되었습니다.", HttpStatus.OK.value()));
    }

}
