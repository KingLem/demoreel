package com.colinmills.demoreel.Entity;

public class Book {
    private Order[] buys;
    private Order[] sells;

    public Order[] getBuys() {
        return buys;
    }

    public void setBuys(Order[] buys) {
        this.buys = buys;
    }

    public Order[] getSells() {
        return sells;
    }

    public void setSells(Order[] sells) {
        this.sells = sells;
    }
}
