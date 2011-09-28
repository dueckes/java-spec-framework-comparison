package net.ueckerman.order;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.StringContains.containsString;

public class IllegalOrderStatusTransitionExceptionTest {

    private IllegalOrderStatusTransitionException illegalOrderStatusTransitionException;

    @Before
    public void setUp() {
        illegalOrderStatusTransitionException =
                new IllegalOrderStatusTransitionException(OrderStatus.NEW, OrderStatus.PAID);
    }

    @Test
    public void getMessageReturnsStringContainingInitialStatus() {
        assertThat(illegalOrderStatusTransitionException.getMessage(), containsString("NEW"));
    }

    @Test
    public void getMessageReturnsStringContainingTargetStatus() {
        assertThat(illegalOrderStatusTransitionException.getMessage(), containsString("PAID"));
    }

}
