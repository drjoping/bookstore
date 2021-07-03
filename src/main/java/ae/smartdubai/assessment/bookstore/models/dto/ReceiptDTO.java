package ae.smartdubai.assessment.bookstore.models.dto;

import ae.smartdubai.assessment.bookstore.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReceiptDTO {
	private String orderId;
	private OrderStatus orderStatus;
	private double totalAmount;
}