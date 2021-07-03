package ae.smartdubai.assessment.bookstore.repo;

import ae.smartdubai.assessment.bookstore.models.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
@RepositoryRestResource
public interface AuthorRepo extends JpaRepository<Author, Long> {
	Optional<Author> findFirstByFirstNameAndLastName(String firstName, String lastName);
}