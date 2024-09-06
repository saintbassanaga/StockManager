package tech.saintbassanaga.stockmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StockManager {

    public static void main(String[] args) {
        SpringApplication.run(StockManager.class, args);
    }

}

