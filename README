A comparison of specification and test frameworks using polyglot programming to verify Java source.

Unit Spec Codebase
==================
src directory contains a contrived model with imaginary services to facilitate writing useful specifications.

test directory exercises the following specification and test frameworks:
* JUnit (in Java)
* JDave (in Java)
* RSpec (in Ruby via JRuby)
* ScalaTest (in Scala)

Mock tools exercised:
* Mockito (both Java and Scala)
* EasyMock (both Java and Scala)
* Powermock (in Java)

Other tools used:
* Checkstyle

Run specs via: mvn test

Acceptance Spec Codebase
========================
Contains a Play! application using the contrived model in the unit test codebase via Play!'s Maven plugin.
The Play! application (v1.2.3) must currently be started / stopped manually.

Also contains cuke4duke features exercising the Play! application and a demonstration of a fairly complex feature.
Scala, Selenium 2 and ScalaTest are used to fulfill the step definitions.

In order to run specs:
# Navigate to unit-spec directory
# Install unit-spec snapshot in local m2 repository via: mvn clean install
# Navigate to acceptance-spec directory
# Install Play! dependencies via: play dependencies & play mvn:update
# Start Play! via: play run --%prod
# Test via: mvn integration-test
