package ae.smartdubai.assessment.bookstore;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Bookstore API Definition",version = "v1"))
public class BookStoreApp {
	public static void main(String[] args) {
		SpringApplication.run(BookStoreApp.class, args);
	}
}