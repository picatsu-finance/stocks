package com.picatsu.financestock.repository;

import com.picatsu.financestock.model.TickerModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TickerRepository extends MongoRepository<TickerModel, String> {

    List<TickerModel> findByCodeContainsIgnoreCase(String val);
    List<TickerModel> findByNameContainsIgnoreCase(String val);
    long deleteAllByCode(String code);
}
