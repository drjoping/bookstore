package ae.smartdubai.assessment.bookstore.rest;

import ae.smartdubai.assessment.bookstore.models.dto.BookDTO;
import ae.smartdubai.assessment.bookstore.models.dto.CheckoutDTO;
import ae.smartdubai.assessment.bookstore.models.entities.Book;
import ae.smartdubai.assessment.bookstore.repo.BookRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = "app.load-mock-data=true")
@AutoConfigureMockMvc
class OrderControllerTest {
	@LocalServerPort
	private String port;
	private String mockEndpoint;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private BookRepo bookRepo;
	Optional<Book> sampleBook;

	@BeforeEach
	void setUp() {
		mockEndpoint = "http://localhost:" + port + "/api";
		sampleBook = bookRepo.findFirstByName("X-Men");
	}

	@SneakyThrows
	public static String jsonToString(Object o) {
		return new ObjectMapper().writeValueAsString(o);
	}

	@Test
	void checkout1Book() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().name("hamlet").build()
				)).build();
		mockMvc.perform(post(mockEndpoint + "/order/checkout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(checkoutDTO)))
				.andExpect(jsonPath("$.orderStatus").value("SUCCESS"))
				.andExpect(jsonPath("$.totalAmount").value(1000));
	}

	@Test
	void checkoutMultipleBooks() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().isbn("0140434585").build(),
						BookDTO.builder().name("Midnight's Children").build(),
						BookDTO.builder().bookId(sampleBook.get().getBookId()).build()
				)).build();
		mockMvc.perform(post(mockEndpoint + "/order/checkout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(checkoutDTO)))
				.andExpect(jsonPath("$.orderStatus").value("SUCCESS"))
				.andExpect(jsonPath("$.totalAmount").value(4055));
	}

	@Test
	void checkoutMultipleBooksWithPromo() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().isbn("0140434585").build(),
						BookDTO.builder().name("Midnight's Children").build(),
						BookDTO.builder().bookId(sampleBook.get().getBookId()).build()
				)).promoCode("ABCD1234").build();
		mockMvc.perform(post(mockEndpoint + "/order/checkout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(checkoutDTO)))
				.andExpect(jsonPath("$.orderStatus").value("SUCCESS"))
				.andExpect(jsonPath("$.totalAmount").value(3705));
	}

	@Test
	void checkoutInvalidBook() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().isbn("0140434585").build(),
						BookDTO.builder().name("test123").build(),
						BookDTO.builder().bookId(sampleBook.get().getBookId()).build()
				)).promoCode("ABCD1234").build();
		mockMvc.perform(post(mockEndpoint + "/order/checkout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(checkoutDTO)))
				.andExpect(jsonPath("$.orderStatus").value("INVALID_BOOK"));
	}

	@Test
	void checkoutInvalidPromocode() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().isbn("0140434585").build(),
						BookDTO.builder().name("Midnight's Children").build(),
						BookDTO.builder().bookId(sampleBook.get().getBookId()).build()
				)).promoCode("TEST123").build();
		mockMvc.perform(post(mockEndpoint + "/order/checkout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(checkoutDTO)))
				.andExpect(jsonPath("$.orderStatus").value("INVALID_PROMO_CODE"));
	}
}