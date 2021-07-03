package ae.smartdubai.assessment.bookstore.repo;

import ae.smartdubai.assessment.bookstore.models.entities.Book;
import ae.smartdubai.assessment.bookstore.models.views.BookView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
@RepositoryRestResource(excerptProjection = BookView.class)
public interface BookRepo extends JpaRepository<Book, Long> {
	Optional<Book> findFirstByName(String name);

	Optional<Book> findFirstByBookIdOrNameIgnoreCaseOrIsbnIgnoreCase(Long bookId, String name, String isbn);
}