package ae.smartdubai.assessment.bookstore.models.views;

import ae.smartdubai.assessment.bookstore.models.entities.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Book.class})
public interface BookView {
	Long getBookId();

	String getName();

	String getDescription();

	@Value("#{target.author?.firstName + ' '+ target.author?.lastName}")
	String getAuthor();

	@Value("#{target.type?.name}")
	String getType();

	double getPrice();

	String getIsbn();
}