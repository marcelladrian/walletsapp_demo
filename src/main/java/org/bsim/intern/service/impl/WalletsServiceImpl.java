package org.bsim.intern.service.impl;

import org.bsim.intern.io.entity.UserEntity;
import org.bsim.intern.io.entity.WalletsEntity;
import org.bsim.intern.io.irepository.UserRepository;
import org.bsim.intern.io.irepository.WalletsRepository;
import org.bsim.intern.service.iservice.IWalletsService;
import org.bsim.intern.shared.dto.UserDTO;
import org.bsim.intern.shared.dto.WalletsBalanceDTO;
import org.bsim.intern.shared.dto.WalletsDTO;
import org.bsim.intern.shared.utils.GenerateRandomPublicId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletsServiceImpl implements IWalletsService {

    @Autowired
    WalletsRepository walletsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GenerateRandomPublicId generateRandomPublicId;
    //get all wallet data
    @Override
    public List<WalletsDTO> getAllWalletsData(String userid) {
        ModelMapper mapper = new ModelMapper();

        List<WalletsDTO> value = new ArrayList<>();

        //Get User
        UserEntity userEntity = userRepository.findByUserId(userid);

        List<WalletsEntity> walletsData = walletsRepository.findAllByUser(userEntity);

        for (WalletsEntity walletsEntity : walletsData){
            value.add(mapper.map(walletsEntity, WalletsDTO.class));
        }
        return value;
    }
    //get balance
    @Override
    public WalletsBalanceDTO getTotalBalance(String userid) {
        ModelMapper mapper = new ModelMapper();
        //user entity
        UserEntity userEntity = userRepository.findByUserId(userid);
        List<WalletsEntity> wallets = walletsRepository.findAllByUser(userEntity);

        long total = 0;
        for (WalletsEntity walletsEntity : wallets){
            total = total + walletsEntity.getBalance();
        }
        WalletsBalanceDTO value = new WalletsBalanceDTO();
        value.setBalance(total);
        value.setName(userEntity.getUserName());

        return value;
    }


//    //Alternatif get total balance
//    @Override
//    public long getTotalBalance2(String userid) {
//        List<WalletsEntity> walletsData = walletsRepository.findAllByUser(userRepository.findByUserid(userid));
//        if (walletsData == null)
//            return 0L;
//
//        long totalBalance = 0;
//        for(WalletsEntity walletsEntity: walletsData){
//            totalBalance = totalBalance + walletsEntity.getBalance();
//        }
//        return totalBalance;
//    }
    //add new wallet data (post)

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

    @Override
    public WalletsDTO updateWalletData(String userid, String walletid, WalletsDTO walletsDTO) {

        WalletsEntity walletData = walletsRepository.findByWalletid(walletid);
        if (walletData == null)
            return null;
//        if(walletData == null || !userid.equals(walletData.getUser().getUserId()))
//            return null;

        //update nohp (or) balance
        walletData.setNohp(walletsDTO.getNoHP());
        walletData.setBalance(walletsDTO.getBalance());

        WalletsEntity updateData = walletsRepository.save(walletData);

        return new ModelMapper().map(updateData, WalletsDTO.class);
    }

}
