package ae.smartdubai.assessment.bookstore.models.entities;

import ae.smartdubai.assessment.bookstore.enums.Status;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Promotion {
	@Id
	private String code;
	@Builder.Default
	@Column(columnDefinition = "VARCHAR2(10) DEFAULT 'ACTIVE'")
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;
}