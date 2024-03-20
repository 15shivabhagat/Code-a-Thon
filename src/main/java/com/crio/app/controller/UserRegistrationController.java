package com.crio.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.app.exceptions.UserAlreadyRegister;
import com.crio.app.exceptions.UserIdNotFoundException;
import com.crio.app.exchange.Response;
import com.crio.app.exchange.UpdateUserScoreRequest;
import com.crio.app.exchange.UserRegistrationRequest;
import com.crio.app.exchange.UserRegistrationResponse;
import com.crio.app.service.UserRegistrationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/users")
    public ResponseEntity<UserRegistrationResponse> registerUser(
            @RequestBody UserRegistrationRequest userRegistrationRequest) throws UserAlreadyRegister {
        UserRegistrationResponse userRegistrationResponse = userRegistrationService
                .registerUser(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistrationResponse);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserRegistrationResponse> getRegisterUser(@PathVariable("id") String userId)
            throws UserIdNotFoundException {
        UserRegistrationResponse userRegistrationResponse = userRegistrationService.getRegisterUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userRegistrationResponse);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserRegistrationResponse> updateRegisterUserScore(@PathVariable("id") String userId,
            @Valid @RequestBody UpdateUserScoreRequest updateUserScoreRequest) throws UserIdNotFoundException {
        UserRegistrationResponse userRegistrationResponse = userRegistrationService.updateRegisterUserScore(userId,
                updateUserScoreRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userRegistrationResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserRegistrationResponse>> getAllRegisteredUsers() {
        List<UserRegistrationResponse> users = userRegistrationService.getAllRegisteredUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Response> removeRegisteredUserById(@PathVariable("id") String userId) throws UserIdNotFoundException {
        Response response = userRegistrationService.removeRegisteredUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
