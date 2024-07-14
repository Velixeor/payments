package com.example.payments.Service;

import com.example.payments.dto.UserDTO;
import com.example.payments.entity.User;
import com.example.payments.repository.UserLoyaltyLevelRepository;
import com.example.payments.repository.UserRepository;
import com.example.payments.repository.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserStatusRepository userStatusRepository;
    @Autowired
    private final UserLoyaltyLevelRepository userLoyaltyLevelRepository;

    public UserService(UserRepository userRepository, UserStatusRepository userStatusRepository, UserLoyaltyLevelRepository userLoyaltyLevelRepository) {
        this.userRepository = userRepository;
        this.userStatusRepository = userStatusRepository;
        this.userLoyaltyLevelRepository = userLoyaltyLevelRepository;
    }

    public Boolean createUser(UserDTO userDTO) {
        User user = new User();
        if (!userRepository.existsUserByLoginAndAndMailAndNumberPhone(userDTO.getLogin(), userDTO.getNumberPhone(), userDTO.getMail())) {
            TransferringDataInUserFromUserDTO(userDTO,user);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public Boolean updateUser(UserDTO userDTO) {
        User user=userRepository.getUserById(userDTO.getId());
        List<User> userList=userRepository.getUsersByLoginOrMailOrNumberPhone(userDTO.getLogin(), userDTO.getMail(), userDTO.getNumberPhone());
        for(User u:userList){
            if(!user.getId().equals(u.getId()))
                return false;
        }
        TransferringDataInUserFromUserDTO(userDTO,user);
        userRepository.save(user);
        return true;
    }

    public Boolean deleteUser(Integer idUser) {
        User user=userRepository.getUserById(idUser);
        if(!user.getIsStaff()) {
            userRepository.delete(user);
            return true;
        }
        else{
            return false;
        }

    }

    public UserDTO getUser(Integer idUser) {
        User user = userRepository.getUserById(idUser);
        UserDTO userDTO = new UserDTO(user.getId(), user.getLogin(), user.getPassword(), user.getFirstName(),
                user.getMiddleName(), user.getLastName(), user.getMail(), user.getNumberPhone(),
                user.getIsStaff(), user.getDateCreate(), user.getUserStatus().getId(), user.getUserLoyaltyLevel().getId());
        return  userDTO;

    }
    private void TransferringDataInUserFromUserDTO(UserDTO userDTO,User user){
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
        user.setUserStatus(userStatusRepository.getUserStatusById(userDTO.getUserStatusId()));

    }


}
