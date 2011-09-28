require 'rspec'
require 'java'

import Java::net.ueckerman.finance.Money

describe Money do

  let(:money) { Money.new(80) }

  context "#add" do

    it "should return Money whose value is that of both Monies combined" do
      addedMoney = money.add(Money.new(70))

      addedMoney.valueInCents.should == 150
    end

  end

  context "#hashCode" do

    describe "when hashcode of the same object is compared" do

      it "should be equal" do
        money.hashCode.should == money.hashCode
      end

    end

    describe "when hashcode of another Money with an identical value is compared" do

      it "should be equal" do
        money.hashCode.should == Money.new(80).hashCode
      end

    end

    describe "when hashcode of another type with an identical value is compared" do

      before(:all) do
        other_type = Class.new(Money)
        @other_instance = other_type.new(80)
      end

      it "should not be equal" do
        money.hashCode.should_not == @other_instance.hashCode
      end

    end

  end

  context "#equals" do

    describe "when compared to the same object" do

      it "should be true" do
        money.equals(money).should be_true
      end

    end

    describe "when compared an object of the same type with the same value" do

      it "should be true" do
        money.equals(Money.new(80)).should be_true
      end

    end

    describe "when compared to an object of the same type with different values" do

      it "should be false" do
        money.equals(Money.new(81)).should be_false
      end

    end

    describe "when compared to an object of a different type with the same value" do

      before(:all) do
        different_type = Class.new(Money)
        @different_object = different_type.new(80)
      end

      it "should be false" do
        money.equals(@different_object).should be_false
      end

    end

    describe "when compared to nil" do

      it "should be false" do
        money.equals(nil).should be_false
      end

    end

  end

end
