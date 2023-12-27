package sparta.a08.trello.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void signup(UserRequestDTO userRequestDTO) {
        String email = userRequestDTO.getEmail();
        String username = userRequestDTO.getUsername();
        String password = passwordEncoder.encode(userRequestDTO.getPassword());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일 주소입니다.");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 사용자명입니다.");
        }

        if (isPasswordValid(userRequestDTO)) {
            throw new IllegalArgumentException("비밀번호는 사용자명을 포함할 수 없습니다.");
        }

        if (!isPasswordConfirmed(userRequestDTO)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        User user = new User(email, username, password);
        userRepository.save(user);
    }


    public boolean isPasswordValid(UserRequestDTO userRequestDTO) {
        return userRequestDTO.getPassword().contains(userRequestDTO.getUsername());
    }

    public boolean isPasswordConfirmed(UserRequestDTO userRequestDTO) {
        return userRequestDTO.getPassword().equals(userRequestDTO.getConfirmPassword());
    }
}
