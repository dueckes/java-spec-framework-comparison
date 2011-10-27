package net.ueckerman.spec.comparison.order;

public enum OrderStatus {
    NEW(null), PAID(NEW), REJECTED(NEW), CANCELLED(NEW);

    private final OrderStatus validPriorStatus;

    OrderStatus(OrderStatus validPriorStatus) {
        this.validPriorStatus = validPriorStatus;
    }

    public boolean isValidPriorStatus(OrderStatus priorStatus) {
        return validPriorStatus == priorStatus;
    }

}
