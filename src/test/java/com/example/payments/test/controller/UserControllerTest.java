package com.example.payments.test.controller;


import com.example.payments.controller.UserController;
import com.example.payments.dto.UserDTO;
import com.example.payments.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void CreateUser() throws Exception {
        ZonedDateTime fixedDateTime = ZonedDateTime.parse("2024-07-14T20:14:05.593901Z");
        UserDTO userDTO = new UserDTO(1, "Velixeor", "123", "Egor", "Tsygankov", "Michalovich"
                , "egor-tsygankov@mail.ru", "+79003674455", true, fixedDateTime, 2, 2);
        String userDTOJson = objectMapper.writeValueAsString(userDTO);
        when(userService.createUser(userDTO)).thenReturn(userDTO);
        mockMvc.perform(post("/api/user/create_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("Velixeor"))
                .andExpect(jsonPath("$.password").value("123"))
                .andExpect(jsonPath("$.firstName").value("Egor"))
                .andExpect(jsonPath("$.middleName").value("Tsygankov"))
                .andExpect(jsonPath("$.lastName").value("Michalovich"))
                .andExpect(jsonPath("$.mail").value("egor-tsygankov@mail.ru"))
                .andExpect(jsonPath("$.numberPhone").value("+79003674455"))
                .andExpect(jsonPath("$.isStaff").value(true))
                .andExpect(jsonPath("$.dateCreate").value("2024-07-14T20:14:05.593901Z"))
                .andExpect(jsonPath("$.userStatusId").value(2))
                .andExpect(jsonPath("$.userLoyaltyLevelId").value(2));
        verify(userService, times(1)).createUser(userDTO);

    }

    @Test
    void getUserById() throws Exception {
        ZonedDateTime fixedDateTime = ZonedDateTime.parse("2024-07-14T20:14:05.593901Z");
        UserDTO userDTO = new UserDTO(1, "Velixeor", "123", "Egor", "Tsygankov", "Michalovich"
                , "egor-tsygankov@mail.ru", "+79003674455", true, fixedDateTime, 2, 2);
        when(userService.getUser(1)).thenReturn(userDTO);
        mockMvc.perform(get("/api/user/get_user").param("idUser", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("Velixeor"))
                .andExpect(jsonPath("$.password").value("123"))
                .andExpect(jsonPath("$.firstName").value("Egor"))
                .andExpect(jsonPath("$.middleName").value("Tsygankov"))
                .andExpect(jsonPath("$.lastName").value("Michalovich"))
                .andExpect(jsonPath("$.mail").value("egor-tsygankov@mail.ru"))
                .andExpect(jsonPath("$.numberPhone").value("+79003674455"))
                .andExpect(jsonPath("$.isStaff").value(true))
                .andExpect(jsonPath("$.dateCreate").value("2024-07-14T20:14:05.593901Z"))
                .andExpect(jsonPath("$.userStatusId").value(2))
                .andExpect(jsonPath("$.userLoyaltyLevelId").value(2));
        verify(userService, times(1)).getUser(1);
    }

    @Test
    void UpdateUserById() throws Exception {
        ZonedDateTime fixedDateTime = ZonedDateTime.parse("2024-07-14T20:14:05.593901Z");
        UserDTO userDTO = new UserDTO(1, "Velixeor", "123", "Egor", "Tsygankov", "Michalovich"
                , "egor-tsygankov@mail.ru", "+79003674455", true, fixedDateTime, 2, 2);
        String userDTOJson = objectMapper.writeValueAsString(userDTO);
        when(userService.updateUserById(userDTO)).thenReturn(userDTO);
        mockMvc.perform(put("/api/user/update_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("Velixeor"))
                .andExpect(jsonPath("$.password").value("123"))
                .andExpect(jsonPath("$.firstName").value("Egor"))
                .andExpect(jsonPath("$.middleName").value("Tsygankov"))
                .andExpect(jsonPath("$.lastName").value("Michalovich"))
                .andExpect(jsonPath("$.mail").value("egor-tsygankov@mail.ru"))
                .andExpect(jsonPath("$.numberPhone").value("+79003674455"))
                .andExpect(jsonPath("$.isStaff").value(true))
                .andExpect(jsonPath("$.dateCreate").value("2024-07-14T20:14:05.593901Z"))
                .andExpect(jsonPath("$.userStatusId").value(2))
                .andExpect(jsonPath("$.userLoyaltyLevelId").value(2));
        verify(userService, times(1)).updateUserById(userDTO);
    }

    @Test
    void DeleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/delete_user").param("idUser", "1"))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(1);
    }


}
