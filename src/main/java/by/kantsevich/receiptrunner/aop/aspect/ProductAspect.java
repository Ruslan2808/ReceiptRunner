package by.kantsevich.receiptrunner.aop.aspect;

import by.kantsevich.receiptrunner.cache.Cache;
import by.kantsevich.receiptrunner.cache.factory.CacheFactory;
import by.kantsevich.receiptrunner.dto.product.ProductResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductAspect {

    private final Cache<Long, ProductResponse> productCache;

    @Autowired
    public ProductAspect(CacheFactory cacheFactory) {
        this.productCache = cacheFactory.createCache();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.ProductService.findById(..))")
    public ProductResponse aroundFindByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];

        var cacheProduct = productCache.get(id);
        if (cacheProduct.isEmpty()) {
            var product = (ProductResponse) joinPoint.proceed();
            productCache.put(id, product);

            return product;
        }

        return cacheProduct.get();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.ProductService.save(..))")
    public ProductResponse aroundSaveMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var product = (ProductResponse) joinPoint.proceed();
        productCache.put(product.getId(), product);

        return product;
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.service.ProductService.update(..))")
    public ProductResponse aroundUpdateMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];

        var product = (ProductResponse) joinPoint.proceed();

        return productCache.put(id, product).get();
    }

    @Around(value = "execution(* by.kantsevich.receiptrunner.repository.ProductRepository.deleteById(..))")
    public ProductResponse aroundDeleteByIdMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        var id = (Long) joinPoint.getArgs()[0];

        joinPoint.proceed();
        var product = productCache.get(id);
        if (product.isEmpty()) {
            return null;
        }

        return productCache.remove(id).get();
    }
}
