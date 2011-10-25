package net.ueckerman.test.comparison.payment

import org.slf4j.Logger
import org.mockito.MockitoAnnotations._
import net.ueckerman.test.comparison.order.{OrderMother, Order}
import org.mockito.Mockito.inOrder
import org.mockito.InOrder
import org.mockito.Matchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, Spec}

@RunWith(classOf[JUnitRunner])
class LoggingPaymentGatewayDecoratorScalaTestSpec extends Spec with ShouldMatchers with BeforeAndAfter {

  @Mock
  var delegate: PaymentGateway = _
  @Mock
  var log: Logger = _

  var order: Order = _

  var loggingPaymentGatewayDecorator: LoggingPaymentGatewayDecorator = _

  before {
    initMocks(this)

    order = OrderMother.createOrder

    loggingPaymentGatewayDecorator = new LoggingPaymentGatewayDecorator(delegate, log)
  }

  describe("payFor") {
    it("shouldLogThatAPaymentForTheOrderWasInitiatedBeforeThePaymentIsProcessed") {
      loggingPaymentGatewayDecorator.payFor(order)

      val verifiableExecutionOrder: InOrder = inOrder(log, delegate)
      verifiableExecutionOrder.verify(log).info(matches(".*initiated"))
      verifiableExecutionOrder.verify(delegate).payFor(order)
    }

    it("shouldLogThatAPaymentForTheOrderWasCompletedAfterThePaymentIsProcessed") {
      loggingPaymentGatewayDecorator.payFor(order)

      val verifiableExecutionOrder: InOrder = inOrder(delegate, log)
      verifiableExecutionOrder.verify(delegate).payFor(order)
      verifiableExecutionOrder.verify(log).info(matches(".*completed"))
    }

  }

}
