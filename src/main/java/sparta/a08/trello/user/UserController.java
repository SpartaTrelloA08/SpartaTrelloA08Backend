package sparta.a08.trello.user;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.a08.trello.CommonResponseDTO;
import sparta.a08.trello.jwt.JwtUtil;

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
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userRequestDTO.getUsername()));

        return ResponseEntity.ok().body(new CommonResponseDTO("로그인 성공.", HttpStatus.OK.value()));
    }
}
