package xyz.keriteal.sosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import xyz.keriteal.sosapi.config.OssProperties;
import xyz.keriteal.sosapi.config.ProfileProperties;

@SpringBootApplication
@EnableConfigurationProperties({ProfileProperties.class, OssProperties.class})
public class SosBkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SosBkApplication.class, args);
    }
}
