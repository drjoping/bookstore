package ae.smartdubai.assessment.bookstore.repo;

import ae.smartdubai.assessment.bookstore.enums.Status;
import ae.smartdubai.assessment.bookstore.models.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface PromotionRepo extends JpaRepository<Promotion, String> {
	Optional<Promotion> findByCodeAndStatus(String id, Status status);
}