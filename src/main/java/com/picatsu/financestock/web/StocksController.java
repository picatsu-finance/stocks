package com.picatsu.financestock.web;



import com.picatsu.financestock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.patriques.output.timeseries.data.StockData;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/stocks")
@RestController("StocksController")
@Slf4j
public class StocksController {

    @Autowired
    public StockService stocksService;


    @GetMapping(value = "/{code}")
    public List<StockData> getByCode(@PathVariable String code){
        log.info("time serie for code "+code);
        return stocksService.getTS(code);
    }




}

