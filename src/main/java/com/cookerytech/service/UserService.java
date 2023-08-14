package com.cookerytech.service;

import com.cookerytech.domain.Role;
import com.cookerytech.domain.User;
import com.cookerytech.domain.enums.RoleType;
import com.cookerytech.dto.request.RegisterRequest;
import com.cookerytech.dto.request.UserUpdateRequest;
import com.cookerytech.dto.response.UserResponse;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.exception.ConflictException;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.repository.UserRepository;
import com.cookerytech.security.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    public void saveUser(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, registerRequest.getEmail()));
        }


        Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);

        Set<Role> roles = new HashSet<>();
        roles.add(role);


        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        LocalDateTime now = LocalDateTime.now();


        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setAddress(registerRequest.getAddress());
        user.setCity(registerRequest.getCity());
        user.setCountry(registerRequest.getCountry());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setTaxNo(registerRequest.getTaxNo());
        user.setCreateAt(now);
        user.setPassword(encodedPassword);


        user.setRoles(roles);

        userRepository.save(user);
    }
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_EXCEPTION, email)));
        return user;
    }

    public User getById(Long id){
        User user = userRepository.findUserById(id).orElseThrow(()->new
                ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_EXCEPTION,id)));
        return user;
    }

    //TODO => Offer'ı (teklifi) varsa silinemez eklenecek
    public void removeUserById(Long id) {
        User user = getById(id);
        if (user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        userRepository.deleteById(id);
    }

    public Page<UserResponse> getUserPage(String qLower, Pageable pageable) {

        Page<UserResponse> usersWithPage = null;
        if (!qLower.isEmpty()) {
            usersWithPage = userRepository.getAllUserWithQAdmin(qLower, pageable);
        } else {
            usersWithPage = userRepository.findAllWithPage(pageable);
        }
        return usersWithPage;
    }


    public void updatePassword(UserUpdateRequest userUpdateRequest) {

        User user = getCurrentUser();

        if(user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if(!passwordEncoder.matches(userUpdateRequest.getOldPassword(), user.getPassword())){
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED_MESSAGE);
        }

        String hashedPassword = passwordEncoder.encode(userUpdateRequest.getNewPassword());

        user.setPassword(hashedPassword);

        userRepository.save(user);

    }

    public User getCurrentUser(){
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(
                ()-> new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
        User user = getUserByEmail(email);
        return user;
    }

    public UserResponse getUserResponseById(Long id) {
        return new UserResponse(getById(id));
    }
}
