require '../../../../../../src/test/ruby/spec_helper'

import Java::net.ueckerman.order.OrderStatus

describe OrderStatus do

  context "#isValidPriorStatus" do

    describe "when the prior status is new" do

      let(:prior_status) { OrderStatus::NEW }

      it "should return true when the target status is paid" do
        OrderStatus::PAID.isValidPriorStatus(prior_status).should be_true
      end

      it "should return true when the target status is rejected" do
        OrderStatus::REJECTED.isValidPriorStatus(prior_status).should be_true
      end

      it "should return true when the target status is cancelled" do
        OrderStatus::CANCELLED.isValidPriorStatus(prior_status).should be_true
      end

    end

    describe "when the prior status is paid" do

      let(:prior_status) { OrderStatus::PAID }

      it "should return false when the target status is cancelled" do
        OrderStatus::CANCELLED.isValidPriorStatus(prior_status).should be_false
      end

      it "should return false when the target status is rejected" do
        OrderStatus::REJECTED.isValidPriorStatus(prior_status).should be_false
      end

    end

  end

end
