package pl.rzeznicki.wypozyczalnia_aut_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.RentalEntity;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
}
