package net.ueckerman.order;

public class IllegalOrderStatusTransitionException extends RuntimeException {

    private final OrderStatus currentStatus;
    private final OrderStatus targetStatus;

    public IllegalOrderStatusTransitionException(OrderStatus currentStatus, OrderStatus targetStatus) {
        super("Invalid order status transition from " + currentStatus + " to " + targetStatus);
        this.currentStatus = currentStatus;
        this.targetStatus = targetStatus;
    }

    OrderStatus getCurrentStatus() {
        return currentStatus;
    }

    OrderStatus getTargetStatus() {
        return targetStatus;
    }

}
