package net.ueckerman.order;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class OrderStatusTest {

    @Test
    public void isValidPriorStatusReturnsTrueWhenNewAttemptsToTransitionToPaid() throws Exception {
        assertThat(OrderStatus.PAID.isValidPriorStatus(OrderStatus.NEW), is(true));
    }

    @Test
    public void isValidPriorStatusReturnsTrueWhenNewAttemptToTransitionToRejected() throws Exception {
        assertThat(OrderStatus.REJECTED.isValidPriorStatus(OrderStatus.NEW), is(true));
    }

    @Test
    public void isValidPriorStatusReturnsTrueWhenNewAttemptsToTransitionToCancelled() throws Exception {
        assertThat(OrderStatus.CANCELLED.isValidPriorStatus(OrderStatus.NEW), is(true));
    }

    @Test
    public void isValidPriorStatusReturnsFalseWhenPaidAttemptsToTransitionToCancelled() throws Exception {
        assertThat(OrderStatus.CANCELLED.isValidPriorStatus(OrderStatus.PAID), is(false));
    }

    @Test
    public void isValidPriorStatusReturnsFalseWhenPaidAttemptsToTransitionToRejected() throws Exception {
        assertThat(OrderStatus.REJECTED.isValidPriorStatus(OrderStatus.PAID), is(false));
    }

}
