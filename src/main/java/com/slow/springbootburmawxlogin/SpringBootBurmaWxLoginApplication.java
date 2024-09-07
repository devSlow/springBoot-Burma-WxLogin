package com.slow.springbootburmawxlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.slow.springbootburmawxlogin.mapper")
public class SpringBootBurmaWxLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBurmaWxLoginApplication.class, args);
    }

}
