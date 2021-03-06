package com.picatsu.financestock.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.picatsu.financestock.service.StockService;
import com.picatsu.financestock.util.CustomFunctions;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api/v1/yahoo")
@RestController("YahooStocksController")
@Slf4j
@CrossOrigin
public class YahooController {

    @Autowired
    private CustomFunctions customFunctions;
    @Autowired
    private StockService stockService;

    @GetMapping(value = "/stock/{code}")
    @Operation(summary = "get stock by code")
    public BigDecimal yahooGetByCode(@PathVariable String code, HttpServletRequest request) throws IOException {

        customFunctions.displayStackTraceIP("/api/v1/yahoo/stock/{code}", request);
        return YahooFinance.get(code).getQuote().getPrice();
    }

    @GetMapping(value = "/history/{code}")
    @Operation(summary = "get stock by code")
    public JsonNode yahooHistoryByCode(@PathVariable String code, HttpServletRequest request) throws IOException {

        customFunctions.displayStackTraceIP("/api/v1/yahoo/stock/history/{code}", request);
        return this.stockService.getStockHistory(code);
    }

    @GetMapping(value = "/stock/all-infos/{code}")
    @Operation(summary = "get all infos for a stock by code")
    public Stock yahooGetByCodeAll(@PathVariable String code, HttpServletRequest request) throws IOException {

        customFunctions.displayStackTraceIP("/api/v1/yahoo/stock/all-infos/{code}", request);
        return YahooFinance.get(code) ;
    }

    @RequestMapping(value = "/stock/multiple", method = RequestMethod.POST )
    public Map<String, Stock> yahooGetByCodeMultiple(@RequestBody List<String> code,
                                                     HttpServletRequest request) throws IOException {

        customFunctions.displayStackTraceIP("/api/v1/yahoo/stock/multiple", request);
        return YahooFinance.get(code.toArray(new String[0]));
    }

    @GetMapping(value = "/fx-quote")
    @Operation(summary = "get specific quote")
    public List<ImmutablePair<String, FxQuote>> yahooGetFx( HttpServletRequest request) throws IOException {

        customFunctions.displayStackTraceIP("/api/v1/yahoo/fx-quote", request);
        List<ImmutablePair<String, FxQuote>> res = new ArrayList<>();
        res.add(new ImmutablePair<>("usd->eur", YahooFinance.getFx(FxSymbols.USDEUR)));
        res.add(new ImmutablePair<>("usd->gbp", YahooFinance.getFx("USDGBP=X")));
        return res;

    }
}
