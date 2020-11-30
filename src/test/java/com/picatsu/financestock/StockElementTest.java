package com.picatsu.financestock;



import com.picatsu.financestock.model.TickerModel;
import com.picatsu.financestock.repository.TickerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockElementTest {

    @Autowired
    private TickerRepository productRepo;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    public void searchProductTest() {
        // définition du comportement de la couche SGBD
        Assertions.assertThat(productRepo.findAll()).hasSize(0);
        TickerModel product1 = productRepo.save(new TickerModel("CODETEST", "CODETEST"));

        // appel de la couche Repository à tester
        Collection<TickerModel> products = productRepo.findByCodeContainsIgnoreCase("CODETEST");

        // vérification de la bonne exécution de la méthode find du MongoTemplate avec les bons paramètres
        Query query = new Query();
        query.addCriteria(Criteria.where("code").regex("CODETEST"));
        Mockito.verify(mongoTemplate, Mockito.times(1)).find(query, TickerModel.class);

        // vérification du résultat de l'execution
        Assertions.assertThat(products).hasSize(1);
        Assertions.assertThat(products).containsExactly(product1);
    }
}
