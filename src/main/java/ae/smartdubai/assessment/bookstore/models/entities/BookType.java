package ae.smartdubai.assessment.bookstore.models.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookType implements Serializable {
	@Id
	@GeneratedValue
	private Long bookTypeId;
	private String name;
	private String description;
	private double discountRate;
}