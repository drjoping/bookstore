package ae.smartdubai.assessment.bookstore.service.impl;

import ae.smartdubai.assessment.bookstore.enums.OrderStatus;
import ae.smartdubai.assessment.bookstore.models.dto.BookDTO;
import ae.smartdubai.assessment.bookstore.models.dto.CheckoutDTO;
import ae.smartdubai.assessment.bookstore.models.dto.ReceiptDTO;
import ae.smartdubai.assessment.bookstore.models.entities.Book;
import ae.smartdubai.assessment.bookstore.repo.BookRepo;
import ae.smartdubai.assessment.bookstore.service.CheckoutService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest(properties = "app.load-mock-data=true")
class CheckoutServiceImplTest {
	@Autowired
	CheckoutService checkoutService;
	@Autowired
	private BookRepo bookRepo;
	Optional<Book> sampleBook;

	@BeforeEach
	void setUp() {
		sampleBook = bookRepo.findFirstByName("X-Men");
	}


	@Test
	void checkout1Book() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().name("hamlet").build()
				)).build();
		ReceiptDTO checkout = checkoutService.checkout(checkoutDTO);
		Assertions.assertEquals(OrderStatus.SUCCESS, checkout.getOrderStatus());
		Assertions.assertEquals(1000, checkout.getTotalAmount());
	}

	@Test
	void checkoutMultipleBooks() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().isbn("0140434585").build(),
						BookDTO.builder().name("Midnight's Children").build(),
						BookDTO.builder().bookId(sampleBook.get().getBookId()).build()
				)).build();
		ReceiptDTO checkout = checkoutService.checkout(checkoutDTO);
		Assertions.assertEquals(OrderStatus.SUCCESS, checkout.getOrderStatus());
		Assertions.assertEquals(4055, checkout.getTotalAmount());
	}

	@Test
	void checkoutMultipleBooksWithPromo() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().isbn("0140434585").build(),
						BookDTO.builder().name("Midnight's Children").build(),
						BookDTO.builder().bookId(sampleBook.get().getBookId()).build()
				)).promoCode("ABCD1234").build();
		ReceiptDTO checkout = checkoutService.checkout(checkoutDTO);
		Assertions.assertEquals(OrderStatus.SUCCESS, checkout.getOrderStatus());
		Assertions.assertEquals(3705, checkout.getTotalAmount());
	}

	@Test
	void checkoutInvalidBook() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().isbn("0140434585").build(),
						BookDTO.builder().name("test123").build(),
						BookDTO.builder().bookId(sampleBook.get().getBookId()).build()
				)).promoCode("ABCD1234").build();
		ReceiptDTO checkout = checkoutService.checkout(checkoutDTO);
		Assertions.assertEquals(OrderStatus.INVALID_BOOK, checkout.getOrderStatus());
	}

	@Test
	void checkoutInvalidPromocode() throws Exception {
		CheckoutDTO checkoutDTO = CheckoutDTO.builder()
				.books(Arrays.asList(
						BookDTO.builder().isbn("0140434585").build(),
						BookDTO.builder().name("Midnight's Children").build(),
						BookDTO.builder().bookId(sampleBook.get().getBookId()).build()
				)).promoCode("TEST123").build();
		ReceiptDTO checkout = checkoutService.checkout(checkoutDTO);
		Assertions.assertEquals(OrderStatus.INVALID_PROMO_CODE, checkout.getOrderStatus());
	}
}