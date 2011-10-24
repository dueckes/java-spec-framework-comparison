require File.dirname(__FILE__) + '/../../../../../spec_helper'

import Java::net.ueckerman.test.comparison.finance.Money
import Java::net.ueckerman.test.comparison.order.Order
import Java::net.ueckerman.test.comparison.order.OrderStatus
import Java::net.ueckerman.test.comparison.order.IllegalOrderStatusTransitionException
import Java::net.ueckerman.test.comparison.product.ProductMother

describe Order do

  let(:order) { Order.new(ProductMother.createProducts(28, 68, 88)) }

  it "should have an initial status of new" do
    order.status.should eq(OrderStatus::NEW)
  end

  context "#totalCost" do

    it "should return Money whose value is the total value of all products" do
      order.totalCost.should eq(Money.new(28 + 68 + 88))
    end

  end

  context "#transitionToStatus" do

    describe "when the target status is a valid prior status" do

      before(:each) do
        @target_status = OrderStatus::PAID
        @target_status.stub(:isValidPriorStatus).with(order.status).and_return(true)
      end

      it "should establish the orders status as the target status" do
        order.transitionToStatus(@target_status)

        order.status.should eq(@target_status)
      end

    end

    describe "when the target status is an invalid prior status" do

      before(:each) do
        @target_status = OrderStatus::NEW
        @target_status.stub(:isValidPriorStatus).with(order.status).and_return(false)
      end

      it "should establish the orders status as the target status" do
        lambda { order.transitionToStatus(@target_status) }.should
          raise_error(IllegalOrderStatusTransitionException, /.*#{order.status}.*#{@target_status}/)
      end

    end

  end

end
