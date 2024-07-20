package com.example.payments.service;


import com.example.payments.dto.UserDTO;
import com.example.payments.entity.Status;
import com.example.payments.entity.User;
import com.example.payments.exception.user.UserCreationException;
import com.example.payments.exception.user.UserDeleteException;
import com.example.payments.exception.user.UserUpdateException;
import com.example.payments.repository.UserLoyaltyLevelRepository;
import com.example.payments.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserLoyaltyLevelRepository userLoyaltyLevelRepository;

    public UserService(UserRepository userRepository,
                       UserLoyaltyLevelRepository userLoyaltyLevelRepository) {
        this.userRepository = userRepository;
        this.userLoyaltyLevelRepository = userLoyaltyLevelRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        if (!userRepository.existsUserByLoginAndAndMailAndNumberPhone(userDTO.getLogin(), userDTO.getNumberPhone(), userDTO.getMail())) {
            TransferringDataInUserFromUserDTO(userDTO, user);
            User resultUser = userRepository.save(user);
            UserDTO resultUserDTO = TransferringDataInUserDTOFromUser(resultUser);
            log.info("User created successfully: {}", userDTO);
            return resultUserDTO;
        } else {
            log.warn("Failed to create user: {}", userDTO);
            throw new UserCreationException(userDTO);
        }
    }

    public UserDTO updateUserById(UserDTO userDTO) {
        User user = userRepository.getUserById(userDTO.getId());

        if (user.getStatus() != Status.ACTIVE) {
            log.warn("Failed to update user: {}", userDTO);
            throw new UserUpdateException(userDTO);
        }

        List<User> userList = userRepository.findByLoginOrMailOrNumberPhone(userDTO.getLogin(), userDTO.getMail(), userDTO.getNumberPhone());
        for (User u : userList) {
            if (!user.getId().equals(u.getId())) {
                log.warn("Failed to update user: {}", userDTO);
                throw new UserUpdateException(userDTO);
            }
        }

        TransferringDataInUserFromUserDTO(userDTO, user);
        User resultUser = userRepository.save(user);
        UserDTO resultUserDTO = TransferringDataInUserDTOFromUser(resultUser);
        log.info("User update successfully: {}", userDTO);
        return resultUserDTO;
    }

    public void deleteUser(Integer idUser) {
        User user = userRepository.getUserById(idUser);
        if (!user.getIsStaff()) {
            userRepository.delete(user);
            log.info("User delete successfully: {}", idUser);
        } else {
            log.warn("Failed to delete user: {}", idUser);
            throw new UserDeleteException("Attempt to delete staff");
        }

    }

    public UserDTO getUser(Integer idUser) {
        User user = userRepository.getUserById(idUser);
        UserDTO userDTO = TransferringDataInUserDTOFromUser(user);
        return userDTO;

    }

    private void TransferringDataInUserFromUserDTO(UserDTO userDTO, User user) {
        user.setPassword(userDTO.getPassword());
        user.setDateCreate(userDTO.getDateCreate());
        user.setMail(userDTO.getMail());
        user.setIsStaff(userDTO.getIsStaff());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setLogin(userDTO.getLogin());
        user.setNumberPhone(userDTO.getNumberPhone());
        user.setUserLoyaltyLevel(userLoyaltyLevelRepository.getUserLoyaltyLevelById(userDTO.getUserLoyaltyLevelId()));
        user.setStatus(userDTO.getStatus());

    }

    private UserDTO TransferringDataInUserDTOFromUser(User user) {
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .mail(user.getMail())
                .numberPhone(user.getNumberPhone())
                .isStaff(user.getIsStaff())
                .dateCreate(user.getDateCreate())
                .status(user.getStatus())
                .userLoyaltyLevelId(user.getUserLoyaltyLevel().getId())
                .build();
        return userDTO;

    }


}
