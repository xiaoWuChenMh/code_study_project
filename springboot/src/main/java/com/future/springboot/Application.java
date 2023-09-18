package com.future.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author hma
 * @Date 2023/6/5 11:42
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.future.springboot.mapper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        System.out.println("springBoot项目启动后执行:" + 222);

    }

    @Component
    public class OrderPropertiesCommandLineRunner implements CommandLineRunner {


        @Override
        public void run(String... args) {
            System.out.println("springBoot项目启动前执行:" + 111);
        }

    }

}
