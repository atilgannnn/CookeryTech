package com.cookerytech.controller;


import com.cookerytech.dto.request.ForgotPasswordRequest;
import com.cookerytech.dto.request.LoginRequest;
import com.cookerytech.dto.request.RegisterRequest;
import com.cookerytech.dto.request.ResetPasswordRequest;
import com.cookerytech.dto.response.*;
import com.cookerytech.security.jwt.*;
import com.cookerytech.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class UserJwtController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;//ilk karsilayacak olan


    @PostMapping("/register")
    public ResponseEntity<CTResponse> registerUser(@Valid
                                                   @RequestBody RegisterRequest registerRequest) {
        userService.saveUser(registerRequest);

        CTResponse response = new CTResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid
                                                      @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword());

        Authentication authentication =
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJwtToken(userDetails);

        LoginResponse loginResponse = new LoginResponse(jwtToken);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        // Şifre sıfırlama isteği işlemleri
        userService.createPasswordResetToken(forgotPasswordRequest.getEmail());
        return ResponseEntity.ok("Password reset request received.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        // Şifre sıfırlama işlemleri
        userService.resetPassword(resetPasswordRequest.getEmail(), resetPasswordRequest.getPassword());
        return ResponseEntity.ok("Password reset successfully.");
    }
}