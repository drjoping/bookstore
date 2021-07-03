package ae.smartdubai.assessment.bookstore.models.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book implements Serializable {
	@Id
	@GeneratedValue
	private Long bookId;
	private String name;
	private String description;
	@ManyToOne
	@JoinColumn(name = "author")
	private Author author; // We could have multiple authors as well, assuming 1 to 1 for simplicity
	@ManyToOne
	@JoinColumn(name = "type")
	private BookType type;
	private double price;
	private String isbn;
}