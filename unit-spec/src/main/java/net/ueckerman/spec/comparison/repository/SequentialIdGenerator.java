package net.ueckerman.spec.comparison.repository;

public class SequentialIdGenerator {

    private Integer currentId;

    public SequentialIdGenerator() {
        this.currentId = 1;
    }

    public Integer next() {
        return currentId++;
    }

}
