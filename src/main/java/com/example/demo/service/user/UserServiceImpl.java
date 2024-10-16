package com.example.demo.service.user;

import com.example.demo.dao.user.User;
import com.example.demo.dao.user.UserRepository;
import com.example.demo.dao.user.dto.CreateUserDTO;
import com.example.demo.dao.user.dto.UpdateUserDTO;
import com.example.demo.dao.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> listUsers(Pageable pageable) {
        return userRepository.findByActiveTrue(pageable)
                .map(this::toUserDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserById(Long id) {
        return userRepository.findById(id)
                .map(this::toUserDTO);
    }

    @Override
    public User createUser(CreateUserDTO request) {
        User user = new User();
        user.setName(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(request.role());
        user.setToken(request.token());
        user.setActive(true);

        return userRepository.save(user);
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserDTO request) {
        return userRepository.findById(id)
                .map(user -> {
                    Optional.ofNullable(request.username()).ifPresent(user::setName);
                    Optional.ofNullable(request.password()).ifPresent(user::setPassword);

                    User updatedUser = userRepository.save(user);
                    System.out.println(updatedUser);
                    return toUserDTO(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        user -> {
                            user.setActive(false);
                            userRepository.save(user);
                        },
                        () -> {
                            throw new RuntimeException("User not found with ID: " + id);
                        }
                );
    }

    private UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.isActive(),
                user.getToken()
        );
    }
}
