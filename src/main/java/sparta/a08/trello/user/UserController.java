package sparta.a08.trello.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.a08.trello.CommonResponseDTO;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDTO> signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        userService.signup(userRequestDTO);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new CommonResponseDTO("회원가입에 성공했습니다.", HttpStatus.CREATED.value()));
    }
}
