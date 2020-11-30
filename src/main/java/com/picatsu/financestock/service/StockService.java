package com.picatsu.financestock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.data.StockData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class StockService {

    private static final String BASE_URL_YAHOO = "https://query1.finance.yahoo.com/v8/finance/chart/";
    @Autowired
    private org.patriques.TimeSeries stockTimeSeries;


    public JsonNode getStockHistory(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = BASE_URL_YAHOO+code+"?interval=1d";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return root;
    }

    public List<StockData> getTS(String code) {

        try {
            IntraDay response = stockTimeSeries
                    .intraDay(code,
                            Interval.ONE_MIN,
                            OutputSize.COMPACT);
            Map<String, String> metaData = response.getMetaData();
            System.out.println("Information: " + metaData.get("1. Information"));
            System.out.println("Stock: " + metaData.get("2. Symbol"));

            List<StockData> stockData = response.getStockData();
            return stockData;

        } catch (AlphaVantageException e) {
            System.out.println("something went wrong");
        }
        return null;
    }


}
