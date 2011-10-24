require File.dirname(__FILE__) + '/../../../../../spec_helper'

import Java::net.ueckerman.test.comparison.order.IllegalOrderStatusTransitionException
import Java::net.ueckerman.test.comparison.order.OrderStatus

describe IllegalOrderStatusTransitionException do

  let(:exception) { IllegalOrderStatusTransitionException.new(OrderStatus::NEW, OrderStatus::PAID) }

  context "#message" do

    it "should contain the initial status" do
      exception.message.should match(/NEW/)
    end

    it "should contain the target status" do
      exception.message.should match(/PAID/)
    end

  end

end
