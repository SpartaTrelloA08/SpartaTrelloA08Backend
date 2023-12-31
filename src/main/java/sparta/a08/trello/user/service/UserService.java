package sparta.a08.trello.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.a08.trello.common.exception.CustomErrorCode;
import sparta.a08.trello.common.exception.CustomException;
import sparta.a08.trello.user.dto.UserSearchResponseDTO;
import sparta.a08.trello.user.repository.UserQueryRepositoryImpl;
import sparta.a08.trello.user.dto.UpdateUserRequestDTO;
import sparta.a08.trello.user.repository.UserRepository;
import sparta.a08.trello.user.dto.UserRequestDTO;
import sparta.a08.trello.user.entity.User;
import sparta.a08.trello.user.repository.UserSearchCond;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserQueryRepositoryImpl userQueryRepository;

    public void signup(UserRequestDTO userRequestDTO) {
        String email = userRequestDTO.getEmail();
        String username = userRequestDTO.getUsername();
        String password = passwordEncoder.encode(userRequestDTO.getPassword());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(CustomErrorCode.ALREADY_EXIST_EMAIL_EXCEPTION, 409);
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new CustomException(CustomErrorCode.ALREADY_EXIST_USER_NAME_EXCEPTION, 409);
        }

        if (isPasswordValid(userRequestDTO)) {
            throw new CustomException(CustomErrorCode.PWD_NO_USERNAME_INCLUSION, 409);
        }

        if (!isPasswordConfirmed(userRequestDTO)) {
            throw new CustomException(CustomErrorCode.PWD_MISMATCH_EXCEPTION, 409);
        }

        User user = new User(email, username, password);
        userRepository.save(user);
    }

    public void login(UserRequestDTO userRequestDTO) {
        String email = userRequestDTO.getEmail();
        String password = userRequestDTO.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER_EXCEPTION, 401));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(CustomErrorCode.PWD_MISMATCH_EXCEPTION, 409);
        }
    }

    @Transactional
    public void logoutUser(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow((() -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER_EXCEPTION, 401)));
        findUser.logout();
    }

    public void updateUserProfile(Long userId, UpdateUserRequestDTO updatedUserRequestDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND_MEMBER_EXCEPTION, 404));

        if (updatedUserRequestDTO.getUsername() != null) {
            existingUser.setUsername(updatedUserRequestDTO.getUsername());
        }

        userRepository.save(existingUser);
    }

    public boolean isPasswordValid(UserRequestDTO userRequestDTO) {
        return userRequestDTO.getPassword().contains(userRequestDTO.getUsername());
    }

    public boolean isPasswordConfirmed(UserRequestDTO userRequestDTO) {
        return userRequestDTO.getPassword().equals(userRequestDTO.getConfirmPassword());
    }

    public List<UserSearchResponseDTO> searchUser(String keyword) {
        return userQueryRepository.searchUser(new UserSearchCond(keyword))
                .stream().map(UserSearchResponseDTO::new).toList();
    }
}
