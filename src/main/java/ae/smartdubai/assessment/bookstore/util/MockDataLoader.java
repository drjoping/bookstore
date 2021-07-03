package ae.smartdubai.assessment.bookstore.util;

import ae.smartdubai.assessment.bookstore.models.entities.Author;
import ae.smartdubai.assessment.bookstore.models.entities.Book;
import ae.smartdubai.assessment.bookstore.models.entities.BookType;
import ae.smartdubai.assessment.bookstore.models.entities.Promotion;
import ae.smartdubai.assessment.bookstore.repo.AuthorRepo;
import ae.smartdubai.assessment.bookstore.repo.BookRepo;
import ae.smartdubai.assessment.bookstore.repo.BookTypeRepo;
import ae.smartdubai.assessment.bookstore.repo.PromotionRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

// Only to load mock/sample data
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.load-mock-data", havingValue = "true")
@Slf4j
public class MockDataLoader {
	private final AuthorRepo authorRepo;
	private final BookRepo bookRepo;
	private final BookTypeRepo bookTypeRepo;
	private final PromotionRepo promotionRepo;

	@PostConstruct
	public void loadData() {
		createBookTypes();
		createAuthors();
		createBooks();
		createPromotions();
	}

	private void createBookTypes() {
		createIfNotPresent(BookType.builder().name("Fiction").description("Fiction books").discountRate(10).build());
		createIfNotPresent(BookType.builder().name("Comic").description("Comic books").discountRate(0).build());
	}

	private void createIfNotPresent(BookType bookType) {
		bookTypeRepo.findFirstByName(bookType.getName())
				.ifPresentOrElse(b -> log.info("BookType {} exists", b.getName()), () -> {
					bookTypeRepo.save(bookType);
					log.info("Created BookType {}", bookType);
				});
	}

	private void createAuthors() {
		createIfNotPresent(Author.builder().firstName("William").lastName("Shakespeare").build());
		createIfNotPresent(Author.builder().firstName("Salman").lastName("Rushdie").build());
		createIfNotPresent(Author.builder().firstName("Chris").lastName("Claremont").build());
	}

	private void createIfNotPresent(Author author) {
		authorRepo.findFirstByFirstNameAndLastName(author.getFirstName(), author.getLastName())
				.ifPresentOrElse(a -> log.info("Author {} {} exists", a.getFirstName(), a.getLastName()), () -> {
					authorRepo.save(author);
					log.info("Created Author {}", author);
				});
	}

	private void createBooks() {
		createIfNotPresent(Book.builder().name("Hamlet").description("Hamlet by Shakespeare")
				.author(authorRepo.findFirstByFirstNameAndLastName("William", "Shakespeare").orElse(null))
				.type(bookTypeRepo.findFirstByName("Fiction").orElse(null)).isbn("0140434585").price(1000).build());
		createIfNotPresent(Book.builder().name("Midnight's Children").description("Midnight's Children by Salman Rushdie")
				.author(authorRepo.findFirstByFirstNameAndLastName("Salman", "Rushdie").orElse(null))
				.type(bookTypeRepo.findFirstByName("Fiction").orElse(null)).isbn("0-224-01823-X").price(2500).build());
		createIfNotPresent(Book.builder().name("X-Men").description("X-Men by Chris Claremont")
				.author(authorRepo.findFirstByFirstNameAndLastName("Chris", "Claremont").orElse(null))
				.type(bookTypeRepo.findFirstByName("Comic").orElse(null)).isbn("0-7851-1560-9").price(555).build());
	}

	private void createIfNotPresent(Book book) {
		bookRepo.findFirstByName(book.getName())
				.ifPresentOrElse(a -> log.info("Book {} exists", a.getName()), () -> {
					bookRepo.save(book);
					log.info("Created Book {}", book);
				});
	}

	private void createPromotions() {
		promotionRepo.save(Promotion.builder().code("ABCD1234").build());
		promotionRepo.save(Promotion.builder().code("XYZ1234").build());
	}
}