package com.picatsu.financestock.web;

import com.picatsu.financestock.model.TickerModel;
import com.picatsu.financestock.repository.TickerRepository;
import com.picatsu.financestock.util.CustomFunctions;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping(value = "/api/v1/tickers")
@RestController("TickersController")
@Slf4j
@CrossOrigin
public class TickersController {

    @Autowired
    private TickerRepository tickerRepository;

    @Autowired
    private CustomFunctions customFunctions;

    @GetMapping(value = "/")
    @Operation(summary = "get all tickers/stock without paginated")
    public List<TickerModel> getCompagnyList(HttpServletRequest request)  {

        customFunctions.displayStackTraceIP("/api/v1/tickers/", request);
        return tickerRepository.findAll();
    }

    @GetMapping(value = "/reload")
    @Operation(summary = "reload tickers list from db")
    public int Load(HttpServletRequest request) throws IOException {

        customFunctions.displayStackTraceIP("/api/v1/tickers/reload", request);
        customFunctions.loadData();
        return this.tickerRepository.findAll().size();
    }

    @GetMapping(value = "/paginate")
    @Operation(summary = "get all tickers from db paginated")
    public Page<TickerModel> populate(@RequestParam int page, @RequestParam int size, HttpServletRequest request)  {

        customFunctions.displayStackTraceIP("/api/v1/tickers/paginate", request);
        return  this.tickerRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/search-tickers/{str}")
    @Operation(summary = "search all tickers with specific character")
    public List<TickerModel> findCode(@PathVariable String str, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/tickers/search-tickers/{str}", request);
        return Stream.concat(this.tickerRepository.findByCodeContainsIgnoreCase(str).stream(),
                this.tickerRepository.findByNameContainsIgnoreCase(str).stream()
        ).collect(Collectors.toList());
    }

    @PostMapping(value = "/create")
    @Operation(summary = "add ticker to db")
    public TickerModel addTickers(@RequestBody TickerModel ticker, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/tickers/create", request);
        return this.tickerRepository.insert(ticker);
    }

    @DeleteMapping(value= "/{ticker-code}/delete")
    @Operation(summary = "delete ticker from db")
    public Boolean deleteTicker(@PathVariable(value= "ticker-code") String code, HttpServletRequest request) {

        customFunctions.displayStackTraceIP("/api/v1/tickers/{ticker-code}/delete", request);
        try {
            tickerRepository.deleteById(code);
            return tickerRepository.existsById(code);
        } catch (Exception e) {
            return null;
        }
    }


}
