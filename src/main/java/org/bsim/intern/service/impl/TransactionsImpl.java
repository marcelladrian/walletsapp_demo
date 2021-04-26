package org.bsim.intern.service.impl;

import org.bsim.intern.io.entity.TransactionsEntity;
import org.bsim.intern.io.entity.WalletsEntity;
import org.bsim.intern.io.irepository.TransactionsRepository;
import org.bsim.intern.io.irepository.WalletsRepository;
import org.bsim.intern.service.iservice.IServiceTransactions;
import org.bsim.intern.shared.dto.TransactionsDTO;
import org.bsim.intern.shared.utils.GenerateRandomPublicId;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionsImpl implements IServiceTransactions {

    private final TransactionsRepository transactionsRepository;
    private final WalletsRepository walletsRepository;
    private final GenerateRandomPublicId generateRandomPublicId;

    public TransactionsImpl(TransactionsRepository transactionsRepository, WalletsRepository walletsRepository, GenerateRandomPublicId generateRandomPublicId) {
        this.transactionsRepository = transactionsRepository;
        this.walletsRepository = walletsRepository;
        this.generateRandomPublicId = generateRandomPublicId;
    }

    @Override
    public List<TransactionsDTO> getAllTransactions() {
        ModelMapper modelMapper = new ModelMapper();
        //wadah untuk mapping dari entity -> dto
        List<TransactionsDTO> returnValue = new ArrayList<>();

        List<TransactionsEntity> transactionsEntities = transactionsRepository.findAll();

        for (TransactionsEntity entity : transactionsEntities){
            returnValue.add(modelMapper.map(entity, TransactionsDTO.class));
        }

        return returnValue;
    }

    @Override
    public TransactionsDTO addNewTransactions(String walletId, TransactionsDTO transactionsDTO) {
        ModelMapper modelMapper = new ModelMapper();

        WalletsEntity walletsEntity = walletsRepository.findByWalletid(walletId);

        TransactionsEntity entity = modelMapper.map(transactionsDTO, TransactionsEntity.class);
        entity.setWalletsEntity(walletsEntity);
        entity.setTransactionsId(generateRandomPublicId.generateUserId(35));

        TransactionsEntity storedValue = transactionsRepository.save(entity);

        TransactionsDTO returnValue = modelMapper.map(storedValue, TransactionsDTO.class);

        return returnValue;
    }
    //get transaction by wallet id
    @Override
    public List<TransactionsDTO> getAllTransactionsByWalletId(String walletId) {
        ModelMapper modelMapper = new ModelMapper();
        WalletsEntity walletsEntity = walletsRepository.findByWalletid(walletId);

        List<TransactionsDTO> returnValue = new ArrayList<>();
        List<TransactionsEntity> transactionsEntities = transactionsRepository.findAllByWalletsEntity(walletsEntity);

        for (TransactionsEntity entity : transactionsEntities){
            returnValue.add(modelMapper.map(entity, TransactionsDTO.class));
        }

        return returnValue;
    }

    @Override
    public TransactionsDTO updateTransactionsByTransactionsId(String walletId, String transactionsId, TransactionsDTO transactionsDTO) {
        ModelMapper modelMapper = new ModelMapper();
        long balanceInit = 0;
        long amountInit = 0;
        long amountUpdate = transactionsDTO.getAmount();
        WalletsEntity walletsEntity = walletsRepository.findByWalletid(walletId);
        balanceInit = walletsEntity.getBalance();

        TransactionsEntity transactionsEntity = transactionsRepository.findByTransactionsId(transactionsId);
        amountInit = transactionsEntity.getAmount();

        TransactionsEntity entity = modelMapper.map(transactionsDTO, TransactionsEntity.class);

        entity.setWalletsEntity(walletsEntity);
        entity.setId(transactionsEntity.getId());
        entity.setTransactionsId(transactionsEntity.getTransactionsId());

        if (amountInit - amountUpdate > 0){
            walletsEntity.setBalance(balanceInit + (amountUpdate-amountInit));
        }else{
            walletsEntity.setBalance(balanceInit - (amountUpdate-amountInit));
        }

        TransactionsEntity storedValue = transactionsRepository.save(entity);

        return modelMapper.map(storedValue, TransactionsDTO.class);
    }

    @Override
    public TransactionsDTO deleteTransactions(String walletId, String transactionsId) {
        WalletsEntity walletsEntity = walletsRepository.findByWalletid(walletId);
        TransactionsEntity transactionsEntity = transactionsRepository.findByTransactionsId(transactionsId);
        transactionsEntity.setWalletsEntity(walletsEntity);
        transactionsEntity.setDeleted(true);

        TransactionsEntity storedValue = transactionsRepository.save(transactionsEntity);

        ModelMapper modelMapper = new ModelMapper();

        return  modelMapper.map(storedValue, TransactionsDTO.class);
    }


}
