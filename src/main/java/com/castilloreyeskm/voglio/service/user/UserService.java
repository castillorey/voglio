package com.castilloreyeskm.voglio.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.castilloreyeskm.voglio.dto.UserDto;
import com.castilloreyeskm.voglio.exceptions.AlreadyExistException;
import com.castilloreyeskm.voglio.exceptions.ResourceNotFoundException;
import com.castilloreyeskm.voglio.model.User;
import com.castilloreyeskm.voglio.repository.UserRepository;
import com.castilloreyeskm.voglio.request.UserAddRequest;
import com.castilloreyeskm.voglio.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found!"));
	}

	@Override
	public UserDto createUser(UserAddRequest request) {
		User newUser =  Optional.of(request)
				.filter(req -> !userRepository.existsByEmail(req.getEmail()))
				.map(req -> {
					User user = new User();
					user.setEmail(req.getEmail());
					user.setPassword(req.getPassword());
					user.setFirstName(req.getFirstName());
					user.setLastName(req.getLastName());
					return user;
				}).orElseThrow(() -> new AlreadyExistException(request.getEmail() + " already exists"));
		return convertUserToDto(newUser);
	}

	@Override
	public UserDto updateUser(UserUpdateRequest request, Long userId) {
		User currentUser = userRepository.findById(userId)
				.map(user -> {
					user.setFirstName(request.getFirstName());
					user.setLastName(request.getLastName());
					return userRepository.save(user);
				}).orElseThrow(() -> new ResourceNotFoundException("User not found"));
		return convertUserToDto(currentUser);
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
            throw new ResourceNotFoundException("User not found");
        });
	}

	@Override
	public UserDto convertUserToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	@Override
    public User getAuthenticatedUser() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

}
