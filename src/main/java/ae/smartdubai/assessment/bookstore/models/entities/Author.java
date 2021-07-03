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
public class Author implements Serializable {
	@Id
	@GeneratedValue
	private Long authorId;
	private String firstName;
	private String lastName;
}