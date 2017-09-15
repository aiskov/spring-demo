package ee.aiskov.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableAutoConfiguration
public class TestApplication {
    public static void main(String[] args) {
         run(TestApplication.class, args);
    }
}
