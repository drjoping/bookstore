package ae.smartdubai.assessment.bookstore.config;

import ae.smartdubai.assessment.bookstore.models.entities.Author;
import ae.smartdubai.assessment.bookstore.models.entities.Book;
import ae.smartdubai.assessment.bookstore.models.entities.BookType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@RequiredArgsConstructor
public class RepositoryRestCustomConfiguration implements RepositoryRestConfigurer {
	@SneakyThrows
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(Book.class, BookType.class, Author.class);
	}
}