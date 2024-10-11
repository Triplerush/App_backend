package com.example.demo.service.user;

import com.example.demo.dao.user.User;
import com.example.demo.dao.user.UserRepository;
import com.example.demo.dao.user.dto.CreateUserDTO;
import com.example.demo.dao.user.dto.ListUserDTO;
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
    public Page<ListUserDTO> listUsers(Pageable pageable) {
        return userRepository.findByActiveTrue(pageable)
                .map(user -> new ListUserDTO(
                        user.getIdUser(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        user.getToken()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getIdUser(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        user.isActive(),
                        user.getToken()
                ));
    }

    @Override
    public User createUser(CreateUserDTO request) {
        User user = new User();
        user.setName(request.name());
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
                    user.setName(request.username());
                    if (request.password() != null) {
                        user.setPassword(request.password());
                    }

                    User updatedUser = userRepository.save(user);

                    return new UserDTO(
                            updatedUser.getIdUser(),
                            updatedUser.getName(),
                            updatedUser.getEmail(),
                            updatedUser.getRole(),
                            updatedUser.isActive(),
                            updatedUser.getToken()
                    );
                }).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
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
}