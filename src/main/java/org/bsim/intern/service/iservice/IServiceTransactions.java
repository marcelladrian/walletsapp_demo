package org.bsim.intern.service.iservice;

import org.bsim.intern.shared.dto.TransactionsDTO;

import java.util.List;

public interface IServiceTransactions {
    List<TransactionsDTO> getAllTransactions();

    TransactionsDTO addNewTransactions(String walletId, TransactionsDTO transactionsDTO);

    List<TransactionsDTO> getAllTransactionsByWalletId(String walletId);


    TransactionsDTO updateTransactionsByTransactionsId(String walletId, String transactionsId, TransactionsDTO transactionsDTO);

    TransactionsDTO deleteTransactions(String walletId, String transactionsId);
}
