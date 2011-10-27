package net.ueckerman.spec.comparison.finance;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class MoneyJDaveSpec extends Specification<Money> {

    private Money money;

    public void create() {
        money = new Money(80);
    }

    public class Add {

        public void shouldReturnMoneyWhoseValueInCentsIsTheSumOfTheInstanceAndTheArgument() throws Exception {
            Money addResult = money.add(new Money(70));

            specify(addResult.getValueInCents(), should.equal(150L));
        }

    }

    public class HashCode {

        public void shouldReturnTheSameValueWhenCalledTwice() {
            specify(money.hashCode(), should.equal(money.hashCode()));
        }

        public void shouldReturnTheSameValueWhenComparedWithObjectOfSameTypeWithSameValue() {
            Money otherMoney = new Money(80);

            specify(money.hashCode(), should.equal(otherMoney.hashCode()));
        }

        public void shouldReturnADifferentValueWhenComparedWithAnObjectOfSameTypeWithADifferentValue() {
            Money otherMoney = new Money(81);

            specify(money.hashCode(), should.not().equal(otherMoney.hashCode()));
        }

        public void shouldReturnADifferentValueWhenComparedWithTheHashCodeOfAnotherTypeWithSameValue() {
            specify(money.hashCode(), should.not().equal(new SimilarToMoney(80).hashCode()));
        }

    }

    public class Equals {

        public void shouldReturnTrueWhenObjectsAreSame() {
           specify(money.equals(money), should.equal(true));
        }

        public void shouldReturnTrueWhenObjectsAreOfTheSameTypeWithEqualValues() {
            specify(money.equals(new Money(80)), should.equal(true));
        }

        public void shouldReturnFalseWhenObjectsAreNotOfTheSameTypeWithSameValue() {
            specify(money.equals(new SimilarToMoney(80)), should.equal(false));
        }

        public void shouldReturnFalseWhenProvidedObjectIsNull() {
            specify(money.equals(null), should.equal(false));
        }

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
