package com.example.payments.test.service;


import com.example.payments.dto.UserDTO;
import com.example.payments.entity.Status;
import com.example.payments.entity.User;
import com.example.payments.entity.UserLoyaltyLevel;
import com.example.payments.exception.user.UserCreationException;
import com.example.payments.exception.user.UserDeleteException;
import com.example.payments.exception.user.UserUpdateException;
import com.example.payments.repository.UserRepository;
import com.example.payments.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void CreateUserTestUserCreationException() {
        ZonedDateTime fixedDateTime = ZonedDateTime.parse("2024-07-14T20:14:05.593901Z");
        UserDTO userDTO = new UserDTO(1, "Velixeor", "123", "Egor", "Tsygankov", "Michalovich"
                , "egor-tsygankov@mail.ru", "+79003674455", true, fixedDateTime, Status.ACTIVE, 2);
        Mockito.when(userRepository.existsUserByLoginAndAndMailAndNumberPhone(userDTO.getLogin(), userDTO.getNumberPhone(),
                userDTO.getMail())).thenReturn(true);
        assertThrows(UserCreationException.class, () -> userService.createUser(userDTO));
    }

    @Test
    void DeleteUserTestUserDeleteException() {
        ZonedDateTime fixedDateTime = ZonedDateTime.parse("2024-07-14T20:14:05.593901Z");
        User user = new User(1, "Velixeor", "123", "Egor", "Tsygankov", "Michalovich"
                , "egor-tsygankov@mail.ru", "+79003674455", true, fixedDateTime, null, Status.ACTIVE,
                new UserLoyaltyLevel());
        Mockito.when(userRepository.getUserById(1)).thenReturn(user);
        assertThrows(UserDeleteException.class, () -> userService.deleteUser(1));
    }

    @Test
    void UpdateUserTestUserUpdateException() {
        ZonedDateTime fixedDateTime = ZonedDateTime.parse("2024-07-14T20:14:05.593901Z");
        UserDTO userDTO = new UserDTO(1, "Velixeor", "123", "Egor", "Tsygankov", "Michalovich"
                , "egor-tsygankov@mail.ru", "+79003674455", true, fixedDateTime, Status.ACTIVE, 2);
        User user1 = new User(1, "Velixeor1", "123", "Egor", "Tsygankov", "Michalovich"
                , "egor-tsygankov1@mail.ru", "+79003674456", true, fixedDateTime, null, Status.ACTIVE,
                new UserLoyaltyLevel());
        User user2 = new User(2, "Velixeor", "123", "Egor", "Tsygankov", "Michalovich"
                , "egor-tsygankov@mail.ru", "+79003674455", true, fixedDateTime, null, Status.ACTIVE,
                new UserLoyaltyLevel());
        Mockito.when(userRepository.getUserById(1)).thenReturn(user1);
        Mockito.when(userRepository.findByLoginOrMailOrNumberPhone("Velixeor", "egor-tsygankov@mail.ru",
                "+79003674455")).thenReturn(List.of(user2));

        assertThrows(UserUpdateException.class, () -> userService.updateUserById(userDTO));
    }
}
