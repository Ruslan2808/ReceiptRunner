package by.kantsevich.receiptrunner.aop.aspect;

import by.kantsevich.receiptrunner.cache.Cache;
import by.kantsevich.receiptrunner.cache.factory.CacheFactory;
import by.kantsevich.receiptrunner.model.entity.DiscountCard;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DiscountCardAspect {

    private final Cache<Long, DiscountCard> discountCardCache;

    @Autowired
    public DiscountCardAspect(CacheFactory cacheFactory) {
        this.discountCardCache = cacheFactory.createCache();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.DiscountCardService.findById(..))")
    public DiscountCard aroundFindByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];

        var cacheDiscountCard = discountCardCache.get(id);
        if (cacheDiscountCard.isEmpty()) {
            var discountCard = (DiscountCard) joinPoint.proceed();
            discountCardCache.put(discountCard.getId(), discountCard);

            return discountCard;
        }

        return cacheDiscountCard.get();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.DiscountCardService.save(..))")
    public DiscountCard aroundSaveMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var discountCard = (DiscountCard) joinPoint.getArgs()[0];

        joinPoint.proceed();
        discountCardCache.put(discountCard.getId(), discountCard);

        return discountCard;
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.DiscountCardService.update(..))")
    public DiscountCard aroundUpdateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];
        var discountCard = (DiscountCard) joinPoint.getArgs()[1];

        joinPoint.proceed();

        return discountCardCache.put(id, discountCard).get();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.repository.DiscountCardRepository.deleteById(..))")
    public DiscountCard aroundDeleteByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];

        joinPoint.proceed();

        return discountCardCache.remove(id).get();
    }
}
