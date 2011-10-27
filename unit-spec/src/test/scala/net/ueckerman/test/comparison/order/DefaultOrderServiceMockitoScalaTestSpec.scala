package net.ueckerman.test.comparison.order

import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.mockito.InOrder
import org.mockito.Mock
import java.util.List
import net.ueckerman.test.comparison.product.Product
import net.ueckerman.test.comparison.user.User
import org.mockito.BDDMockito.given
import org.mockito.Matchers.any
import org.mockito.Matchers.argThat
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, Spec}
import net.ueckerman.test.comparison.payment.PaymentGateway
import net.ueckerman.test.comparison.product.ProductMother.createProducts
import net.ueckerman.test.comparison.order.OrderMother.createOrder
import net.ueckerman.test.comparison.user.UserMother.createUser

@RunWith(classOf[JUnitRunner])
class DefaultOrderServiceMockitoScalaTestSpec extends Spec with ShouldMatchers with BeforeAndAfter {

  @Mock
  var orderRepository: OrderRepository = _
  @Mock
  var orderFactory: OrderFactory = _
  @Mock
  var paymentGateway: PaymentGateway = _

  var products: List[Product] = _
  var user: User = _
  var unsavedOrder: Order = _
  var savedOrder: Order = _

  var defaultOrderService: DefaultOrderService = _

  before {
    initMocks(this)

    products = createProducts
    user = createUser
    unsavedOrder = createOrder
    savedOrder = createOrder

    defaultOrderService = new DefaultOrderService(orderFactory, orderRepository, paymentGateway)

    given(orderFactory.create(products, user)).willReturn(unsavedOrder)
    given(orderRepository.save(any(classOf[Order]))).willReturn(savedOrder)
  }

  describe("placeOrderFor") {

    it("should persist a created order") {
      defaultOrderService.placeOrderFor(products, user)

      verify(orderRepository).save(unsavedOrder)
    }

    it("should charge for a saved order via the payment gateway") {
      defaultOrderService.placeOrderFor(products, user)

      verify(paymentGateway).payFor(savedOrder)
    }

    it("should charge for an order whose status is new") {
      val forcedException: RuntimeException = new RuntimeException("Invalid Order Status")
      doThrow(forcedException).when(paymentGateway).payFor(argThat(isOrderWithStatus(OrderStatus.NEW)))

      val actualException = evaluating {
        defaultOrderService.placeOrderFor(products, user)
      } should produce[RuntimeException]

      actualException should be theSameInstanceAs (forcedException)
    }

    it("should charge for a saved order after a new order is persisted") {
      defaultOrderService.placeOrderFor(products, user)

      val verifiableExecutionOrder: InOrder = inOrder(orderRepository, paymentGateway)
      verifiableExecutionOrder.verify(orderRepository).save(unsavedOrder)
      verifiableExecutionOrder.verify(paymentGateway).payFor(savedOrder)
    }

    it("should persist a paid order after an order is persisted") {
      defaultOrderService.placeOrderFor(products, user)

      val verifiableExecutionOrder: InOrder = inOrder(paymentGateway, orderRepository)
      verifiableExecutionOrder.verify(paymentGateway).payFor(savedOrder)
      verifiableExecutionOrder.verify(orderRepository).save(savedOrder)
    }

    it("should return the last persisted order") {
      val secondSavedOrder: Order = createOrder
      given(orderRepository.save(savedOrder)).willReturn(secondSavedOrder)

      secondSavedOrder should equal(defaultOrderService.placeOrderFor(products, user))
    }

  }

  private def isOrderWithStatus(orderStatus: OrderStatus): ArgumentMatcher[Order] = {
    new OrderWithStatusMatcher(orderStatus)
  }

  private final class OrderWithStatusMatcher(val expectedOrderStatus: OrderStatus) extends ArgumentMatcher[Order] {

    def matches(argument: AnyRef): Boolean = {
      (argument.asInstanceOf[Order]).getStatus eq (expectedOrderStatus)
    }

  }

}
