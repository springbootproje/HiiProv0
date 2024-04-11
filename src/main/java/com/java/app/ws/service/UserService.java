
package com.java.app.ws.service;

import com.java.app.ws.dto.CreateUserDto;
import com.java.app.ws.dto.UpdateUserDto;
import com.java.app.ws.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(CreateUserDto createUserDto);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UpdateUserDto userDetails);

    void deleteUser(Long id);
    boolean updatePassword(Long userId, String currentPassword, String newPassword);
}
