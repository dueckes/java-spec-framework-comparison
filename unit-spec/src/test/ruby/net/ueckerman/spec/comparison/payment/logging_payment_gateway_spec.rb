require File.dirname(__FILE__) + '/../../../../../spec_helper'

import Java::net.ueckerman.spec.comparison.order.OrderMother
import Java::net.ueckerman.spec.comparison.payment.LoggingPaymentGatewayDecorator
import Java::net.ueckerman.spec.comparison.payment.PaymentGateway
import Java::org.slf4j.Logger

describe LoggingPaymentGatewayDecorator do

  let(:delegate) { double(PaymentGateway) }
  let(:log) { double(Logger, :info => nil) }
  let(:order) { OrderMother.createOrder }

  let(:decorator) { LoggingPaymentGatewayDecorator.new(delegate, log) }

  context "#payFor" do

    it "should log the payment is initiated before the payment is processed" do
      log.should_receive(:info).with(/#{Regexp.escape(order.to_s)}.*initiated/) do
        delegate.should_receive(:payFor).with(order)
      end

      decorator.pay_for(order)
    end

    it "should log the payment is completed after the payment is processed" do
      delegate.should_receive(:payFor).with(order) do
         log.should_receive(:info).with(/#{Regexp.escape(order.to_s)}.*completed/)
      end

      decorator.pay_for(order)
    end

  end

end
