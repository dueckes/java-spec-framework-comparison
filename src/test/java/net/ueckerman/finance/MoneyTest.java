package net.ueckerman.finance;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class MoneyTest {

    private Money money;

    @Before
    public void setUp() {
        money = new Money(80);
    }

    @Test
    public void addReturnsMoneyWhoseValueInCentsIsTheSumOfTheInstanceAndTheArgument() throws Exception {
        Money addResult = money.add(new Money(70));

        assertThat(addResult.getValueInCents(), is(equalTo(150L)));
    }

    @Test
    public void hashCodeReturnsSameValueWhenCalledTwice() {
        assertThat(money.hashCode(), is(equalTo(money.hashCode())));
    }

    @Test
    public void hashCodeReturnsSameValueWhenComparedWithObjectOfSameTypeWithSameValue() {
        Money otherMoney = new Money(80);

        assertThat(money.hashCode(), is(equalTo(otherMoney.hashCode())));
    }

    @Test
    public void hashCodeReturnsDifferentValueWhenComparedWithObjectOfSameTypeWithDifferentValue() {
        Money otherMoney = new Money(81);

        assertThat(money.hashCode(), is(not(equalTo(otherMoney.hashCode()))));
    }

    @Test
    public void hashCodeReturnsDifferentValueWhenComparedWithHashCodeOfAnotherTypeWithSameValue() {
        assertThat(money.hashCode(), is(not(equalTo(new SimilarToMoney(80).hashCode()))));
    }

    @Test
    public void equalsReturnsTrueWhenObjectsAreSame() {
       assertThat(money.equals(money), is(true));
    }

    @Test
    public void equalsReturnsTrueWhenObjectsAreOfTheSameTypeWithEqualValues() {
        assertThat(money.equals(new Money(80)), is(true));
    }

    @Test
    public void equalsReturnsFalseWhenObjectsAreNotOfTheSameTypeWithSameValue() {
        assertThat(money.equals(new SimilarToMoney(80)), is(false));
    }

    @Test
    public void equalsReturnsFalseWhenProvidedObjectIsNull() {
        assertThat(money.equals(null), is(false));
    }

    private static final class SimilarToMoney {

        private final long valueInCents;

        public SimilarToMoney(long valueInCents) {
            this.valueInCents = valueInCents;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 7).append(valueInCents).build();
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            SimilarToMoney otherSimilarToMoney = (SimilarToMoney) other;
            return this == other || new EqualsBuilder().append(valueInCents, otherSimilarToMoney.valueInCents).build();
        }
    }

}
