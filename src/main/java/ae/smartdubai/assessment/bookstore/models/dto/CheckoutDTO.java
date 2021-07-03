package ae.smartdubai.assessment.bookstore.models.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CheckoutDTO {
	private List<BookDTO> books;
	private String promoCode;
}