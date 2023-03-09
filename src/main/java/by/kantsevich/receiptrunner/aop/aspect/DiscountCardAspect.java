package by.kantsevich.receiptrunner.aop.aspect;

import by.kantsevich.receiptrunner.cache.Cache;
import by.kantsevich.receiptrunner.cache.factory.CacheFactory;
import by.kantsevich.receiptrunner.dto.discount_card.DiscountCardResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DiscountCardAspect {

    private final Cache<Long, DiscountCardResponse> discountCardCache;

    @Autowired
    public DiscountCardAspect(CacheFactory cacheFactory) {
        this.discountCardCache = cacheFactory.createCache();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.DiscountCardService.findById(..))")
    public DiscountCardResponse aroundFindByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];

        var cacheDiscountCard = discountCardCache.get(id);
        if (cacheDiscountCard.isEmpty()) {
            var discountCard = (DiscountCardResponse) joinPoint.proceed();
            discountCardCache.put(id, discountCard);

            return discountCard;
        }

        return cacheDiscountCard.get();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.DiscountCardService.save(..))")
    public DiscountCardResponse aroundSaveMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var discountCard = (DiscountCardResponse) joinPoint.proceed();
        discountCardCache.put(discountCard.getId(), discountCard);

        return discountCard;
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.DiscountCardService.update(..))")
    public DiscountCardResponse aroundUpdateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];

        var discountCard = (DiscountCardResponse) joinPoint.proceed();

        return discountCardCache.put(id, discountCard).get();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.repository.DiscountCardRepository.deleteById(..))")
    public DiscountCardResponse aroundDeleteByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];

        joinPoint.proceed();
        var discountCard = discountCardCache.get(id);
        if (discountCard.isEmpty()) {
            return null;
        }

        return discountCardCache.remove(id).get();
    }
}
