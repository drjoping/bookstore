package ae.smartdubai.assessment.bookstore.rest;

import ae.smartdubai.assessment.bookstore.models.dto.CheckoutDTO;
import ae.smartdubai.assessment.bookstore.models.dto.ReceiptDTO;
import ae.smartdubai.assessment.bookstore.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	private final CheckoutService checkoutService;

	@Operation(description = "Endpoint to checkout books. Provide one or more books & optional promocode for discounts. Each book can be identified by Id or Name or ISBN ")
	@PostMapping(path = "/checkout")
	public ReceiptDTO checkout(@RequestBody CheckoutDTO checkoutDTO) {
		log.info("Checkout request: {}", checkoutDTO);
		return checkoutService.checkout(checkoutDTO);
	}
}