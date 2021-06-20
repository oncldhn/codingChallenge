package com.n26.codingchallange.service;

import com.n26.codingchallange.entity.model.Transaction;
import com.n26.codingchallange.entity.TransactionStore;
import com.n26.codingchallange.exception.GenericCodingChallangeException;
import com.n26.codingchallange.exception.TransactionDateIsInFutureException;
import com.n26.codingchallange.exception.TransactionExpiredException;
import com.n26.codingchallange.exception.JSONParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionStore transactionStore;

    @Autowired
    public  TransactionServiceImpl (TransactionStore transactionStore) {
        this.transactionStore=transactionStore;
    }

    @Override
    public void executeTransaction(String amount, String time) throws GenericCodingChallangeException {
        BigDecimal transactionAmount = convertToAmount (amount);
        Instant transactionTime = convertTime(time);
        Instant currentTime = Instant.now();

        validateTransactionTime(transactionTime,currentTime);

        transactionStore.addTransaction(new Transaction(transactionAmount,transactionTime),currentTime);
    }


    private void validateTransactionTime(Instant transactionTime,Instant currentTime) throws GenericCodingChallangeException {
        //check if the transaction time is valid
        if(currentTime.minusSeconds(60L).isAfter(transactionTime)){
            throw new TransactionExpiredException();
        }

        if(currentTime.isBefore(transactionTime)){
            throw new TransactionDateIsInFutureException();
        }
    }


    private Instant convertTime(String time) throws GenericCodingChallangeException {
        try{
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(ZoneId.of("UTC"));
            return Instant.from(formatter.parse(time));
        }catch (DateTimeException dte) {
            throw new JSONParseException();
        }
    }

    private BigDecimal convertToAmount(String amount) throws GenericCodingChallangeException {
        try{
            return new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP);
        }catch (NumberFormatException nfe) {
            throw new JSONParseException();
        }
    }

    @Override
    public void deleteTransactions() {
        transactionStore.deleteTransactions();
    }
}
