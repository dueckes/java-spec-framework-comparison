package net.ueckerman.spec.comparison.finance;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * An abstraction encapsulating price.
 */
public class Money {

    private static final int HASH_INITIAL_NUMBER = 17;
    private static final int HASH_MULTIPLIER = 7;

    private final long valueInCents;

    public Money(long valueInCents) {
        Validate.isTrue(valueInCents >= 0, "Value cannot be negative value");
        this.valueInCents = valueInCents;
    }

    public Money add(Money amount) {
        return new Money(valueInCents + amount.getValueInCents());
    }

    public long getValueInCents() {
        return valueInCents;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASH_INITIAL_NUMBER, HASH_MULTIPLIER)
                .append(getClass()).append(valueInCents)
                .build();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !other.getClass().equals(getClass())) {
            return false;
        }
        Money otherMoney = (Money) other;
        return other == this || new EqualsBuilder().append(valueInCents, otherMoney.getValueInCents()).build();
    }

}
