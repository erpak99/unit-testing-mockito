package com.testing.api.service;

import com.testing.api.dto.UserDto;
import com.testing.api.dto.UserResponse;
import com.testing.api.model.User;
import com.testing.api.repository.UserRepository;
import com.testing.api.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertAll;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void UserService_CreateUser_ReturnsUserDto() {
        User user = User.builder()
                .name("Arda")
                .type("customer").build();

        UserDto userDto = UserDto.builder()
                .name("Arda")
                .type("customer")
                .build();

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto savedUser = userService.createUser(userDto);

        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    public void UserService_GetAllUser_ReturnsResponseDto() {

        Page<User> users = Mockito.mock(Page.class);

        when(userRepository.findAll(Mockito.any(Pageable.class))).thenReturn(users);

        UserResponse savedUser = userService.getAllUser(1,10);

        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    public void UserService_GetUserById_ReturnsUserDto() {
        User user = User.builder()
                .name("Arda")
                .type("customer").build();

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));

        UserDto savedUser = userService.getUserById(1);

        Assertions.assertThat(savedUser).isNotNull();

    }

    @Test
    public void UserService_UpdateUser_ReturnsUserDto() {

        User user = User.builder()
                .name("Arda")
                .type("customer").build();

        UserDto userDto = UserDto.builder()
                .name("Arda")
                .type("customer")
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto savedUser = userService.updateUser(userDto,1);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserService_DeleteUser_ReturnsUserDto() {
        User user = User.builder()
                .name("Arda")
                .type("customer").build();

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));

        assertAll(() -> userService.deleteUserId(1));

    }



}
