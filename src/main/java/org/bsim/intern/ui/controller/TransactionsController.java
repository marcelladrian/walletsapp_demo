package org.bsim.intern.ui.controller;

import org.bsim.intern.service.iservice.IServiceTransactions;
import org.bsim.intern.shared.dto.TransactionsDTO;
import org.bsim.intern.ui.model.request.TransactionsRequest;
import org.bsim.intern.ui.model.response.TransactionsResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionsController {

    private final IServiceTransactions iServiceTransactions;

    public TransactionsController(IServiceTransactions iServiceTransactions) {
        this.iServiceTransactions = iServiceTransactions;
    }
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TransactionsResponse> getAllTransactions(){
        ModelMapper modelMapper = new ModelMapper();
        List<TransactionsResponse> returnValue = new ArrayList<>();

        List<TransactionsDTO> transactionsDTO = iServiceTransactions.getAllTransactions();

        for (TransactionsDTO dto : transactionsDTO){
            returnValue.add(modelMapper.map(dto, TransactionsResponse.class));
        }

        return returnValue;
    }

    //add new transaction
    @PostMapping(path = "/{walletId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionsResponse addNewTransactions(@PathVariable String walletId,
                                                   @RequestBody TransactionsRequest transacsactionRequest){
        ModelMapper modelMapper = new ModelMapper();

        TransactionsDTO transactionsDTO = modelMapper.map(transacsactionRequest, TransactionsDTO.class);
        TransactionsDTO storedValue = iServiceTransactions.addNewTransactions(walletId, transactionsDTO);

        TransactionsResponse returnValue = modelMapper.map(storedValue, TransactionsResponse.class);

        return returnValue;
    }

    //get transaction by walletId
    @GetMapping(path = "/{walletId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TransactionsResponse> getAllTransactionsByWalletId(@PathVariable String walletId){
        ModelMapper modelMapper = new ModelMapper();
        List<TransactionsResponse> returnValue = new ArrayList<>();

        List<TransactionsDTO> allTransactions = iServiceTransactions.getAllTransactionsByWalletId(walletId);

        for (TransactionsDTO dto : allTransactions){
            returnValue.add(modelMapper.map(dto, TransactionsResponse.class));
        }

        return returnValue;
    }

    //update
    @PutMapping(path = "/{walletId}/{transactionsId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
                                                          produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionsResponse updateTransactionsByTransactionsId(@PathVariable String walletId,
                                                                   @PathVariable String transactionsId,
                                                                   @RequestBody TransactionsRequest transactionsRequest){
        ModelMapper modelMapper = new ModelMapper();
        TransactionsDTO transactionsDTO = modelMapper.map(transactionsRequest, TransactionsDTO.class);
        TransactionsDTO updateData = iServiceTransactions.updateTransactionsByTransactionsId(walletId, transactionsId, transactionsDTO);

        return modelMapper.map(updateData, TransactionsResponse.class);
    }

    //delete
    @DeleteMapping(path = "/{walletId}/{transactionsId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransactionsResponse deleteTransactions(@PathVariable String walletId, @PathVariable String transactionsId){
        ModelMapper modelMapper = new ModelMapper();

        TransactionsDTO transactionsDTO = iServiceTransactions.deleteTransactions(walletId, transactionsId);

        return modelMapper.map(transactionsDTO, TransactionsResponse.class);
    }


}
