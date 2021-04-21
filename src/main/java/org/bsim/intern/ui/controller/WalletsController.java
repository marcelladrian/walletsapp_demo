package org.bsim.intern.ui.controller;


import org.bsim.intern.service.iservice.IWalletsService;
import org.bsim.intern.shared.dto.WalletsDTO;
import org.bsim.intern.ui.model.request.WalletRequest;
import org.bsim.intern.ui.model.response.WalletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletsController {

    @Autowired
    IWalletsService walletsService;

    @GetMapping(path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})

    public List<WalletResponse> getAllWallet(@PathVariable String userId){
        List<WalletResponse> value = new ArrayList<>();

        ModelMapper mapper = new ModelMapper();
        List<WalletsDTO> wallets = walletsService.getListWallet(userId);

        for (WalletsDTO walletsDTO : wallets){
            value.add(mapper.map(walletsDTO, WalletResponse.class));
        }
        return value;
    }

    @PostMapping(path = "/{userid}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public WalletResponse addNewWallet(@PathVariable String userid, @RequestBody WalletRequest walletRequest){
        ModelMapper mapper = new ModelMapper();

        WalletsDTO walletsDTO = mapper.map(walletRequest, WalletsDTO.class);
        WalletsDTO createdWallet = walletsService.addNewWalletData(userid, walletsDTO);
        return mapper.map(createdWallet, WalletResponse.class);
    }

}
