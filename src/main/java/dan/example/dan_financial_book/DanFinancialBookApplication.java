package dan.example.dan_financial_book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "dan.example.dan_financial_book")
@MapperScan(basePackages = "dan.example.dan_financial_book")
public class DanFinancialBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(DanFinancialBookApplication.class, args);
    }
}
