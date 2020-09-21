package com.picatsu.financestock.service;


import com.picatsu.financestock.model.TickerModel;
import com.picatsu.financestock.util.CustomFunctions;
import lombok.Getter;
import org.patriques.AlphaVantageConnector;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.IntraDay;
import org.patriques.output.timeseries.data.StockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class StockService {



    private org.patriques.TimeSeries stockTimeSeries;


    public StockService() {
        String apiKey = "63NJUA45A97BF6OI";
        int timeout = 3000;
        stockTimeSeries = new org.patriques.TimeSeries(new AlphaVantageConnector(apiKey, timeout));

    }

    public List<StockData> getTS(String code ) {

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

    public List<TickerModel> findTickers(String str) throws IOException {

        List<TickerModel> values = new ArrayList<>();
        for (TickerModel entry : CustomFunctions.loadData() ) {
            if(( (entry.getCode() + entry.getName() ).toLowerCase()).contains(str.toLowerCase()) ) {
                values.add (entry);
            }
        }

        return values ;
    }
}
