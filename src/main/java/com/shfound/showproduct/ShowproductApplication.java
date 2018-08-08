package com.shfound.showproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@ServletComponentScan
@EnableJpaAuditing
public class ShowproductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShowproductApplication.class, args);
    }
}
