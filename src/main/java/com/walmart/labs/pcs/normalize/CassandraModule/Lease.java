package com.walmart.labs.pcs.normalize.CassandraModule;

/*
 * Created by pzhong1 on 12/5/14.
 */
public class Lease {
    private String name;
    private String owner;
    private String value;
    private Long lastUpdateTime;

    public Lease(String name, String owner, String value, Long lastUpdateTime) {
        this.name = name;
        this.owner = owner;
        this.value = value;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return String.format("%-30s\t%-20s\t%-20s\t%-20s", this.getLastUpdateTime(), this.getName(), this.getOwner(), this.getValue());
    }
}
