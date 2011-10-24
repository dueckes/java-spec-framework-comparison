package net.ueckerman.test.comparison.payment;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import net.ueckerman.test.comparison.order.Order;
import net.ueckerman.test.comparison.order.OrderMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.slf4j.Logger;

import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.inOrder;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JDaveRunner.class)
public class LoggingPaymentGatewayDecoratorJDaveSpec extends Specification<LoggingPaymentGatewayDecorator> {

    @Mock
    private PaymentGateway delegate;
    @Mock
    private Logger log;

    private Order order;

    private LoggingPaymentGatewayDecorator loggingPaymentGatewayDecorator;

    @Override
    public void create() {
        initMocks(this);

        order = OrderMother.createOrder();

        loggingPaymentGatewayDecorator = new LoggingPaymentGatewayDecorator(delegate, log);
    }

    public class PayFor {

        @Test
        public void shouldLogThatAPaymentForTheOrderWasInitiatedBeforeThePaymentIsProcessed() {
            loggingPaymentGatewayDecorator.payFor(order);

            InOrder inOrder = inOrder(log, delegate);
            inOrder.verify(log).info(matches(".*initiated"));
            inOrder.verify(delegate).payFor(order);
        }

        @Test
        public void shouldLogThatAPaymentForTheOrderWasCompletedAfterThePaymentIsProcessed() {
            loggingPaymentGatewayDecorator.payFor(order);

            InOrder inOrder = inOrder(delegate, log);
            inOrder.verify(delegate).payFor(order);
            inOrder.verify(log).info(matches(".*completed"));
        }

    }

}
