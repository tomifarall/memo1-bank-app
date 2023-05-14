package com.aninfo.service;

import com.aninfo.exceptions.InvalidTransactionTypeException;
import com.aninfo.exceptions.TransactionNotFoundException;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    @Autowired
    public TransactionService(final TransactionRepository transactionRepository,
                              final AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Transaction createTransaction(final Transaction transaction) {
        final TransactionType transactionType = transaction.getTransactionType();

        if (TransactionType.DEPOSIT.equals(transactionType)) {
            final double amountToDeposit = applyPromo(transaction.getAmount());
            accountService.deposit(transaction.getCbu(), amountToDeposit);
        } else if (TransactionType.WITHDRAW.equals(transactionType)) {
            accountService.withdraw(transaction.getCbu(), transaction.getAmount());
        } else {
            throw new InvalidTransactionTypeException("Invalid transaction type.");
        }
        return transactionRepository.save(transaction);
    }

    private double applyPromo(Double sumToDeposit) {
        if (sumToDeposit >= 2000) {
            final double promoAmount = sumToDeposit * 0.1;
            return promoAmount < 500
                    ? sumToDeposit + promoAmount
                    : sumToDeposit + 500;
        }
        return sumToDeposit;
    }

    public Transaction getTransactionById(final Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            return transaction.get();
        }
        throw new TransactionNotFoundException("Transaction not found.");
    }

    public List<Transaction> getAllTransactionByCBU(final Long cbu) {
        return transactionRepository.findAllByCbu(cbu);
    }

    public void deleteById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
