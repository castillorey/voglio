package com.castilloreyeskm.voglio.service.user;

import com.castilloreyeskm.voglio.dto.UserDto;
import com.castilloreyeskm.voglio.model.User;
import com.castilloreyeskm.voglio.request.UserAddRequest;
import com.castilloreyeskm.voglio.request.UserUpdateRequest;

public interface IUserService {
	User getUserById(Long userId);
    UserDto createUser(UserAddRequest request);
    UserDto updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
