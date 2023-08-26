package com.cookerytech.service;

import com.cookerytech.domain.Role;
import com.cookerytech.domain.User;
import com.cookerytech.domain.enums.RoleType;
import com.cookerytech.dto.UserDTO;
import com.cookerytech.dto.request.RegisterRequest;
import com.cookerytech.dto.request.UserDeleteRequest;
import com.cookerytech.dto.request.UserRequest;
import com.cookerytech.dto.request.UserUpdateRequest;
import com.cookerytech.dto.response.UserResponse;
import com.cookerytech.exception.BadRequestException;
import com.cookerytech.exception.ConflictException;
import com.cookerytech.exception.ResourceNotFoundException;
import com.cookerytech.exception.message.ErrorMessage;
import com.cookerytech.mapper.UserMapper;
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
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }


    public UserDTO saveUser(RegisterRequest registerRequest) {

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

       User savedUser = userRepository.save(user);
       UserDTO userDTO = userMapper.userToUserDTO(savedUser);


       return userDTO;
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
    public UserDTO removeUserById(Long id) {
        User user = getById(id);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        User currentUser = getCurrentUser();

        if (user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if(currentUser.getRoles().equals(RoleType.ROLE_SALES_SPECIALIST) && user.getRoles().equals(RoleType.ROLE_CUSTOMER)){
            userRepository.deleteById(id);
        }

        if(currentUser.getRoles().equals(RoleType.ROLE_SALES_MANAGER) &&
                (user.getRoles().equals(RoleType.ROLE_CUSTOMER) || user.getRoles().equals(RoleType.ROLE_SALES_SPECIALIST)))
        {
            userRepository.deleteById(id);
        }

        if(currentUser.getRoles().equals(RoleType.ROLE_ADMIN) )
        {
            userRepository.deleteById(id);
        }

        return userDTO;
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
    public void createPasswordResetToken(String email) {
        User user =  getUserByEmail(email);

//        // Şifre sıfırlama tokenı oluştur
//        String tokenValue = generateToken();
//        PasswordResetToken token = new PasswordResetToken();
//        token.setUser(user);
//        token.setToken(tokenValue);
//
//        passwordResetTokenRepository.save(token);
    }

    public void resetPassword(String email, String password) {

        User user =  getUserByEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        // Ayrıca, sıfırlama tokenını veritabanından silmelisiniz?
        userRepository.deleteByEmail(user);
    }

    private String generateToken() {
        return UUID.randomUUID().toString(); // Rastgele UUID oluşturma
//        generateToken() metodu, java.util.UUID.randomUUID().toString()
//        ile rastgele bir UUID oluşturur ve bu UUID'yi string olarak döndürür.
//        Bu UUID, benzersizliği garanti eden bir dize olarak şifre sıfırlama tokenı olarak kullanılabilir.
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


    public void removeUserByAuth(UserDeleteRequest userDeleteRequest) {
        User user = getCurrentUser();
        if(user.getPassword().equals(userDeleteRequest.getPassword())){
            userRepository.deleteById(user.getId());
        }
    }

    public UserResponse getUserResponseById(Long id) {
        return new UserResponse(getById(id));

    }


    public UserResponse updateUser(UserRequest userRequest) {



        User user =getCurrentUser();
      if(user.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
      }


        boolean emailExist = userRepository.existsByEmail(userRequest.getEmail());// burada Db de varmı baklılcak
        //Sneryo kontrolu
        if(emailExist && !userRequest.getEmail().equals(user.getEmail())) {// buraya 3 senaryoda girme kontorlu
            throw new ConflictException(
                    String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,userRequest.getEmail()));
        }

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        user.setCity(userRequest.getCity());
        user.setCountry(userRequest.getCountry());
        user.setBirthDate(userRequest.getBirthDate());
        user.setTaxNo(userRequest.getTaxNo());
        user.setStatus(userRequest.getStatus());
        user.setUpdateAt(LocalDateTime.now());
        userRepository.save(user);

        UserResponse userResponse = new UserResponse(user);
        return userResponse;
    }
}
