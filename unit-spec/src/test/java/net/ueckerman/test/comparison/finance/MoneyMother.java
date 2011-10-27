package net.ueckerman.test.comparison.finance;

import java.util.Random;

public class MoneyMother {

    public static final int MAXIMUM_VALUE_IN_CENTS = 1000;

    private static final Random RANDOM_NUMBER_GENERATOR = new Random();

    private MoneyMother() {
        // Static class
    }

    public static Money createMoney() {
        return createMoney(RANDOM_NUMBER_GENERATOR.nextInt(MAXIMUM_VALUE_IN_CENTS));
    }

    public static Money createMoney(long valueInCents) {
        return new Money(valueInCents);
    }

}
