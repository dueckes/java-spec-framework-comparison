package net.ueckerman.spec.comparison.product;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static net.ueckerman.spec.comparison.finance.MoneyMother.createMoney;

/**
 * Constructs Products for test purposes.
 */
public class ProductMother {

    private ProductMother() {
        // Static class
    }

    public static Product createProduct() {
        return new Product("Some description", createMoney());
    }

    public static List<Product> createProducts() {
        return newArrayList(createProduct(), createProduct(), createProduct());
    }

    public static List<Product> createProducts(long... productValuesInCents) {
        List<Product> products = newArrayList();
        for (long productValue : productValuesInCents) {
            products.add(createProduct(productValue));
        }
        return products;
    }

    private static Product createProduct(long valueInCents) {
        return new Product("Some product description", createMoney(valueInCents));
    }

}
