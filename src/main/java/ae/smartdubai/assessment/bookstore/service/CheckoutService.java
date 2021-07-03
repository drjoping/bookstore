package ae.smartdubai.assessment.bookstore.service;

import ae.smartdubai.assessment.bookstore.models.dto.CheckoutDTO;
import ae.smartdubai.assessment.bookstore.models.dto.ReceiptDTO;

public interface CheckoutService {
	ReceiptDTO checkout(CheckoutDTO checkoutDTO);
}