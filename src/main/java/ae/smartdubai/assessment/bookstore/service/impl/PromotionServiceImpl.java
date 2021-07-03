package ae.smartdubai.assessment.bookstore.service.impl;

import ae.smartdubai.assessment.bookstore.enums.Status;
import ae.smartdubai.assessment.bookstore.repo.PromotionRepo;
import ae.smartdubai.assessment.bookstore.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
	private final PromotionRepo promotionRepo;

	@Override
	public boolean validatePromoCode(String promoCode) {
		return promotionRepo.findByCodeAndStatus(promoCode, Status.ACTIVE).isPresent();
	}
}