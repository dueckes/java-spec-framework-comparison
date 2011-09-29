require "../../../../../../src/test/ruby/spec_helper"

import Java::net.ueckerman.order.IllegalOrderStatusTransitionException
import Java::net.ueckerman.order.OrderStatus

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
