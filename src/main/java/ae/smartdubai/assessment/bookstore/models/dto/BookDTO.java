package ae.smartdubai.assessment.bookstore.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO implements Serializable {
	private Long bookId;
	private String name;
	private String isbn;

	@JsonIgnore
	public boolean isValid() {
		return Optional.ofNullable(bookId).orElse(0L) > 0 || !StringUtils.isAllBlank(name, isbn);
	}
}