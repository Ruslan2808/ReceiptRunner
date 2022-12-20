package by.kantsevich.receiptrunner.repository;

import by.kantsevich.receiptrunner.model.entity.DiscountCard;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscountCardRepositoryTest {

    @Autowired
    private DiscountCardRepository discountCardRepository;

    @Test
    void givenExistNumberDiscountCard_whenFindDiscountCardByNumber_thanReturnNotNullDiscountCard() {
        Integer numberDiscountCard = 1234;

        Optional<DiscountCard> discountCard = discountCardRepository.findByNumber(numberDiscountCard);

        assertNotNull(discountCard.get());
    }

}