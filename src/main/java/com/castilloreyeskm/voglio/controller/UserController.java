package com.castilloreyeskm.voglio.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castilloreyeskm.voglio.dto.UserDto;
import com.castilloreyeskm.voglio.exceptions.AlreadyExistException;
import com.castilloreyeskm.voglio.exceptions.ResourceNotFoundException;
import com.castilloreyeskm.voglio.model.User;
import com.castilloreyeskm.voglio.request.UserAddRequest;
import com.castilloreyeskm.voglio.request.UserUpdateRequest;
import com.castilloreyeskm.voglio.response.ApiResponse;
import com.castilloreyeskm.voglio.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
	private final IUserService userService;

	@GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User found", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

	@PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserAddRequest request) {
        try {
            UserDto userDto = userService.createUser(request);
            return ResponseEntity.ok(new ApiResponse("User add successful", userDto));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

	@PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        try {
            UserDto userDto = userService.updateUser(request, userId);
            return ResponseEntity.ok(new ApiResponse("User update Successful", userDto));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

	@DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User delete successful!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
