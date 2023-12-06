package com.testing.api.service;

import com.testing.api.dto.UserDto;
import com.testing.api.dto.UserResponse;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserResponse getAllUser(int pageNo, int pageSize);
    UserDto getUserById(int id);


}
