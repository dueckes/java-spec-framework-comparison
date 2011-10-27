package net.ueckerman.spec.comparison.order;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

@RunWith(JDaveRunner.class)
public class IllegalOrderStatusTransitionExceptionJDaveSpec extends Specification<IllegalOrderStatusTransitionException> {

    private IllegalOrderStatusTransitionException illegalOrderStatusTransitionException;

    @Override
    public void create() {
        illegalOrderStatusTransitionException =
                new IllegalOrderStatusTransitionException(OrderStatus.NEW, OrderStatus.PAID);
    }

    public class GetMessage {

        public void shouldReturnAStringContainingTheInitialStatus() {
            assertThat(illegalOrderStatusTransitionException.getMessage(), containsString("NEW"));
        }

        public void shouldReturnAStringContainingTheTargetStatus() {
            assertThat(illegalOrderStatusTransitionException.getMessage(), containsString("PAID"));
        }

    }

}
