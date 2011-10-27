require "rspec"

import Java::net.ueckerman.spec.comparison.repository.SequentialIdGenerator

describe SequentialIdGenerator do

  let(:id_generator) { SequentialIdGenerator.new }

  context "#next" do

    describe "when invoked for the first time" do

      it "should return 1" do
        id_generator.next.should eql(1)
      end

    end

    describe "when invoked multiple times" do

      it "should return a number incrementing by 1" do
        first_value = id_generator.next

        (1..3).each { |i| id_generator.next.should eql(first_value + i) }
      end

    end

  end

end
