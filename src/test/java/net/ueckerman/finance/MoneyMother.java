package net.ueckerman.finance;

import com.sun.tools.corba.se.idl.ModuleEntry;

import java.util.Random;

public class MoneyMother {

    public static final int MAXIMUM_VALUE_IN_CENTS = 1000;

    private static final Random randomNumberGenerator = new Random();

    private MoneyMother() {
        // Static class
    }

    public static Money createMoney() {
        return createMoney(randomNumberGenerator.nextInt(MAXIMUM_VALUE_IN_CENTS));
    }

    public static Money createMoney(long valueInCents) {
        return new Money(valueInCents);
    }

}
