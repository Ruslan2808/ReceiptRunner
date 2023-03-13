package by.kantsevich.receiptrunner.controller;

import by.kantsevich.receiptrunner.dto.discount_card.DiscountCardRequest;
import by.kantsevich.receiptrunner.dto.discount_card.DiscountCardResponse;
import by.kantsevich.receiptrunner.service.DiscountCardService;
import by.kantsevich.receiptrunner.service.impl.DiscountCardServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discount-cards")
public class DiscountCardController {

    private final DiscountCardService discountCardService;

    @Autowired
    public DiscountCardController(DiscountCardServiceImpl discountCardServiceImpl) {
        this.discountCardService = discountCardServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<DiscountCardResponse>> getDiscountCards() {
        List<DiscountCardResponse> discountCards = discountCardService.findAll();
        return ResponseEntity.ok(discountCards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountCardResponse> getProduct(@PathVariable Long id) {
        DiscountCardResponse discountCard = discountCardService.findById(id);
        return ResponseEntity.ok(discountCard);
    }

    @PostMapping
    public ResponseEntity<DiscountCardResponse> saveProduct(@Valid @RequestBody DiscountCardRequest discountCardRequest) {
        DiscountCardResponse saveDiscountCard = discountCardService.save(discountCardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveDiscountCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountCardResponse> updateProduct(@PathVariable Long id,
                                                              @Valid @RequestBody DiscountCardRequest discountCardRequest) {
        DiscountCardResponse updateDiscountCard = discountCardService.update(id, discountCardRequest);
        return ResponseEntity.ok(updateDiscountCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        discountCardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
