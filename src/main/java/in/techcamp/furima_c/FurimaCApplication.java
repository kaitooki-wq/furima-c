package in.techcamp.furima_c;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("in.techcamp.furima_c.mapper")
public class FurimaCApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurimaCApplication.class, args);
    }

}
