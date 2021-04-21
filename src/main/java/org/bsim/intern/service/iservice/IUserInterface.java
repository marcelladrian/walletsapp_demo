package org.bsim.intern.service.iservice;

import org.bsim.intern.shared.dto.UserDTO;

import java.util.List;

public interface IUserInterface {
    //get all users
    List<UserDTO> getListUser();

    //get single value by username
    UserDTO getUserByUsername(String username);

    //add new user
    UserDTO addNewData(UserDTO user);

}
