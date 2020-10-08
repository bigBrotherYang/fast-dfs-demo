package com.yangml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yangml.dao")
public class FastDfsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastDfsDemoApplication.class, args);
    }

}
