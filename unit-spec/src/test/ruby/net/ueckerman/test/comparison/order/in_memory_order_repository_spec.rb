require "rspec"

import Java::net.ueckerman.test.comparison.order.InMemoryOrderRepository
import Java::net.ueckerman.test.comparison.order.OrderMother

describe InMemoryOrderRepository do

  let(:order_repository) { InMemoryOrderRepository.new }

  context "#save" do

    describe "when provided an order having no id" do

      let(:order) { OrderMother.createOrder() }

      it "should return an order with an id" do
        saved_order = order_repository.save(order)

        saved_order.id.should_not be_nil
      end

      it "should return an order whose other attributes match that of the provided order" do
        saved_order = order_repository.save(order)

        saved_order.products.should eql(order.products)
        saved_order.status.should eql(order.status)
      end

      it "should leave the provided order unchanged" do
        order_repository.save(order)

        order.id.should be_nil
      end

    end

    describe "when provided an order having an id" do

      let(:order) { OrderMother.createOrder() }

      it "should return an order whose attributes match that of the provided order" do
        saved_order = order_repository.save(order)

        saved_order.products.should eql(order.products)
        saved_order.status.should eql(order.status)
      end

    end

    describe "when multiple orders are saved" do

      it "should save each order with a unique id" do
        saved_order_ids = (1..3).collect { |i| save_new_order.id }

        saved_order_ids.uniq.should eql(saved_order_ids)
      end

    end

  end

  context "#find" do

    let(:saved_order) { save_new_order() }

    describe "when no order has been saved with the provided id" do

      let(:id_to_find) { -1 }

      it "should return raise an error including the provided order id" do
        lambda { order_repository.find(id_to_find) }.should raise_error(Exception, /#{id_to_find}/)
      end

    end

    describe "when an order has been saved with the provided id" do

      let(:id_to_find) { saved_order.id }

      it "should return the saved order" do
        order_repository.find(id_to_find).should eql(saved_order)
      end

    end

  end

  context "#findAll" do

    describe "when multiple orders have been saved" do

      before(:each) { @saved_orders = (1..3).collect { save_new_order } }

      it "should return all the saved orders" do
        order_repository.findAll().to_a.should eql(@saved_orders)
      end

    end

  end

  def save_new_order
    order_repository.save(OrderMother.create_order())
  end

end
