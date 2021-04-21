package org.bsim.intern.service.impl;

import org.bsim.intern.io.entity.UserEntity;
import org.bsim.intern.io.entity.WalletsEntity;
import org.bsim.intern.io.irepository.UserRepository;
import org.bsim.intern.io.irepository.WalletsRepository;
import org.bsim.intern.service.iservice.IWalletsService;
import org.bsim.intern.shared.dto.UserDTO;
import org.bsim.intern.shared.dto.WalletsDTO;
import org.bsim.intern.shared.utils.GenerateRandomPublicId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletsServiceImpl implements IWalletsService {

    @Autowired
    WalletsRepository walletsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GenerateRandomPublicId generateRandomPublicId;

    @Override
    public WalletsDTO addNewWalletData(String userid, WalletsDTO walletsDTO) {
        ModelMapper mapper = new ModelMapper();

        //Generate WalletId
        walletsDTO.setWalletId(generateRandomPublicId.generateUserId(30));
        //Get User
        UserEntity userData = userRepository.findByUserId(userid);
        //Set User
        walletsDTO.setUser(mapper.map(userData, UserDTO.class));

        WalletsEntity entity = mapper.map(walletsDTO, WalletsEntity.class);
        //Save Data to Database (table : walletsbl)
        WalletsEntity storedData = walletsRepository.save(entity);

        return mapper.map(storedData, WalletsDTO.class);
    }

}
