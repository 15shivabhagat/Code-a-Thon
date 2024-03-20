package com.crio.app.service;

import java.util.List;

import com.crio.app.exceptions.UserAlreadyRegister;
import com.crio.app.exceptions.UserIdNotFoundException;
import com.crio.app.exchange.Response;
import com.crio.app.exchange.UpdateUserScoreRequest;
import com.crio.app.exchange.UserRegistrationRequest;
import com.crio.app.exchange.UserRegistrationResponse;

public interface UserRegistrationService {

    UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) throws UserAlreadyRegister;

    UserRegistrationResponse getRegisterUser(String userId) throws UserIdNotFoundException;

    UserRegistrationResponse updateRegisterUserScore(String userId, UpdateUserScoreRequest updateUserScoreRequest) throws UserIdNotFoundException;

    List<UserRegistrationResponse> getAllRegisteredUsers();

    Response removeRegisteredUserById(String userId) throws UserIdNotFoundException;
    
}
