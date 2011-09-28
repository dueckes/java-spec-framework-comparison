package net.ueckerman.order;

import net.ueckerman.finance.Money;
import net.ueckerman.product.ProductMother;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OrderStatus.class)
public class OrderTest {

    private Order order;
    private OrderStatus initialStatus;

    @Before
    public void setUp() throws Exception {
        order = new Order(ProductMother.createProducts(28, 68, 88));
        initialStatus = order.getStatus();
    }

    @Test
    public void initialStatusIsNew() {
        assertThat(order.getStatus(), is(equalTo(OrderStatus.NEW)));
    }

    @Test
    public void totalCostReturnsMoneyWhoseValueIsTheSumOfAllProductCosts() throws Exception {
        assertThat(order.totalCost(), is(equalTo(new Money(28 + 68 + 88))));
    }

    @Test
    public void transitionToStatusEstablishesStatusAsProvidedStatusWhenTransitionIsValid() {
        OrderStatus targetStatus = mock(OrderStatus.class);
        given(targetStatus.isValidPriorStatus(initialStatus)).willReturn(true);

        order.transitionToStatus(targetStatus);
    }

    @Test
    public void transitionToStatusThrowsIllegalStatusTransitionExceptionWhenTransitionIsInvalid() {
        OrderStatus targetStatus = mock(OrderStatus.class);
        when(targetStatus.isValidPriorStatus(initialStatus)).thenReturn(false);

        try {
            order.transitionToStatus(targetStatus);
            fail(IllegalOrderStatusTransitionException.class.getName() + " expected");
        } catch (IllegalOrderStatusTransitionException illegalOrderStatusTransitionExc) {
            assertThat(illegalOrderStatusTransitionExc.getCurrentStatus(), is(equalTo(initialStatus)));
            assertThat(illegalOrderStatusTransitionExc.getTargetStatus(), is(equalTo(targetStatus)));
        }
    }

}
