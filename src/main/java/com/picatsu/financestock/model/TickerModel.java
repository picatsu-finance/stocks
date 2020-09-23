package com.picatsu.financestock.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@Document(collection = "tickers")
@Builder
public class TickerModel {

    @Id
    private String code;
    private String name;

    public TickerModel(String code, String name) {
        this.code = code;
        this.name = name;
    }
}

