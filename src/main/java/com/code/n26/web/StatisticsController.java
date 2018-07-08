package com.code.n26.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.code.n26.model.Statistics;
import com.code.n26.model.Transaction;
import com.code.n26.service.StatisticsService;

@RestController
public class StatisticsController {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

	@Autowired
	private StatisticsService statisticsService;

	@GetMapping("/statistics")
	public ResponseEntity<Statistics> getStatistics() {

		logger.info("GET method invoked");
		
		Statistics statistics = statisticsService.getStatisticsForLast60Seconds();

		return new ResponseEntity<Statistics>(statistics, HttpStatus.OK);
	}

	@PostMapping("/transactions")
	public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {

		logger.info("POST method invoked");

		boolean isTransactionOlderThanSixtySeconds = statisticsService.addTransaction(transaction);

		if (isTransactionOlderThanSixtySeconds) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}

	}

}
