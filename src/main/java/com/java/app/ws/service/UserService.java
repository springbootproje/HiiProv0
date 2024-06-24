
package com.java.app.ws.service;

import com.java.app.ws.Response.LoginResponse;
import com.java.app.ws.dto.CreateUserDto;
import com.java.app.ws.dto.LoginDTO;
import com.java.app.ws.dto.UpdateUserDto;
import com.java.app.ws.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(CreateUserDto createUserDto);
    void changePassword(String email, String currentPassword, String newPassword);

    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UpdateUserDto userDetails);

    void deleteUser(Long id);



}
