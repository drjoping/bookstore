package ae.smartdubai.assessment.bookstore.rest;

import ae.smartdubai.assessment.bookstore.models.entities.Book;
import ae.smartdubai.assessment.bookstore.repo.BookRepo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static ae.smartdubai.assessment.bookstore.rest.OrderControllerTest.jsonToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = "app.load-mock-data=true")
@AutoConfigureMockMvc
class BookCRUDTest {
	@Autowired
	private BookRepo bookRepo;

	@LocalServerPort
	private String port;
	private String mockEndpoint;
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockEndpoint = "http://localhost:" + port + "/api/data";
	}

	@SneakyThrows
	public ResultActions mockPost(String endpoint, Object o, Object... uriVars) {
		return mockMvc.perform(post(mockEndpoint + endpoint, uriVars)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(o)));
	}

	@SneakyThrows
	public ResultActions mockPatch(String endpoint, Object o, Object... uriVars) {
		return mockMvc.perform(patch(mockEndpoint + endpoint, uriVars)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(o)));
	}

	@Test
	void create() throws Exception {
		Book book = Book.builder()
				.name("Some book")
				.description("Some book bla bla")
				.price(789)
				.isbn("123-456-789-1")
				.build();
		mockPost("/books", book).andExpect(status().isCreated());
		Assertions.assertTrue(bookRepo.findFirstByBookIdOrNameIgnoreCaseOrIsbnIgnoreCase(null, null, "123-456-789-1").isPresent());
	}

	@Test
	void update() throws Exception {
		Book book = Book.builder()
				.name("Some book")
				.description("Some book bla bla")
				.price(789)
				.isbn("123-456-789-2")
				.build();
		mockPost("/books", book).andExpect(status().isCreated());
		Book book1 = bookRepo.findFirstByBookIdOrNameIgnoreCaseOrIsbnIgnoreCase(null, null, "123-456-789-2").get();
		mockPatch("/books/{id}", Collections.singletonMap("name", "New Name"), book1.getBookId())
				.andExpect(status().isOk());
		Assertions.assertEquals("New Name", bookRepo.findFirstByBookIdOrNameIgnoreCaseOrIsbnIgnoreCase(null, null, "123-456-789-2").get().getName());
	}

	@Test
	void get() throws Exception {
		Book book = Book.builder()
				.name("Some book")
				.description("Some book bla bla")
				.price(789)
				.isbn("123-456-789-3")
				.build();
		mockPost("/books", book).andExpect(status().isCreated());
		Book book1 = bookRepo.findFirstByBookIdOrNameIgnoreCaseOrIsbnIgnoreCase(null, null, "123-456-789-3").get();
		mockMvc.perform(MockMvcRequestBuilders.get(mockEndpoint + "/books/{id}", book1.getBookId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("name").value("Some book"));
	}

	@Test
	void delete() throws Exception {
		Book book = Book.builder()
				.name("Some book")
				.description("Some book bla bla")
				.price(789)
				.isbn("123-456-789-4")
				.build();
		mockPost("/books", book).andExpect(status().isCreated());
		Book book1 = bookRepo.findFirstByBookIdOrNameIgnoreCaseOrIsbnIgnoreCase(null, null, "123-456-789-4").get();
		mockMvc.perform(MockMvcRequestBuilders.delete(mockEndpoint + "/books/{id}", book1.getBookId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		Assertions.assertFalse(bookRepo.findFirstByBookIdOrNameIgnoreCaseOrIsbnIgnoreCase(null, null, "123-456-789-4").isPresent());
	}
}