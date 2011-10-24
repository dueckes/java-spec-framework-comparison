require File.dirname(__FILE__) + '/../../../../../spec_helper'

import Java::net.ueckerman.test.comparison.order.DefaultOrderService
import Java::net.ueckerman.test.comparison.order.Order
import Java::net.ueckerman.test.comparison.order.OrderStatus
import Java::net.ueckerman.test.comparison.order.OrderFactory
import Java::net.ueckerman.test.comparison.order.OrderMother
import Java::net.ueckerman.test.comparison.order.OrderRepository
import Java::net.ueckerman.test.comparison.payment.PaymentGateway
import Java::net.ueckerman.test.comparison.product.Product
import Java::net.ueckerman.test.comparison.product.ProductMother
import Java::net.ueckerman.test.comparison.user.UserMother

describe DefaultOrderService do

  let(:products) { ProductMother.createProducts }
  let(:user) { UserMother.createUser }
  let(:unsaved_order) { OrderMother.createOrder }
  let(:saved_order) { OrderMother.createOrder }

  let(:order_factory) { double(OrderFactory, :create => unsaved_order) }
  let(:order_repository) { double(OrderRepository, :save => saved_order) }
  let(:payment_gateway) { double(PaymentGateway, :payFor => nil) }

  let(:default_order_service) { DefaultOrderService.new(order_factory, order_repository, payment_gateway) }

  context "#placeOrderFor" do

    it "should persist the created order" do
      order_repository.should_receive(:save).with(unsaved_order)

      default_order_service.placeOrderFor(products, user)
    end

    it "should charge for the saved order via the payment gateway" do
      payment_gateway.should_receive(:payFor).with(saved_order)

      default_order_service.placeOrderFor(products, user)
    end

    it "should charge for an order whose status is new" do
      payment_gateway.should_receive(:payFor).with { |order| order.status == OrderStatus::NEW }

      default_order_service.placeOrderFor(products, user)
    end

    it "should charge for the saved order after the created order is persisted" do
      order_repository.should_receive(:save).with(unsaved_order) do
        payment_gateway.should_receive(:payFor).with(saved_order)
      end.and_return(saved_order)

      default_order_service.placeOrderFor(products, user)
    end

    it "should persist the paid order after the payment is made" do
      payment_gateway.should_receive(:payFor).with(saved_order) do
        order_repository.should_receive(:save).with(saved_order)
      end

      default_order_service.placeOrderFor(products, user)
    end

    it "should return the last saved order" do
      second_saved_order = OrderMother.createOrder
      order_repository.stub(:save).with(saved_order).and_return(second_saved_order)

      default_order_service.placeOrderFor(products, user).should eql(second_saved_order)
    end

  end

end
