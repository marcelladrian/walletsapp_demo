package org.bsim.intern.ui.controller;

import org.bsim.intern.service.iservice.IUserInterface;
import org.bsim.intern.shared.dto.UserDTO;
import org.bsim.intern.ui.model.request.UserRequest;
import org.bsim.intern.ui.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    IUserInterface userService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserResponse> getUsers(){
        return null;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserResponse addNewUser(@RequestBody UserRequest user){
        ModelMapper mapper = new ModelMapper();
        //UserRequest --> UserDTO
        UserDTO userDTO = mapper.map(user, UserDTO.class);
        UserDTO createdUser = userService.addNewData(userDTO);
        //UserDTO --> UserResponse
        UserResponse response = mapper.map(createdUser, UserResponse.class);
        //return
        return response;
    }

}
