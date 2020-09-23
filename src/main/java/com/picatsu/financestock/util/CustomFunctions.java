package com.picatsu.financestock.util;

import com.picatsu.financestock.model.TickerModel;
import com.picatsu.financestock.repository.TickerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@Component
@AllArgsConstructor
public class CustomFunctions {

    @Autowired
    private  TickerRepository tickerRepository;

    public  List<TickerModel> loadData() throws IOException {

        List<TickerModel> liste = new ArrayList<>();
        String[] fileNames = new String[]{"listing_status.csv", "wilshire_5000_stocks.csv"};

        String myLine = "";
        BufferedReader reader = null;

        for(String name : fileNames) {

            File file = new ClassPathResource(name).getFile();
            try {
                reader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            while ((myLine = reader.readLine()) != null ) {
                addToArray(liste, myLine);
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public void displayStackTraceIP(String path, HttpServletRequest request ) {
        String ipAddress = request.getHeader("X-Forward-For");
        if(ipAddress== null){
            ipAddress = request.getRemoteAddr();
        }
        log.info("path = " + path + " IP : " +  ipAddress);
    }

    private void addToArray(List<TickerModel> liste, String myLine) {
        String[] infos = myLine.split(",");
        if (infos.length > 1) {
            TickerModel res = new TickerModel(infos[0], infos[1]);
          /*  if(!liste.contains(res)) {
                liste.add(res);
            }*/
            if(myLine.toLowerCase().contains("tesla")) {
                System.out.println(myLine);
            }
            if (!tickerRepository.existsById(res.getCode())  ) {

                tickerRepository.insert(res);
            }

        }
    }
}
