package xyz.keriteal.sosapi;

import com.blinkfox.fenix.EnableFenix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import xyz.keriteal.sosapi.config.OssProperties;
import xyz.keriteal.sosapi.config.ProfileProperties;

/**
 * @author Kerit
 */
@SpringBootApplication
@EnableConfigurationProperties({ProfileProperties.class, OssProperties.class})
@EnableFenix
public class SosBkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SosBkApplication.class, args);
    }
}
