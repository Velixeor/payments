package com.example.payments.service;


import com.example.payments.dto.UserCoreDTO;
import com.example.payments.dto.UserDTO;
import com.example.payments.entity.Status;
import com.example.payments.entity.User;
import com.example.payments.exception.user.UserCreationException;
import com.example.payments.exception.user.UserDeleteException;
import com.example.payments.exception.user.UserUpdateException;
import com.example.payments.repository.UserLoyaltyLevelRepository;
import com.example.payments.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserLoyaltyLevelRepository userLoyaltyLevelRepository;
    private final CoreServiceSynchronization coreService;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        log.info("Start transactional");
        if (!userRepository.existsUserByLoginAndAndMailAndNumberPhone(userDTO.getLogin(), userDTO.getNumberPhone(), userDTO.getMail())) {
            transferringDataInUserFromUserDTO(userDTO, user);
            User resultUser = userRepository.save(user);
            UserDTO resultUserDTO = transferringDataInUserDTOFromUser(resultUser);
            coreService.coreSynchronization(resultUserDTO);
            log.info("User created successfully: {}", userDTO);
            return resultUserDTO;
        } else {
            log.error("Failed to create user: {}", userDTO);
            throw new UserCreationException(userDTO);
        }
    }

    @Transactional
    public UserDTO updateUserById(UserDTO userDTO) {
        User user = userRepository.getUserById(userDTO.getId());
        log.info("Start transactional");
        if (user.getStatus() != Status.ACTIVE) {
            log.error("Failed to update user: {}", userDTO);
            throw new UserUpdateException(userDTO);
        }

        List<User> userList = userRepository.findByLoginOrMailOrNumberPhone(userDTO.getLogin(), userDTO.getMail(), userDTO.getNumberPhone());
        for (User u : userList) {
            if (!user.getId().equals(u.getId())) {
                log.error("Failed to update user: {}", userDTO);
                throw new UserUpdateException(userDTO);
            }
        }

        transferringDataInUserFromUserDTO(userDTO, user);
        user.setDateUpdate(ZonedDateTime.now());
        User resultUser = userRepository.save(user);
        UserDTO resultUserDTO = transferringDataInUserDTOFromUser(resultUser);
        UserCoreDTO userCoreDTO = new UserCoreDTO(resultUserDTO.getId(), resultUserDTO.getLogin(), resultUserDTO.getStatus());
        coreService.updateCoreUser(userCoreDTO);
        log.info("User update successfully: {}", userDTO);
        return resultUserDTO;
    }

    public void deleteUser(Integer idUser) {
        User user = userRepository.getUserById(idUser);
        user.setDateUpdate(ZonedDateTime.now());
        if (!user.isStaff()) {
            userRepository.delete(user);
            log.info("User delete successfully: {}", idUser);
        } else {
            log.error("Failed to delete user: {}", idUser);
            throw new UserDeleteException("Attempt to delete staff");
        }

    }

    public UserDTO getUser(Integer idUser) {
        User user = userRepository.getUserById(idUser);
        UserDTO userDTO = transferringDataInUserDTOFromUser(user);
        return userDTO;

    }

    private void transferringDataInUserFromUserDTO(final UserDTO userDTO, User user) {
        user.setPassword(userDTO.getPassword());
        user.setDateCreate(userDTO.getDateCreate());
        user.setMail(userDTO.getMail());
        user.setStaff(userDTO.isStaff());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setLogin(userDTO.getLogin());
        user.setNumberPhone(userDTO.getNumberPhone());
        user.setUserLoyaltyLevel(userLoyaltyLevelRepository.getUserLoyaltyLevelById(userDTO.getUserLoyaltyLevelId()));
        user.setStatus(userDTO.getStatus());

    }

    private UserDTO transferringDataInUserDTOFromUser(final User user) {
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .mail(user.getMail())
                .numberPhone(user.getNumberPhone())
                .isStaff(user.isStaff())
                .dateCreate(user.getDateCreate())
                .status(user.getStatus())
                .userLoyaltyLevelId(user.getUserLoyaltyLevel().getId())
                .build();
        return userDTO;

    }


}
