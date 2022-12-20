package by.kantsevich.receiptrunner.repository;

import by.kantsevich.receiptrunner.model.entity.DiscountCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {
    Optional<DiscountCard> findByNumber(Integer number);
}
