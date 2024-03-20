package com.crio.app.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.crio.app.exceptions.UserAlreadyRegister;
import com.crio.app.exceptions.UserIdNotFoundException;
import com.crio.app.exchange.Response;
import com.crio.app.exchange.UpdateUserScoreRequest;
import com.crio.app.exchange.UserRegistrationRequest;
import com.crio.app.exchange.UserRegistrationResponse;
import com.crio.app.service.UserRegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserRegistrationController.class)
public class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRegistrationService userRegistrationService;

    private UserRegistrationResponse response;

    @BeforeEach
    void setUp() {
        response = new UserRegistrationResponse("1",
                                            "test_user",
                                            0,
                                            new HashSet<>()
                                        );
    }


    @Test
    void testGetRegisterUserSuccess() throws Exception {
        Mockito.when(userRegistrationService.getRegisterUser("1")).thenReturn(response);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists());
    }

    @Test
    void testGetRegisterUser_thenThrowError() throws Exception {
        Mockito.when(userRegistrationService.getRegisterUser("1")).thenThrow(new UserIdNotFoundException("user not registered"));
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    void testRegisterUserSuccess() throws JsonProcessingException, Exception {
        UserRegistrationRequest request = 
                        new UserRegistrationRequest("1","test_user");
        Mockito.when(userRegistrationService.registerUser(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists());
    }

    @Test
    void testUserAlreadyRegistered_thenThrowError() throws JsonProcessingException, Exception {
        UserRegistrationRequest request = 
                        new UserRegistrationRequest("1", "test_user");
        Mockito.when(userRegistrationService.registerUser(request)).thenThrow(new UserAlreadyRegister("user already registered"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    void testUpdateRegisterUserScoreSuccess() throws JsonProcessingException, Exception {
        String userId = "1";
        UpdateUserScoreRequest updateUserScoreRequest =
                    new UpdateUserScoreRequest(10);
        
        Mockito.when(userRegistrationService.updateRegisterUserScore(userId, updateUserScoreRequest))
                .thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserScoreRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists());
    }

    @Test
    void testUpdateRegisterUserScore_thenThrowError() throws JsonProcessingException, Exception {
        String userId = "1";
        UpdateUserScoreRequest updateUserScoreRequest = new UpdateUserScoreRequest(10);
        Mockito.when(userRegistrationService.updateRegisterUserScore(userId, updateUserScoreRequest)).thenThrow(new UserIdNotFoundException("user not registered"));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateUserScoreRequest)))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    void testGetAllRegisteredUsers() throws Exception {
        List<UserRegistrationResponse> responses = new ArrayList<>();
        responses.add(response);

        Mockito.when(userRegistrationService.getAllRegisteredUsers()).thenReturn(responses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").exists());
    }

    @Test
    void testRemoveRegisteredUserByIdSuccess() throws Exception {
        Response response = new Response(HttpStatus.OK, "User removed Ssuccessfully!");
        Mockito.when(userRegistrationService.removeRegisteredUserById("1")).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User removed Ssuccessfully!"));
    }

    @Test
    void testRemoveRegisteredUserById_thenThrowError() throws Exception {
        Mockito.when(userRegistrationService.removeRegisteredUserById("1")).thenThrow(new UserIdNotFoundException("user not registered"));
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }
}
