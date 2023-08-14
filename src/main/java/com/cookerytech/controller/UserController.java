package com.cookerytech.controller;

import com.cookerytech.dto.request.UserUpdateRequest;
import com.cookerytech.dto.response.CTResponse;
import com.cookerytech.dto.response.ResponseMessage;
import com.cookerytech.dto.response.UserResponse;
import com.cookerytech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @DeleteMapping("/{id}/auth")
    public ResponseEntity<CTResponse> deleteUser(@PathVariable Long id){
        userService.removeUserById(id);
        CTResponse response = new CTResponse();
        response.setMessage(ResponseMessage.USER_DELETE_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_SPECIALIST') or hasRole('SALES_MANAGER')")
    public ResponseEntity<Page<UserResponse>> getAllUsersByPage(

            @RequestParam(required = false, value = "q", defaultValue = "") String q,
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false,value = "size", defaultValue = "20") int size,
            @RequestParam(required = false,value = "sort", defaultValue = "createAt") String prop,
            @RequestParam(required = false,value = "type", defaultValue = "DESC") Sort.Direction direction) {

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));

        String qLower = q.toLowerCase();

        Page<UserResponse> usersWithPage = userService.getUserPage(qLower, pageable);
        return  ResponseEntity.ok(usersWithPage);
    }

    @PatchMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('PRODUCT_MANAGER') or hasRole('SALES_SPECIALIST') or hasRole('SALES_MANAGER')")
    public ResponseEntity<CTResponse> updatePassword(
            @Valid @RequestBody UserUpdateRequest userUpdateRequest){

        userService.updatePassword(userUpdateRequest);

        CTResponse response = new CTResponse();
        response.setMessage(ResponseMessage.PASSWORD_CHANGED_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
    //deneme...
}
