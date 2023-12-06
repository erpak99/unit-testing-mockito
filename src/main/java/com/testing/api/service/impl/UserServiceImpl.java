package com.testing.api.service.impl;

import com.testing.api.dto.UserDto;
import com.testing.api.model.User;
import com.testing.api.repository.UserRepository;
import com.testing.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setType(userDto.getType());

        User newUser = userRepository.save(user);

        UserDto userResponse = new UserDto();
        userResponse.setId(newUser.getId());
        userResponse.setName(newUser.getName());
        userResponse.setType(newUser.getType());
        return userResponse;
    }









    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setType(user.getType());
        return userDto;
    }

    private User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setType(userDto.getType());
        return user;
    }


}
