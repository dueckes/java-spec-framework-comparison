require File.dirname(__FILE__) + '/../../../spec_helper'

import Java::net.ueckerman.order.OrderMother
import Java::net.ueckerman.payment.LoggingPaymentGatewayDecorator
import Java::net.ueckerman.payment.PaymentGateway
import Java::org.slf4j.Logger

describe LoggingPaymentGatewayDecorator do

  before(:each) do
    @delegate = double(PaymentGateway)
    @log = double(Logger, :info => nil)

    @order = OrderMother.createOrder

    @decorator = LoggingPaymentGatewayDecorator.new(@delegate, @log)
  end

  context "#payFor" do

    it "should log the payment is initiated before the payment is processed" do
      @log.should_receive(:info).with(/#{Regexp.escape(@order.to_s)}.*initiated/) do
        @delegate.should_receive(:payFor).with(@order)
      end

      @decorator.pay_for(@order)
    end

    it "should log the payment is completed after the payment is processed" do
      @delegate.should_receive(:payFor).with(@order) do
         @log.should_receive(:info).with(/#{Regexp.escape(@order.to_s)}.*completed/)
      end

      @decorator.pay_for(@order)
    end

  end

end