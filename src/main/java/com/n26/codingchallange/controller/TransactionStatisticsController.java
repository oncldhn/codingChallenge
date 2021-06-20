package com.n26.codingchallange.controller;

import com.n26.codingchallange.entity.model.TransactionStatistics;
import com.n26.codingchallange.service.TransactionStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TransactionStatisticsController {

    private final TransactionStatisticsService statisticsService;

    @Autowired
    public TransactionStatisticsController(TransactionStatisticsService statisticsService) {
        this.statisticsService =statisticsService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<TransactionStatistics> getStatistics() {
        return new ResponseEntity<>(statisticsService.getStatistics(),HttpStatus.OK);
    }
}
