package com.testing.api.service.impl;

import com.testing.api.dto.UserDto;
import com.testing.api.dto.UserResponse;
import com.testing.api.exceptions.UserNotFoundException;
import com.testing.api.model.User;
import com.testing.api.repository.UserRepository;
import com.testing.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public UserResponse getAllUser(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);
        List<User> listOfUser = users.getContent();
        List<UserDto> content = listOfUser.stream().map(u -> mapToDto(u)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());

        return userResponse;
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User could not be found"));
        return mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User could not be updated")
                );

        user.setName(userDto.getName());
        user.setType(userDto.getType());

        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    public void deleteUserId(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("Pokemon could not be delete")
                );
        userRepository.delete(user);
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
