package com.crio.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.crio.app.data.User;
import com.crio.app.exceptions.UserAlreadyRegister;
import com.crio.app.exceptions.UserIdNotFoundException;
import com.crio.app.exchange.Response;
import com.crio.app.exchange.UpdateUserScoreRequest;
import com.crio.app.exchange.UserRegistrationRequest;
import com.crio.app.exchange.UserRegistrationResponse;
import com.crio.app.repository.UserRegistrationRepository;


@SpringBootTest
public class UserRegistrationServiceTest {

    @Autowired
    private UserRegistrationService userRegistrationService;

    @MockBean
    private UserRegistrationRepository userRegistrationRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                        .id("1")
                        .username("test_user")
                        .score(0)
                        .badges(new HashSet<>())
                        .build();
    }

    @Test
    void testGetRegisterUser_returnSuccess() throws UserIdNotFoundException {
        Mockito.when(userRegistrationRepository.findById("1")).thenReturn(Optional.of(user));

        UserRegistrationResponse response = userRegistrationService.getRegisterUser("1");
        assertEquals("test_user", response.username());
        assertEquals("1", response.userId());
    }

    @Test
    void testGetRegisterUser_throwUserIdNotFoundException() throws UserIdNotFoundException {
        Mockito.when(userRegistrationRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserIdNotFoundException.class, () -> {
            userRegistrationService.getRegisterUser("1");
        });
        
        assertTrue(exception.getMessage().contains("User Id not found"));
    }

    @Test
    void testRegisterUser_returnSuccess() throws UserAlreadyRegister {
        UserRegistrationRequest newUser = new UserRegistrationRequest("1", "test_user");
        User buildUser = User.builder()
                    .id("1")
                    .username("test_user")
                    .score(0)
                    .badges(new HashSet<>())
                    .build();
    
        Mockito.when(userRegistrationRepository.existsByUsername(newUser.username())).thenReturn(false);
        Mockito.when(userRegistrationRepository.save(buildUser)).thenReturn(user);

        UserRegistrationResponse response = userRegistrationService.registerUser(newUser);
        assertEquals("test_user", response.username());
        assertEquals("1", response.userId());
    }

    @Test
    void testRegisterUser_throwUserAlreadyRegisterException() throws UserAlreadyRegister {
        UserRegistrationRequest newUser = new UserRegistrationRequest("1", "test_user");        
        Mockito.when(userRegistrationRepository.existsByUsername(newUser.username())).thenReturn(true);

        Exception exception = assertThrows(UserAlreadyRegister.class, () -> {
            userRegistrationService.registerUser(newUser);
        });

        assertTrue(exception.getMessage().contains(newUser.username()+" user already registered"));
        
    }

    @Test
    void testUpdateRegisterUserScore_returnSuccess() throws UserIdNotFoundException {
        
        Mockito.when(userRegistrationRepository.findById("1")).thenReturn(Optional.of(user));
        user.setScore(23);
        Mockito.when(userRegistrationRepository.save(user)).thenReturn(null);

        UserRegistrationResponse response = userRegistrationService.updateRegisterUserScore("1", new UpdateUserScoreRequest(23));
        assertEquals(23, response.score());
    }

    @Test
    void testUpdateRegisterUserScore_throwUserIdNotFoundException() throws UserIdNotFoundException {
        Mockito.when(userRegistrationRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserIdNotFoundException.class, () -> {
            userRegistrationService.updateRegisterUserScore("1", new UpdateUserScoreRequest(23));
        });
        
        assertTrue(exception.getMessage().contains("User Id not found"));
    }

    @Test
    void testGetAllRegisteredUsers_returnSuccess() {
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRegistrationRepository.findAllByOrderByScoreDesc()).thenReturn(users);

        List<UserRegistrationResponse> response = userRegistrationService.getAllRegisteredUsers();

        assertEquals(1, response.size());
        assertEquals("test_user", response.get(0).username());
    }

    @Test
    void testRemoveRegisteredUserById_returnSuccess() throws UserIdNotFoundException {
    
        Mockito.when(userRegistrationRepository.findById("1")).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRegistrationRepository).delete(user);

        Response response = userRegistrationService.removeRegisteredUserById("1");
        assertEquals("User removed Ssuccessfully!", response.message());
        assertEquals(HttpStatus.OK, response.status());
    }

    @Test
    void testRemoveRegisteredUserById_throwUserIdNotFoundException() throws UserIdNotFoundException {
        Mockito.when(userRegistrationRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserIdNotFoundException.class, () -> {
            userRegistrationService.removeRegisteredUserById("1");
        });

        assertTrue(exception.getMessage().contains("User Id not found"));
        
    }

}
