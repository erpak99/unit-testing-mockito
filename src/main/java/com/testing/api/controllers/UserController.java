package com.testing.api.controllers;

import com.testing.api.dto.UserDto;
import com.testing.api.dto.UserResponse;
import com.testing.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("user/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("user")
    public ResponseEntity<UserResponse> getUsers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(userService.getAllUser(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDto> userDetail(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));

    }


    @PutMapping("user/{id}/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") int userId) {
        UserDto response = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int userId) {
        userService.deleteUserId(userId);
        return new ResponseEntity<>("User delete", HttpStatus.OK);
    }


}
