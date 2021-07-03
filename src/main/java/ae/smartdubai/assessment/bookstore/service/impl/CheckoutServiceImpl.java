package ae.smartdubai.assessment.bookstore.service.impl;

import ae.smartdubai.assessment.bookstore.enums.OrderStatus;
import ae.smartdubai.assessment.bookstore.models.dto.BookDTO;
import ae.smartdubai.assessment.bookstore.models.dto.CheckoutDTO;
import ae.smartdubai.assessment.bookstore.models.dto.ReceiptDTO;
import ae.smartdubai.assessment.bookstore.models.entities.Book;
import ae.smartdubai.assessment.bookstore.repo.BookRepo;
import ae.smartdubai.assessment.bookstore.service.CheckoutService;
import ae.smartdubai.assessment.bookstore.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {
	private final BookRepo bookRepo;
	private final PromotionService promotionService;

	@Override
	public ReceiptDTO checkout(CheckoutDTO checkoutDTO) {
		ReceiptDTO receiptDTO = new ReceiptDTO();
		List<Book> books;
		if (!CollectionUtils.isEmpty(checkoutDTO.getBooks())
				&& !CollectionUtils.isEmpty(books = findBooks(checkoutDTO.getBooks()))
				&& books.size() == checkoutDTO.getBooks().size()) {
			boolean applyDiscount = false;
			if (!StringUtils.isBlank(checkoutDTO.getPromoCode())) {
				if (promotionService.validatePromoCode(checkoutDTO.getPromoCode())) applyDiscount = true;
				else return receiptDTO.setOrderStatus(OrderStatus.INVALID_PROMO_CODE);
			}
			return receiptDTO.setOrderId(UUID.randomUUID().toString()).setOrderStatus(OrderStatus.SUCCESS).setTotalAmount(calculateTotal(books, applyDiscount));
		} else return receiptDTO.setOrderStatus(OrderStatus.INVALID_BOOK);
	}

	private List<Book> findBooks(List<BookDTO> bookDTOList) {
		return bookDTOList
				.stream()
				.filter(BookDTO::isValid)
				.map(bookDTO -> bookRepo.findFirstByBookIdOrNameIgnoreCaseOrIsbnIgnoreCase(bookDTO.getBookId(), bookDTO.getName(), bookDTO.getIsbn()).orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private double calculateTotal(List<Book> books, boolean applyDiscount) {
		return books.stream()
				.map(book -> applyDiscount ? book.getPrice() * (100 - book.getType().getDiscountRate()) / 100 : book.getPrice())
				.reduce(Double::sum).orElse(0.0);
	}
}