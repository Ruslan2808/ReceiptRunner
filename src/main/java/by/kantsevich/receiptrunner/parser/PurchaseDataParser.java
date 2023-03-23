package by.kantsevich.receiptrunner.parser;

import java.util.*;
import java.util.stream.Collectors;

public class PurchaseDataParser {

    public final static String SEPARATOR_CHAR = "-";

    public static Map<Integer, Integer> getProducts(String[] args) {
        return Arrays.stream(args)
                .filter(s -> s.matches("^[1-9]+" + SEPARATOR_CHAR + "[1-9]+$"))
                .map(s -> s.split(SEPARATOR_CHAR))
                .collect(Collectors.toMap(
                        product -> Integer.parseInt(product[0]),
                        product -> Integer.parseInt(product[1]),
                        Integer::sum,
                        LinkedHashMap::new
                ));
    }

    public static Map<Integer, Integer> getProducts(List<Integer> productsId, List<Integer> productsQty) {
        Map<Integer, Integer> products = new LinkedHashMap<>();

        var productsIdIterator = productsId.iterator();
        var productsQtyIterator = productsQty.iterator();

        while (productsIdIterator.hasNext() && productsQtyIterator.hasNext()) {
            Integer productId = productsIdIterator.next();
            Integer productQty = productsQtyIterator.next();

            if (products.containsKey(productId)) {
                productQty += products.get(productId);
            }

            products.put(productId, productQty);
        }

        return products;
    }

    public static Integer getNumberDiscountCard(String[] args) {
        return Arrays.stream(args)
                .map(String::toLowerCase)
                .filter(s -> s.matches("^card" + SEPARATOR_CHAR + "[1-9]+$"))
                .map(s -> Integer.parseInt(s.split(SEPARATOR_CHAR)[1]))
                .findFirst()
                .orElse(0);
    }
}
