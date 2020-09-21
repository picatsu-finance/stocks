package com.picatsu.financestock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceStockApplication {
	// http://localhost:8003/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
	public static void main(String[] args) {
		SpringApplication.run(FinanceStockApplication.class, args);
	}

}
