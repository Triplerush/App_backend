package com.example.demo.service.user;

import com.example.demo.dao.user.User;
import com.example.demo.dao.user.dto.CreateUserDTO;
import com.example.demo.dao.user.dto.ListUserDTO;
import com.example.demo.dao.user.dto.UpdateUserDTO;
import com.example.demo.dao.user.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    Page<ListUserDTO> listUsers(Pageable pageable);
    Optional<UserDTO> findUserById(Long id);
    User createUser(CreateUserDTO request);
    UserDTO updateUser(Long id, UpdateUserDTO request);
    void deleteUser(Long id);

}