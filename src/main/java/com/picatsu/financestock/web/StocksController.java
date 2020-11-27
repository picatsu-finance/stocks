package com.picatsu.financestock.web;



import com.picatsu.financestock.service.StockService;
import com.picatsu.financestock.util.CustomFunctions;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.patriques.output.timeseries.data.StockData;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(value = "/api/v1/stocks")
@RestController("StocksController")
@Slf4j
@CrossOrigin
public class StocksController {

    @Autowired
    public StockService stockService;
    @Autowired
    public CustomFunctions customFunctions;

    @GetMapping(value = "/{code}")
    @Operation(summary = "get stock by code")
    public List<StockData> getByCode(@PathVariable String code, HttpServletRequest request){

        customFunctions.displayStackTraceIP("/api/v1/stocks/{code}", request);
        return stockService.getTS(code);
    }




}

