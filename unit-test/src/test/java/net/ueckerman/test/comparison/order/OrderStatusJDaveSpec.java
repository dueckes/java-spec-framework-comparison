package net.ueckerman.test.comparison.order;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class OrderStatusJDaveSpec extends Specification<OrderStatus> {

    public class IsValidPriorStatusWhenThePriorStatusIsNew {

        public void itShouldReturnTrueWhenAttemptingToTransitionToPaid() throws Exception {
            specify(OrderStatus.PAID.isValidPriorStatus(OrderStatus.NEW), should.equal(true));
        }

        public void itShouldReturnTrueWhenAttemptingToTransitionToRejected() throws Exception {
            specify(OrderStatus.REJECTED.isValidPriorStatus(OrderStatus.NEW), should.equal(true));
        }

        public void itShouldReturnTrueWhenAttemptingToTransitionToCancelled() throws Exception {
            specify(OrderStatus.CANCELLED.isValidPriorStatus(OrderStatus.NEW), should.equal(true));
        }

    }

    public class IsValidPriorStatusWhenThePriorStatusIsPaid {

        public void itShouldReturnFalseWhenAttemptingToTransitionToCancelled() throws Exception {
            specify(OrderStatus.CANCELLED.isValidPriorStatus(OrderStatus.PAID), should.equal(false));
        }

        public void itShouldReturnFalseWhenAttemptingToTransitionToRejected() throws Exception {
            specify(OrderStatus.REJECTED.isValidPriorStatus(OrderStatus.PAID), should.equal(false));
        }

    }

}
