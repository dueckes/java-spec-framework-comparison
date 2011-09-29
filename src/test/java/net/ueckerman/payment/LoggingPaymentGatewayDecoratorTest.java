package net.ueckerman.payment;

import net.ueckerman.order.Order;
import net.ueckerman.order.OrderMother;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.slf4j.Logger;

import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.inOrder;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoggingPaymentGatewayDecoratorTest {

    @Mock
    private PaymentGateway delegate;
    @Mock
    private Logger log;

    private Order order;

    private LoggingPaymentGatewayDecorator loggingPaymentGatewayDecorator;

    @Before
    public void setUp() {
        initMocks(this);

        order = OrderMother.createOrder();

        loggingPaymentGatewayDecorator = new LoggingPaymentGatewayDecorator(delegate, log);
    }

    @Test
    public void payForLogsThatAPaymentForTheOrderHasInitiatedBeforeThePaymentIsProcessed() {
        loggingPaymentGatewayDecorator.payFor(order);

        InOrder inOrder = inOrder(log, delegate);
        inOrder.verify(log).info(matches(".*initiated"));
        inOrder.verify(delegate).payFor(order);
    }

    @Test
    public void payForLogsThatAPaymentForTheOrderHasCompletedAfterThePaymentIsProcessed() {
        loggingPaymentGatewayDecorator.payFor(order);

        InOrder inOrder = inOrder(delegate, log);
        inOrder.verify(delegate).payFor(order);
        inOrder.verify(log).info(matches(".*completed"));
    }

}
