package by.kantsevich.receiptrunner.controller;

import by.kantsevich.receiptrunner.model.entity.DiscountCard;
import by.kantsevich.receiptrunner.service.DiscountCardService;
import by.kantsevich.receiptrunner.service.impl.DiscountCardServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/discount-cards")
public class DiscountCardController {

    private final DiscountCardService discountCardService;

    @Autowired
    public DiscountCardController(DiscountCardServiceImpl discountCardServiceImpl) {
        this.discountCardService = discountCardServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<DiscountCard>> getDiscountCards() {
        List<DiscountCard> discountCards = discountCardService.findAll();
        return ResponseEntity.ok(discountCards);
    }

    @GetMapping("{id}")
    public ResponseEntity<DiscountCard> getProduct(@PathVariable Long id) {
        DiscountCard discountCard = discountCardService.findById(id);
        return ResponseEntity.ok(discountCard);
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@Valid @RequestBody DiscountCard discountCard) {
        discountCardService.save(discountCard);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<DiscountCard> updateProduct(@PathVariable Long id, @Valid @RequestBody DiscountCard discountCard) {
        DiscountCard updateDiscountCard = discountCardService.update(id, discountCard);
        return ResponseEntity.ok(updateDiscountCard);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        discountCardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
