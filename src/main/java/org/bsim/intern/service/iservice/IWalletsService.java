package org.bsim.intern.service.iservice;

import org.bsim.intern.shared.dto.WalletsDTO;

import java.util.List;

public interface IWalletsService {

    WalletsDTO addNewWalletData(String userid, WalletsDTO walletsDTO);

    List<WalletsDTO> getListWallet (String userid);
}
