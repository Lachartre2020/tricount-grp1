package com.natixis.tricount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TricountApplication {

    public static void main(String[] args) {
        SpringApplication.run(TricountApplication.class, args);
    }

}
