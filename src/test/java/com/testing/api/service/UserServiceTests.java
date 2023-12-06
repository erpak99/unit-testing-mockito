package com.testing.api.service;

import com.testing.api.dto.UserDto;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    public UserRepository userRepository;

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


}
