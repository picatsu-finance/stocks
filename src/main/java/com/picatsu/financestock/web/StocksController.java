package com.picatsu.financestock.web;


import com.picatsu.financestock.model.TickerModel;
import com.picatsu.financestock.service.StockService;
import com.picatsu.financestock.util.CustomFunctions;
import lombok.extern.slf4j.Slf4j;
import org.patriques.output.timeseries.data.StockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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


    @GetMapping(value = "/search-ticker/{str}")
    public List<TickerModel> findCode(@PathVariable String str) throws IOException {
        return stocksService.findTickers(str);
    }

    @GetMapping(value = "/tickers")
    public List<TickerModel> LoadCompagnyList() throws IOException {
        return CustomFunctions.loadData();
    }
}

