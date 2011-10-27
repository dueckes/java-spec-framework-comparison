package net.ueckerman.spec.comparison.payment;

import net.ueckerman.spec.comparison.order.Order;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import static java.lang.String.format;

public class LoggingPaymentGatewayDecorator implements PaymentGateway {

    private static final String PAYMENT_REQUEST_LIFECYCLE_LOG_MESSAGE_FORMAT = "Payment request for order %s %s";

    private static final String PAYMENT_INITIATED = "initiated";
    private static final String PAYMENT_COMPLETED = "completed";

    private final PaymentGateway delegate;
    private final Logger log;

    public LoggingPaymentGatewayDecorator(PaymentGateway delegate, Logger log) {
        Validate.notNull(delegate, "Delegate of must be provided");
        Validate.notNull(log, "Log of must be provided");
        this.delegate = delegate;
        this.log = log;
    }

    @Override
    public void payFor(Order order) {
        logPaymentRequestForLifecycle(order, PAYMENT_INITIATED);
        delegate.payFor(order);
        logPaymentRequestForLifecycle(order, PAYMENT_COMPLETED);
    }

    private void logPaymentRequestForLifecycle(Order order, String lifecycle) {
        log.info(format(PAYMENT_REQUEST_LIFECYCLE_LOG_MESSAGE_FORMAT, order.toString(), lifecycle));
    }

}
