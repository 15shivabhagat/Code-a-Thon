package com.crio.app.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.crio.app.data.Badges;
import com.crio.app.data.User;
import com.crio.app.exceptions.UserAlreadyRegister;
import com.crio.app.exceptions.UserIdNotFoundException;
import com.crio.app.exchange.Response;
import com.crio.app.exchange.UpdateUserScoreRequest;
import com.crio.app.exchange.UserRegistrationRequest;
import com.crio.app.exchange.UserRegistrationResponse;
import com.crio.app.repository.UserRegistrationRepository;
import com.crio.app.service.UserRegistrationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRegistrationRepository userRegistrationRepository;

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) throws UserAlreadyRegister {
        if(userRegistrationRepository.existsByUsername(userRegistrationRequest.username())) {
            // throw error
            throw new UserAlreadyRegister(userRegistrationRequest.username()+" user already registered");
        }

        User user = User.builder()
                        .id(userRegistrationRequest.userId())
                        .username(userRegistrationRequest.username())
                        .score(0)
                        .badges(new HashSet<>())
                        .build();
        
        user = userRegistrationRepository.save(user);
        UserRegistrationResponse response = new UserRegistrationResponse(
                user.getId(), 
                user.getUsername(),
                user.getScore(),
                user.getBadges()
            );
        return response; 
    }

    @Override
    public UserRegistrationResponse getRegisterUser(String userId) throws UserIdNotFoundException {
        Optional<User> userOptional = userRegistrationRepository.findById(userId);
        if(!userOptional.isPresent()) {
            // Throw Error
            throw new UserIdNotFoundException("User Id not found");
        }

        User user = userOptional.get();
        UserRegistrationResponse response = new UserRegistrationResponse(
                user.getId(),
                user.getUsername(),
                user.getScore(),
                user.getBadges()
                );
        return response; 
    }

    @Override
    public UserRegistrationResponse updateRegisterUserScore(String userId,
            UpdateUserScoreRequest updateUserScoreRequest) throws UserIdNotFoundException {
        
        Optional<User> userOptional = userRegistrationRepository.findById(userId);
        if(!userOptional.isPresent()) {
            throw new UserIdNotFoundException("User Id not found");
        }

        User user = userOptional.get();
        user.setScore(updateUserScoreRequest.score());
        addBadgeAccordingToScore(user, updateUserScoreRequest.score());

        userRegistrationRepository.save(user);

        UserRegistrationResponse response = new UserRegistrationResponse(
                user.getId(),
                user.getUsername(),
                user.getScore(),
                user.getBadges()
                );
        return response;
    }

    @Override
    public List<UserRegistrationResponse> getAllRegisteredUsers() {
        List<User> users = userRegistrationRepository.findAllByOrderByScoreDesc();
        List<UserRegistrationResponse> responses = new ArrayList<>();
        for(User user : users) {
            UserRegistrationResponse response = new UserRegistrationResponse(user.getId(), user.getUsername(), user.getScore(), user.getBadges());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public Response removeRegisteredUserById(String userId) throws UserIdNotFoundException {
        Optional<User> userOptional = userRegistrationRepository.findById(userId);
        if(!userOptional.isPresent()) {
            throw new UserIdNotFoundException("User Id not found");
        }
        User user = userOptional.get();
        userRegistrationRepository.delete(user);
        Response response = new Response(HttpStatus.OK, "User removed Ssuccessfully!");
        return response;
    }

    private void addBadgeAccordingToScore(User user, Integer score) {
        Set<Badges> badge = user.getBadges();
        if(score >= 60 && score <= 100) {
            badge.add(Badges.CODE_MASTER);  
        } else if(score >= 30 && score <= 59) {
            badge.add(Badges.CODE_CHAMP);
        } else {
            badge.add(Badges.CODE_NINJA);
        }
        user.setBadges(badge);
    }

    
}
