package xyz.keriteal.sosbk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SosBkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SosBkApplication.class, args);
    }

}
