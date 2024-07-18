package com.example.payments.service;


import com.example.payments.dto.UserLoyaltyLevelDTO;
import com.example.payments.entity.Privilege;
import com.example.payments.entity.UserLoyaltyLevel;
import com.example.payments.exception.userLoyaltyLevel.UserLoyaltyLevelCreationException;
import com.example.payments.exception.userLoyaltyLevel.UserLoyaltyLevelUpdateException;
import com.example.payments.repository.PrivilegeRepository;
import com.example.payments.repository.UserLoyaltyLevelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class UserLoyaltyLevelService {
    private final UserLoyaltyLevelRepository userLoyaltyLevelRepository;
    private final PrivilegeRepository privilegeRepository;

    public UserLoyaltyLevelService(UserLoyaltyLevelRepository userLoyaltyLevelRepository,
                                   PrivilegeRepository privilegeRepository) {
        this.userLoyaltyLevelRepository = userLoyaltyLevelRepository;
        this.privilegeRepository = privilegeRepository;
    }


    public UserLoyaltyLevelDTO createUserLoyaltyLevel(UserLoyaltyLevelDTO userLoyaltyLevelDTO) {
        if (!userLoyaltyLevelRepository.existsUserLoyaltyLevelByCode(userLoyaltyLevelDTO.getCode())) {
            UserLoyaltyLevel userLoyaltyLevel = new UserLoyaltyLevel();
            TransferringDataInUserLoyaltyLevelFromUserLoyaltyLevelDTO(userLoyaltyLevelDTO, userLoyaltyLevel);
            UserLoyaltyLevel resultUserLoyaltyLevel = userLoyaltyLevelRepository.save(userLoyaltyLevel);
            return TransferringDataInUserLoyaltyLevelDTOFromUserLoyaltyLevel(resultUserLoyaltyLevel);
        } else {
            throw new UserLoyaltyLevelCreationException(userLoyaltyLevelDTO);
        }
    }

    public UserLoyaltyLevelDTO updateUserLoyaltyLevelById(UserLoyaltyLevelDTO userLoyaltyLevelDTO) {
        UserLoyaltyLevel userLoyaltyLevel = userLoyaltyLevelRepository.getUserLoyaltyLevelById(userLoyaltyLevelDTO.getId());
        List<UserLoyaltyLevel>  userLoyaltyLevels=userLoyaltyLevelRepository.findUserLoyaltyLevelByCode(userLoyaltyLevel.getCode());
        for (UserLoyaltyLevel u : userLoyaltyLevels) {
            if (!userLoyaltyLevel.getId().equals(u.getId())) {
                log.warn("Failed to update user: {}", userLoyaltyLevelDTO);
                throw new UserLoyaltyLevelUpdateException(userLoyaltyLevelDTO);
            }
        }
        TransferringDataInUserLoyaltyLevelFromUserLoyaltyLevelDTO(userLoyaltyLevelDTO, userLoyaltyLevel);
        UserLoyaltyLevel resultUserLoyaltyLevel = userLoyaltyLevelRepository.save(userLoyaltyLevel);
        return TransferringDataInUserLoyaltyLevelDTOFromUserLoyaltyLevel(resultUserLoyaltyLevel);

    }


    private void TransferringDataInUserLoyaltyLevelFromUserLoyaltyLevelDTO(UserLoyaltyLevelDTO userLoyaltyLevelDTO,
                                                                           UserLoyaltyLevel userLoyaltyLevel) {
        userLoyaltyLevel.setCode(userLoyaltyLevelDTO.getCode());
        userLoyaltyLevel.setIsActive(userLoyaltyLevelDTO.getIsActive());
        userLoyaltyLevel.setCode(userLoyaltyLevelDTO.getCode());
        userLoyaltyLevel.setTitle(userLoyaltyLevelDTO.getTitle());
        List<Privilege> privileges = new ArrayList<>();
        for (int i : userLoyaltyLevelDTO.getPrivilegesID()) {
            privileges.add(privilegeRepository.getPrivilegeById(i));
        }
        userLoyaltyLevel.setPrivileges(privileges);

    }

    private UserLoyaltyLevelDTO TransferringDataInUserLoyaltyLevelDTOFromUserLoyaltyLevel(UserLoyaltyLevel userLoyaltyLevel) {
        UserLoyaltyLevelDTO userLoyaltyLevelDTO = new UserLoyaltyLevelDTO();
        userLoyaltyLevelDTO.setId(userLoyaltyLevel.getId());
        userLoyaltyLevelDTO.setCode(userLoyaltyLevel.getCode());
        userLoyaltyLevelDTO.setTitle(userLoyaltyLevel.getTitle());
        userLoyaltyLevelDTO.setIsActive(userLoyaltyLevel.getIsActive());
        List<Integer> listIdPrivileges = new ArrayList<>();
        for (Privilege i : userLoyaltyLevel.getPrivileges()) {
            listIdPrivileges.add(i.getId());
        }
        userLoyaltyLevelDTO.setPrivilegesID(listIdPrivileges);
        return userLoyaltyLevelDTO;
    }

}
