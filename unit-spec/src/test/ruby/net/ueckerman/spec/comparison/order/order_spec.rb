require File.dirname(__FILE__) + '/../../../../../spec_helper'

import Java::net.ueckerman.spec.comparison.finance.Money
import Java::net.ueckerman.spec.comparison.order.Order
import Java::net.ueckerman.spec.comparison.order.OrderStatus
import Java::net.ueckerman.spec.comparison.order.IllegalOrderStatusTransitionException
import Java::net.ueckerman.spec.comparison.product.ProductMother

describe Order do

  let(:products) { ProductMother.createProducts(28, 68, 88) }
  let(:order) { Order.new(products) }

  it "should have a default id of nil" do
    order.id.should be_nil
  end

  it "should have an initial status of new" do
    order.status.should eq(OrderStatus::NEW)
  end

  context "#totalCost" do

    it "should return Money whose value is the total value of all products" do
      order.totalCost.should eq(Money.new(28 + 68 + 88))
    end

  end

  context "#copyWithId" do

    it "should return an order containing the provided id" do
      copy_of_order = order.copyWithId(8)

      copy_of_order.id.should eql(8)
    end

    it "should return an order containing the products and status of the order" do
      order.transitionToStatus(OrderStatus::PAID)

      copy_of_order = order.copyWithId(8)

      copy_of_order.products.should eql(products)
      copy_of_order.status.should eql(OrderStatus::PAID)
    end

  end

  context "#transitionToStatus" do

    describe "when the target status is a subsequent prior status" do

      before(:each) do
        @target_status = OrderStatus::PAID
        @target_status.stub(:isValidPriorStatus).with(order.status).and_return(true)
      end

      it "should establish the orders status as the target status" do
        order.transitionToStatus(@target_status)

        order.status.should eql(@target_status)
      end

    end

    describe "when the target status is an invalid subsequent status" do

      before(:each) do
        @target_status = OrderStatus::NEW
        @target_status.stub(:isValidPriorStatus).with(order.status).and_return(false)
      end

      it "should throw an illegal order status transition exception whose message includes the prior and target status" do
        lambda { order.transitionToStatus(@target_status) }.should
          raise_error(IllegalOrderStatusTransitionException, /.*#{order.status}.*#{@target_status}/)
      end

    end

  end

end
