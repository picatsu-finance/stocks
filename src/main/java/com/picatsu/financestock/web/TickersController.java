package com.picatsu.financestock.web;

import com.picatsu.financestock.model.TickerModel;
import com.picatsu.financestock.repository.TickerRepository;
import com.picatsu.financestock.util.CustomFunctions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping(value = "/api/v1/tickers")
@RestController("TickersController")
@Slf4j
public class TickersController {

    @Autowired
    private TickerRepository tickerRepository;

    @Autowired
    private CustomFunctions customFunctions;

    @GetMapping(value = "/load")
    public int Load() throws IOException {
        customFunctions.loadData() ;
        return this.tickerRepository.findAll().size();
    }

    @GetMapping(value = "/")
    public List<TickerModel> getCompagnyList()  {
        return tickerRepository.findAll();
    }


    @GetMapping(value = "/tickers/paginate")
    public Page<TickerModel> populate(@RequestParam int page, @RequestParam int size )  {

        return  this.tickerRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping(value = "/search-ticker/{str}")
    public List<TickerModel> findCode(@PathVariable String str) {

        return Stream.concat(this.tickerRepository.findByCodeContainsIgnoreCase(str).stream(),
                this.tickerRepository.findByNameContainsIgnoreCase(str).stream()
        ).collect(Collectors.toList());


    }

}
