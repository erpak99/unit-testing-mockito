package com.testing.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.api.controllers.UserController;
import com.testing.api.dto.ReviewDto;
import com.testing.api.dto.UserDto;
import com.testing.api.dto.UserResponse;
import com.testing.api.model.Review;
import com.testing.api.model.User;
import com.testing.api.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Review review;
    private ReviewDto reviewDto;
    private UserDto userDto;

    @BeforeEach
    public void init() {
        user = User.builder().name("Arda").type("customer").build();
        userDto = UserDto.builder().name("Arda").type("customer").build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    public void UserController_CreateUser_ReturnCreated() throws Exception {

        given(userService.createUser(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(userDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(userDto.getType())));

    }

    @Test
    public void UserController_GetAllUser_ReturnResponseDto() throws Exception {

        UserResponse responseDto = UserResponse.builder()
                .pageSize(10)
                .last(true)
                .pageNo(1)
                .content(Arrays.asList(userDto))
                .build();

        when(userService.getAllUser(1,10))
                .thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize","10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content.size()",CoreMatchers.is(responseDto.getContent().size())));

    }

    @Test
    public void UserController_GetUserById_ReturnUserDto() throws Exception {
        int userId = 1;
        when(userService.getUserById(userId)).thenReturn(userDto);

        ResultActions response = mockMvc.perform(get("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(userDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(userDto.getType())));
    }

    @Test
    public void UserController_UpdateUser_ReturnUserDto() throws Exception {
        int userId = 1;
        when(userService.updateUser(userDto, userId)).thenReturn(userDto);

        ResultActions response = mockMvc.perform(put("/api/user/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(userDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(userDto.getType())));
    }

    @Test
    public void UserController_DeleteUser_ReturnString() throws Exception {
        int userId = 1;
        doNothing().when(userService).deleteUserId(1);

        ResultActions response = mockMvc.perform(delete("/api/user/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


}
