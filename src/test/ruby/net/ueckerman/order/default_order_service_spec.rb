require File.dirname(__FILE__) + '/../../../spec_helper'

import Java::net.ueckerman.order.DefaultOrderService
import Java::net.ueckerman.order.Order
import Java::net.ueckerman.order.OrderStatus
import Java::net.ueckerman.order.OrderFactory
import Java::net.ueckerman.order.OrderMother
import Java::net.ueckerman.order.OrderRepository
import Java::net.ueckerman.payment.PaymentGateway
import Java::net.ueckerman.product.Product
import Java::net.ueckerman.product.ProductMother
import Java::net.ueckerman.user.UserMother

describe DefaultOrderService do

  before(:each) do
    @products = ProductMother.createProducts
    @user = UserMother.createUser

    @unsaved_order = OrderMother.createOrder
    @saved_order = OrderMother.createOrder

    @order_factory = double(OrderFactory, :create => @unsaved_order)
    @order_repository = double(OrderRepository, :save => @saved_order)
    @payment_gateway = double(PaymentGateway, :payFor => nil)

    @default_order_service = DefaultOrderService.new(@order_factory, @order_repository, @payment_gateway)
  end

  context "#placeOrderFor" do

    it "should persist the created order" do
      @order_repository.should_receive(:save).with(@unsaved_order)

      @default_order_service.placeOrderFor(@products, @user)
    end

    it "should charge for the saved order via the payment gateway" do
      @payment_gateway.should_receive(:payFor).with(@saved_order)

      @default_order_service.placeOrderFor(@products, @user)
    end

    it "should charge for an order whose status is new" do
      @payment_gateway.should_receive(:payFor).with { |order| order.status == OrderStatus::NEW }

      @default_order_service.placeOrderFor(@products, @user)
    end

    it "should charge for the saved order after the created order is persisted" do
      @order_repository.should_receive(:save).with(@unsaved_order) do
        @payment_gateway.should_receive(:payFor).with(@saved_order)
      end.and_return(@saved_order)

      @default_order_service.placeOrderFor(@products, @user)
    end

    it "should persist the paid order after the payment is made" do
      @payment_gateway.should_receive(:payFor).with(@saved_order) do
        @order_repository.should_receive(:save).with(@saved_order)
      end

      @default_order_service.placeOrderFor(@products, @user)
    end

    it "should return the last saved order" do
      secondSavedOrder = OrderMother.createOrder
      @order_repository.stub(:save).with(@saved_order).and_return(secondSavedOrder)

      @default_order_service.placeOrderFor(@products, @user).should eq(secondSavedOrder)

    end

  end

end
