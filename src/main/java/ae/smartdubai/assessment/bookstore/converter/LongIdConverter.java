package ae.smartdubai.assessment.bookstore.converter;

import ae.smartdubai.assessment.bookstore.models.entities.Author;
import ae.smartdubai.assessment.bookstore.models.entities.Book;
import ae.smartdubai.assessment.bookstore.models.entities.BookType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class LongIdConverter implements BackendIdConverter {

	private final Map<Class, Class> entities = new HashMap<>() {{
		put(Author.class, String.class);
		put(Book.class, String.class);
		put(BookType.class, String.class);
	}};

	@Override
	public Serializable fromRequestId(String id, Class<?> entityType) {
		if (id == null) return null;
		return Long.valueOf(id);
	}

	@Override
	public String toRequestId(Serializable id, Class<?> entityType) {
		return id.toString();
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return entities.containsKey(aClass);
	}
}